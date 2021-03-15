//package tran.server.io.netty.handler;
//
//import com.aww.fts.modules.transfer.model.Client;
//import com.aww.fts.modules.transfer.model.ClientConfig;
//import com.aww.fts.modules.transfer.service.ClientConfigService;
//import com.aww.fts.modules.transfer.service.ClientService;
//import com.aww.fts.socket.netty.model.ClientConfigPackage;
//import com.aww.fts.socket.netty.model.ClientConfigSyncPackage;
//import com.aww.fts.socket.netty.model.FTPackage;
//import io.netty.channel.ChannelHandlerContext;
//import org.apache.log4j.Logger;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//@Component
//public class ClientConfigSyncBasePackageHandler extends BasePackageHandler {
//    @Autowired
//    ClientService clientService;
//    @Autowired
//    ClientConfigService clientConfigService;
//    private static Logger logger = Logger.getLogger(ClientConfigSyncBasePackageHandler.class);
//
//    public ClientConfigSyncBasePackageHandler() {
//    }
//
//    @Override
//    public boolean support(short package_type) {
//        return package_type == 27;
//    }
//
//    @Override
//    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
//        try {
//            ClientConfigSyncPackage pkg = (ClientConfigSyncPackage) ftpackage;
//            Client client = this.clientService.getClientById(pkg.getClientId());
//            if (client != null) {
//                ClientConfig clientConfig = this.clientConfigService.getClientConfig(client.getId());
//                ClientConfigPackage ack = new ClientConfigPackage();
//                ack.setClientId(clientConfig.getClientId());
//                ack.setInitTimeout(clientConfig.getInitTimeout());
//                ack.setMinTimeout(clientConfig.getMinTimeout());
//                ack.setMaxTimeout(clientConfig.getMaxTimeout());
//                ack.setBlockSize(clientConfig.getBlockSize());
//                ack.setPriority1ThreadSize(clientConfig.getPriority1ThreadSize());
//                ack.setPriority2ThreadSize(clientConfig.getPriority2ThreadSize());
//                ack.setPriority3ThreadSize(clientConfig.getPriority3ThreadSize());
//                ack.setPriority4ThreadSize(clientConfig.getPriority4ThreadSize());
//                ack.setPriority5ThreadSize(clientConfig.getPriority5ThreadSize());
//                ack.setRecipient(ftpackage.getRecipient());
//                this.response(ctx, ack);
//            }
//        }
//        catch (Exception ex) {
//            ex.printStackTrace();
//        }
//    }
//}
