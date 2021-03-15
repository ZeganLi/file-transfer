////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package tran.server.io.netty.handler;
//
//import com.aww.common.utils.AesUtil;
//import com.aww.fts.file.FTSFileInputStream;
//import com.aww.fts.modules.transfer.model.UploadFileBlock;
//import com.aww.fts.modules.transfer.service.UploadService;
//import com.aww.fts.socket.netty.model.FTPackage;
//import com.aww.fts.socket.netty.model.UploadDataAckPackage;
//import com.aww.fts.socket.netty.model.UploadDataPackage;
//import com.aww.fts.socket.netty.statistics.FileStatistics;
//import io.netty.channel.ChannelHandlerContext;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.io.UnsupportedEncodingException;
//
//@Component
//public class UploadDataBasePackageHandler extends BasePackageHandler {
//    @Autowired
//    UploadService uploadService;
//    private static Logger logger = Logger.getLogger(UploadDataBasePackageHandler.class);
//
//    public UploadDataBasePackageHandler() {
//    }
//
//    @Override
//    public boolean support(short package_type) {
//        return package_type == 6;
//    }
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
//        try {
//            UploadDataPackage uploadDataPackage = (UploadDataPackage) ftpackage;
//            UploadFileBlock block = this.uploadService.get(uploadDataPackage.getClientId(), uploadDataPackage.getFileId(), uploadDataPackage.getBlock());
//            if (block == null) {
//                block = this.uploadService.check(uploadDataPackage.getClientId(), uploadDataPackage.getFileId(), uploadDataPackage.getBlock());
//                if (block == null) {
//                    return;
//                }
//            }
//
//            try {
//                uploadDataPackage.setData(AesUtil.aesDecrypt(uploadDataPackage.getData(), AesUtil.clientKey.get(uploadDataPackage.getClientId())));
//
//            } catch (Exception e) {
//
//                if (AesUtil.clientKey.get(uploadDataPackage.getClientId()) == null) {
//                    AesUtil.clientKey.put(uploadDataPackage.getClientId(), AesUtil.randomKey);
//                }
//
//                e.printStackTrace();
//
//                this.keyError(ctx, uploadDataPackage);
//                return;
//            }
//
//            FTSFileInputStream writer = FTSFileInputStream.getInstance(block.getKey(), block.getPath());
//            long length = writer.getLength();
//            if (length == (long) uploadDataPackage.getPosition()) {
//                this.ok(ctx, uploadDataPackage);
//                FileStatistics.update(uploadDataPackage.getFileId(), uploadDataPackage.getDataLength());
//                writer.write(uploadDataPackage.getPosition(), uploadDataPackage.getData());
//            } else if (length < (long) uploadDataPackage.getPosition()) {
//                logger.debug("DATA resend:" + block.getFileId() + "-" + block.getBlockId());
//                this.resend(ctx, uploadDataPackage, (int) length);
//            } else {
//                this.ok(ctx, uploadDataPackage);
//                FileStatistics.update(uploadDataPackage.getFileId(), uploadDataPackage.getDataLength());
//                writer.write(uploadDataPackage.getPosition(), uploadDataPackage.getData());
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void ok(ChannelHandlerContext ctx, UploadDataPackage uploadDataPackage) {
//        UploadDataAckPackage ack = new UploadDataAckPackage();
//        ack.setPackageType((short)7);
//
//        ack.setBlock(uploadDataPackage.getBlock());
//        ack.setClientId(uploadDataPackage.getClientId());
//        ack.setFileId(uploadDataPackage.getFileId());
//        ack.setPosition(uploadDataPackage.getPosition());
//        ack.setResendPosition(0);
//        ack.setRecipient(uploadDataPackage.getRecipient());
//        this.response(ctx, ack);
//    }
//
//    private void keyError(ChannelHandlerContext ctx,UploadDataPackage uploadDataPackage){
//        UploadDataAckPackage ack = new UploadDataAckPackage();
//        ack.setPackageType((short)35);
//
//        ack.setBlock(uploadDataPackage.getBlock());
//        ack.setClientId(uploadDataPackage.getClientId());
//        ack.setFileId(uploadDataPackage.getFileId());
//        ack.setPosition(uploadDataPackage.getPosition());
//        ack.setResendPosition(0);
//
//        try {
//            String newKey = AesUtil.clientKey.get(uploadDataPackage.getClientId());
//
//            if (newKey == null)
//                newKey = "";
//
//            ack.setKey(newKey.getBytes("UTF-8"));
//
//        } catch (UnsupportedEncodingException e) {
//            e.printStackTrace();
//        }
//
//        ack.setRecipient(uploadDataPackage.getRecipient());
//        this.response(ctx, ack);
//    }
//
//
//    private void resend(ChannelHandlerContext ctx, UploadDataPackage uploadDataPackage, int resendPosition) {
//        UploadDataAckPackage ack = new UploadDataAckPackage();
//        ack.setPackageType((short)8);
//        ack.setBlock(uploadDataPackage.getBlock());
//        ack.setClientId(uploadDataPackage.getClientId());
//        ack.setFileId(uploadDataPackage.getFileId());
//        ack.setPosition(uploadDataPackage.getPosition());
//        ack.setResendPosition(resendPosition);
//        ack.setRecipient(uploadDataPackage.getRecipient());
//        this.response(ctx, ack);
//    }
//}
