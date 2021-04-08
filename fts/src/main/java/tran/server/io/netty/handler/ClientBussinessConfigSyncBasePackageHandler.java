//package tran.server.io.netty.handler;
//
//import com.aww.fts.modules.transfer.model.Bussiness;
//import com.aww.fts.modules.transfer.model.Client;
//import com.aww.fts.modules.transfer.service.BussinessService;
//import com.aww.fts.modules.transfer.service.ClientBussinessConfigService;
//import com.aww.fts.modules.transfer.service.ClientService;
//import com.aww.fts.socket.netty.model.ClientBussinessConfigPackage;
//import com.aww.fts.socket.netty.model.ClientBussinessConfigSyncPackage;
//import com.aww.fts.socket.netty.model.FTPackage;
//import io.netty.channel.ChannelHandlerContext;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ClientBussinessConfigSyncBasePackageHandler extends BasePackageHandler {
//    @Autowired
//    ClientService clientService;
//    @Autowired
//    BussinessService bussinessService;
//    @Autowired
//    ClientBussinessConfigService clientBussinessConfigService;
//    private static Logger logger = Logger.getLogger(ClientBussinessConfigSyncBasePackageHandler.class);
//
//    public ClientBussinessConfigSyncBasePackageHandler() {
//    }
//
//    @Override
//    public boolean support(short package_type) {
//        return package_type == 28;
//    }
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
//        try {
//            ClientBussinessConfigSyncPackage pkg = (ClientBussinessConfigSyncPackage) ftpackage;
//            Client client = this.clientService.getClientById(pkg.getClientId());
//            if (client != null) {
//                Bussiness bussiness = this.bussinessService.getBussinessByName(pkg.getBussiness());
//                if (bussiness == null) {
//                    bussiness = new Bussiness();
//                    bussiness.setName(pkg.getBussiness());
//                    this.bussinessService.insertBussiness(bussiness);
//                }
//
//                String bussinessConfig = this.clientBussinessConfigService.getBussinessConfigStr(pkg.getClientId(), bussiness.getId());
//                ClientBussinessConfigPackage ack = new ClientBussinessConfigPackage();
//                ack.setClientId(pkg.getClientId());
//                ack.setBussiness(pkg.getBussiness());
//                ack.setBussinessConfig(bussinessConfig);
//                ack.setRecipient(ftpackage.getRecipient());
//                this.response(ctx, ack);
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
