//package tran.server.io.netty.handler;
//
//import io.netty.channel.ChannelHandlerContext;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//import tran.server.io.netty.model.ClientBussinessConfigPackage;
//import tran.server.io.netty.model.FTPackage;
//
//@Component
//@Slf4j
//public class ClientBussinessConfigBasePackageHandler extends BasePackageHandler {
//    @Autowired
//    ClientService clientService;
//    @Autowired
//    ClientBussinessConfigService clientBussinessConfigService;
//
//    public ClientBussinessConfigBasePackageHandler() {
//    }
//
//    @Override
//    public boolean support(short package_type) {
//        return package_type == 30;
//    }
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
//        try {
//            ClientBussinessConfigPackage pkg = (ClientBussinessConfigPackage) ftpackage;
//            Client client = this.clientService.getClientById(pkg.getClientId());
//            if (client != null) {
//                this.clientBussinessConfigService.updateBussinessConfig(pkg.getClientId(), pkg.getBussinessStr(), pkg.getBussinessConfigStr());
//                ClientBussinessConfigAckPackage ack = new ClientBussinessConfigAckPackage();
//                ack.setClientId(pkg.getClientId());
//                ack.setBussiness(pkg.getBussiness());
//                ack.setRecipient(ftpackage.getRecipient());
//                this.response(ctx, ack);
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
