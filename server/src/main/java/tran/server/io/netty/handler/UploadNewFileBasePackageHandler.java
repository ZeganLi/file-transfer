//package tran.server.io.netty.handler;
//
//import io.netty.channel.ChannelHandlerContext;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import tran.server.io.netty.model.FTPackage;
//import tran.server.io.netty.model.UploadNewFilePackage;
//
//@Component
//@Slf4j
//public class UploadNewFileBasePackageHandler extends BasePackageHandler {
//    @Autowired
//    ClientService clientService;
//    @Autowired
//    UploadFileService uploadFileService;
//    @Autowired
//    SysParamService sysParamService;
//    @Autowired
//    BussinessService bussinessService;
//
//    public UploadNewFileBasePackageHandler() {
//    }
//
//    @Override
//    public boolean support(short package_type) {
//        return package_type == 2;
//    }
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
//        try {
//            UploadNewFilePackage newFilePackage = (UploadNewFilePackage) ftpackage;
//            Client client = this.clientService.selectByPrimaryKey(newFilePackage.getClientId());
//            if (client != null) {
//                String upload_dir = this.sysParamService.getParamValueByName("upload_dir");
//                UploadFile file = this.uploadFileService.getUploadFileByPackage(newFilePackage, upload_dir, client);
//
//                if (file != null) {
//                    UploadNewFileAckPackage ack = new UploadNewFileAckPackage();
//                    ack.setClientId(file.getClientId());
//                    ack.setFileId(file.getId());
//                    ack.setBlock(file.getBlock() == null ? -1 : file.getBlock());
//                    ack.setRecipient(ftpackage.getRecipient());
//                    this.response(ctx, ack);
//                }
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
