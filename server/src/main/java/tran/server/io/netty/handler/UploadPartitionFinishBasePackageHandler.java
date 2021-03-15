////
//// Source code recreated from a .class file by IntelliJ IDEA
//// (powered by Fernflower decompiler)
////
//
//package tran.server.io.netty.handler;
//
//import com.aww.common.utils.Utility;
//import com.aww.fts.file.FTSFileInputStream;
//import com.aww.fts.modules.sys.service.SysParamService;
//import com.aww.fts.modules.transfer.model.UploadFileBlock;
//import com.aww.fts.modules.transfer.service.UploadService;
//import com.aww.fts.socket.netty.model.FTPackage;
//import com.aww.fts.socket.netty.model.UploadPartitionFinishAckPackage;
//import com.aww.fts.socket.netty.model.UploadPartitionFinishAckResendPackage;
//import com.aww.fts.socket.netty.model.UploadPartitionFinishPackage;
//import io.netty.channel.ChannelHandlerContext;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.Arrays;
//
//@Component
//public class UploadPartitionFinishBasePackageHandler extends BasePackageHandler {
//    @Autowired
//    SysParamService sysParamService;
//    @Autowired
//    UploadService uploadService;
//    private static Logger logger = Logger.getLogger(UploadPartitionFinishBasePackageHandler.class);
//
//    public UploadPartitionFinishBasePackageHandler() {
//    }
//
//    @Override
//    public boolean support(short package_type) {
//        return package_type == 9;
//    }
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
//        try {
//            UploadPartitionFinishPackage pkg = (UploadPartitionFinishPackage) ftpackage;
//            UploadFileBlock block = this.uploadService.get(pkg.getClientId(), pkg.getFileId(), pkg.getBlock());
//            if (block == null) {
//                block = this.uploadService.check(pkg.getClientId(), pkg.getFileId(), pkg.getBlock());
//                if (block == null) {
//                    return;
//                }
//            }
//
//            long length = Utility.getFileSize(block.getPath());
//            if (length > (long) pkg.getSize()) {
//                FTSFileInputStream.remove(block.getKey());
//                this.resend(ctx, pkg, 0);
//            } else if (length < (long) pkg.getSize()) {
//                this.resend(ctx, pkg, 1);
//            } else {
//                byte[] md5 = pkg.getMd5();
//                byte[] file_md5 = Utility.getFileMD5(block.getPath());
//                if (!Arrays.equals(md5, file_md5)) {
//                    FTSFileInputStream.remove(block.getKey());
//                    this.resend(ctx, pkg, 0);
//                } else {
//                    this.uploadService.remove(block.getClientId(), block.getFileId(), block.getBlockId());
//                    FTSFileInputStream.close(block.getKey());
//                    this.ok(ctx, pkg);
//                }
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void ok(ChannelHandlerContext ctx, UploadPartitionFinishPackage uploadDataPackage) {
//        UploadPartitionFinishAckPackage ack = new UploadPartitionFinishAckPackage();
//        ack.setPackageType((short)10);
//        ack.setBlock((short)uploadDataPackage.getBlock());
//        ack.setClientId(uploadDataPackage.getClientId());
//        ack.setFileId(uploadDataPackage.getFileId());
//        ack.setRecipient(uploadDataPackage.getRecipient());
//        this.response(ctx, ack);
//    }
//
//    private void resend(ChannelHandlerContext ctx, UploadPartitionFinishPackage uploadDataPackage, int flag) {
//        UploadPartitionFinishAckResendPackage ack = new UploadPartitionFinishAckResendPackage();
//        ack.setPackageType((short)11);
//        ack.setBlock((short)uploadDataPackage.getBlock());
//        ack.setClientId(uploadDataPackage.getClientId());
//        ack.setFileId(uploadDataPackage.getFileId());
//        ack.setFlag(flag);
//        ack.setRecipient(uploadDataPackage.getRecipient());
//        this.response(ctx, ack);
//    }
//}
