//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.handler;

import com.aww.fts.file.FTSFileInputStream;
import com.aww.fts.modules.sys.service.SysParamService;
import com.aww.fts.modules.transfer.model.UploadFile;
import com.aww.fts.modules.transfer.model.UploadFileBlock;
import com.aww.fts.modules.transfer.service.UploadFileService;
import com.aww.fts.modules.transfer.service.UploadService;
import com.aww.fts.socket.netty.model.FTPackage;
import com.aww.fts.socket.netty.model.UploadBlockFileAckPackage;
import com.aww.fts.socket.netty.model.UploadBlockFilePackage;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadBlockFileBasePackageHandler extends BasePackageHandler {
    @Autowired
    UploadFileService uploadFileService;
    @Autowired
    UploadService uploadService;
    @Autowired
    SysParamService sysParamService;
    private static Logger logger = Logger.getLogger(UploadBlockFileBasePackageHandler.class);

    public UploadBlockFileBasePackageHandler() {
    }

    @Override
    public boolean support(short package_type) {
        return package_type == 4;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
        try {
            UploadBlockFilePackage blockFilePackage = (UploadBlockFilePackage) ftpackage;
            UploadFile file = this.uploadFileService.getFileById(blockFilePackage.getFileId());
            if (file != null && file.getClientId() == blockFilePackage.getClientId()) {
                String temp_dir = this.sysParamService.getParamValueByName("temp_dir");
                UploadFileBlock block = new UploadFileBlock();
                block.setClientId(blockFilePackage.getClientId());
                block.setFileId(blockFilePackage.getFileId());
                block.setBlockId(Integer.valueOf(blockFilePackage.getBlock()));
                block.setLength(blockFilePackage.getLength());
                block.setStatus(UploadFileBlock.UPLOAD_FILE_BLOCK_STATUS_UPLOADING);
                String path = temp_dir + File.separator + block.getKey() + ".tmp";
                block.setPath(path);
                this.uploadService.addBlock(block);
                UploadBlockFileAckPackage ack = new UploadBlockFileAckPackage();
                ack.setBlock(blockFilePackage.getBlock());
                ack.setClientId(blockFilePackage.getClientId());
                ack.setFileId(blockFilePackage.getFileId());
                File block_file = new File(path);
                if (block_file.exists()) {
                    if (block_file.length() > (long) blockFilePackage.getLength()) {
                        FTSFileInputStream.remove(block.getKey());
                        ack.setPosition(0);
                    } else {
                        ack.setPosition((int) block_file.length());
                    }
                } else {
                    ack.setPosition(0);
                }

                ack.setRecipient(ftpackage.getRecipient());
                this.response(ctx, ack);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
