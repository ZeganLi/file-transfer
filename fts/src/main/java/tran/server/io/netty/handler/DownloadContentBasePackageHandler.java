//package tran.server.io.netty.handler;
//
//import io.netty.channel.ChannelHandlerContext;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import tran.server.io.netty.model.DownloadContentAckPackage;
//import tran.server.io.netty.model.DownloadContentPackage;
//import tran.server.io.netty.model.FTPackage;
//
//import javax.jnlp.DownloadService;
//
///**
// * @author mrliz
// */
//@Component
//@Slf4j
//public class DownloadContentBasePackageHandler extends BasePackageHandler {
//    @Autowired
//    DownloadService downloadService;
//
//    public DownloadContentBasePackageHandler() {
//    }
//
//    @Override
//    public boolean support(short package_type) {
//        return package_type == 18;
//    }
//
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
//        try {
//            DownloadContentPackage contentRequestPackage = (DownloadContentPackage) ftpackage;
//            DownloadFile downloadFile = this.downloadService.getFile(contentRequestPackage.getClientId(), contentRequestPackage.getFileId());
//            if (downloadFile == null) {
//                downloadFile = this.downloadService.check(contentRequestPackage.getClientId(), contentRequestPackage.getFileId());
//                if (downloadFile == null) {
//                    return;
//                }
//            }
//
//            if (contentRequestPackage.getClientId() == downloadFile.getClientId()) {
//                byte[] file_data = FTSFileInputStream.getInstance(downloadFile.getKey(), downloadFile.getPath()).read(contentRequestPackage.getPosition(), contentRequestPackage.getLength());
//                this.fileInfo(ctx, contentRequestPackage, file_data);
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//
//    private void fileInfo(ChannelHandlerContext ctx, DownloadContentPackage downloadRequestPackage, byte[] file_data) {
//        DownloadContentAckPackage ack = new DownloadContentAckPackage();
//        ack.setClientId(downloadRequestPackage.getClientId());
//        ack.setFileId(downloadRequestPackage.getFileId());
//        ack.setData(file_data);
//        ack.setPosition(downloadRequestPackage.getPosition());
//        ack.setCrc((short)CRC16CCITT.getCRC1021(file_data, file_data.length));
//        ack.setRecipient(downloadRequestPackage.getRecipient());
//        this.response(ctx, ack);
//    }
//}
