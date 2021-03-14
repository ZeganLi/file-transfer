//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.handler;

import com.aww.common.utils.DateUtils;
import com.aww.fts.file.FTSFileInputStream;
import com.aww.fts.modules.sys.service.SysParamService;
import com.aww.fts.modules.transfer.model.UploadFile;
import com.aww.fts.modules.transfer.service.UploadFileService;
import com.aww.fts.socket.netty.model.FTPackage;
import com.aww.fts.socket.netty.model.UploadFileFinishAckPackage;
import com.aww.fts.socket.netty.model.UploadFileFinishPackage;
import com.aww.fts.socket.netty.statistics.FileStatistics;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.RandomAccessFile;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.zip.GZIPInputStream;

import static com.aww.fts.socket.netty.decoder.FTProtocolDecoder.*;

@Component
public class UploadFileFinishBasePackageHandler extends BasePackageHandler {
    @Autowired
    UploadFileService uploadFileService;

    @Autowired
    SysParamService sysParamService;

    private static Logger logger = Logger.getLogger(UploadFileFinishBasePackageHandler.class);

//    private ChannelHandlerContext m_CTX;
//    private UploadFileFinishPackage m_Package;
//    private UploadFile m_File;

    public UploadFileFinishBasePackageHandler() {
    }

    @Override
    public boolean support(short package_type) {
        return package_type == 12;
    }

    @Override
    public void handle(ChannelHandlerContext pCTX, FTPackage pPackage) {

        UploadFileFinishPackage m_Package = null;

        try {
            m_Package = (UploadFileFinishPackage) pPackage;

            //----------------------------------------------------------------
            // 获取文件在数据库表中对应的信息
            // 如果文件不存在或文件对应的客户端编号有误，则重传
            //----------------------------------------------------------------
            boolean mergeFlag = false;

            UploadFile m_File = null;

            synchronized (this.uploadFileService) {
                m_File = this.uploadFileService.getFileById(m_Package.getFileId());
                logger.debug("上传文件完成开始：" + m_File.getName() + "_" + Thread.currentThread().getName());

                if (m_File == null || m_File.getClientId() != m_Package.getClientId()) {
                    this.Resend(pCTX, m_Package);
                    return;
                }

                //----------------------------------------------------------------
                // 如果文件正在上传，则更新文件状态为正在校验，启用单独的线程校验
                // 如果文件正在校验，则不返回消息或通知终端正在校验
                // 如果文件校验成功，则返回成功
                //----------------------------------------------------------------
                if (m_File.getStatus() == UploadFile.UPLOAD_FILE_STATUS_UPLOADING) {
                    FileStatistics.remove(m_File.getId());
                    m_File.setStatus(UploadFile.UPLOAD_FILE_STATUS_CHECKING);
                    m_File.setUpdateTime(DateUtils.now());
                    this.uploadFileService.updateStatus(m_File);
                    mergeFlag = true;
                } else if (m_File.getStatus() == UploadFile.UPLOAD_FILE_STATUS_CHECKING) {
                    // 暂时不响应，终端会超时重发
                } else if (m_File.getStatus() == UploadFile.UPLOAD_FILE_STATUS_COMPLETE) {
                    this.Success(pCTX, m_Package);
                }
            }

            //-----------------------------------------------------
            // 合并文件并校验
            //-----------------------------------------------------
            if (mergeFlag == true) {
                logger.debug("上传文件合并开始" + Thread.currentThread().getName());
                MergeAndAudit(pCTX, m_Package, m_File);
                logger.debug("上传文件合并结束" + Thread.currentThread().getName());
            }

            logger.debug("上传文件完成结束：" + m_File.getName() + "_" + Thread.currentThread().getName());
        }
        catch (Exception ex){
            this.Resend(pCTX, m_Package);
        }
    }

    private void Success(ChannelHandlerContext m_CTX, UploadFileFinishPackage m_Package) {
        responseMsg(PACKAGE_TYPE_UPLOAD_FILE_FINISH_ACK_SUCCESS, m_CTX, m_Package);
    }

    private void Resend(ChannelHandlerContext m_CTX, UploadFileFinishPackage m_Package){
        responseMsg(PACKAGE_TYPE_UPLOAD_FILE_FINISH_ACK_RESEND, m_CTX, m_Package);
    }

    private void responseMsg(short pResponseType, ChannelHandlerContext m_CTX, UploadFileFinishPackage m_Package) {
        UploadFileFinishAckPackage ack = new UploadFileFinishAckPackage();
        ack.setPackageType(pResponseType);
        ack.setClientId(m_Package.getClientId());
        ack.setFileId(m_Package.getFileId());
        ack.setRecipient(m_Package.getRecipient());
        this.response(m_CTX, ack);
    }

    public void MergeAndAudit(ChannelHandlerContext m_CTX, UploadFileFinishPackage m_Package, UploadFile m_File){
        try {

            String temp_dir = this.sysParamService.getParamValueByName("temp_dir");
            byte[] file_md5 = merge(temp_dir, m_File, m_Package.getPosition(), m_Package.getFileLength());
            if (file_md5 != null) {
                if (!Arrays.equals(m_Package.getMd5(), file_md5)) {
                    logger.debug("MD5 ERROR");
                    m_File.setStatus(UploadFile.UPLOAD_FILE_STATUS_UPLOADING);
                    m_File.setUpdateTime(DateUtils.now());
                    m_File.setVersion(m_File.getVersion() + 1);
                    this.uploadFileService.updateStatus(m_File);
                    this.Resend(m_CTX, m_Package);
                } else {
                    m_File.setStatus(UploadFile.UPLOAD_FILE_STATUS_COMPLETE);
                    m_File.setCompleteTime(DateUtils.now());
                    m_File.setAvgTr(m_Package.getAvgTr());
                    m_File.setMinTr(m_Package.getMinTr());
                    m_File.setMaxTr(m_Package.getMaxTr());
                    m_File.setTraffic(m_Package.getTranffic());

                    synchronized (this.uploadFileService) {
                        this.uploadFileService.complete(m_File);
                    }

                    this.Success(m_CTX, m_Package);
                }
            } else {
                this.Resend(m_CTX, m_Package);
                m_File.setStatus(UploadFile.UPLOAD_FILE_STATUS_UPLOADING);
                m_File.setUpdateTime(DateUtils.now());
                m_File.setVersion(m_File.getVersion() + 1);
                this.uploadFileService.updateStatus(m_File);
            }
        }
        catch (Exception ex){
            logger.debug("Merge Audit Error" + ex.getMessage());
        }
    }

    public byte[] merge(String temp_dir, UploadFile file, long position, long length) {
        Object var2 = null;

        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            File destFile = new File(file.getPath());
            if (!destFile.exists()) {
                destFile.getParentFile().mkdirs();
                destFile.createNewFile();
            }

            GZIPInputStream gzip = null;
            int block = file.getBlock() == null ? -1 : file.getBlock();
//            FileOutputStream out = new FileOutputStream(destFile, true);
            RandomAccessFile out = new RandomAccessFile(destFile, "rw");
            out.setLength(length);
            out.seek(position);
//            FileChannel channel = newOut.getChannel();
            while(true) {
                ++block;
                String path = temp_dir + File.separator + "U" + file.getClientId() + "_" + file.getId() + "_" + block + ".tmp";
                File block_file = new File(path);
                if (!block_file.exists()) {
                    --block;
                    file.setBlock(block);
                    if (out != null) {
                        out.close();
                    }

                    if (block == -1) {
                        return null;
                    } else {
                        byte[] md5 = messageDigest.digest();
                        return md5;
                    }
                }

                FTSFileInputStream.close("U" + file.getClientId() + "_" + file.getId() + "_" + block);
                FileInputStream ins = null;

                try {
                    byte[] b = new byte[4096];
                    int n;
                    if (file.getIsCompressed() != 1) {
                        ins = new FileInputStream(block_file);

                        while((n = ins.read(b)) != -1) {
                            if (n > 0) {
                                messageDigest.update(b, 0, n);
                                out.write(b, 0, n);
                            }
                        }
                    } else {
                        ins = new FileInputStream(block_file);
                        gzip = new GZIPInputStream(ins);

                        while((n = gzip.read(b)) != -1) {
                            if (n > 0) {
                                messageDigest.update(b, 0, n);
                                out.write(b, 0, n);
                            }
                        }
                    }

                    if (gzip != null) {
                        gzip.close();
                    }

                    if (ins != null) {
                        ins.close();
                    }

                    gzip = null;
                    ins = null;
                    boolean ret = block_file.delete();
                    if (!ret) {
                        block_file.delete();
                    }
                } catch (Exception var18) {
                    var18.printStackTrace();
                } finally {
                    if (gzip != null) {
                        gzip.close();
                    }

                    if (ins != null) {
                        ins.close();
                    }

                }
            }
        } catch (Exception var20) {
            var20.printStackTrace();
            return null;
        }
    }
}
