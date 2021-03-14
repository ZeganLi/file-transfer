//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.handler;

import com.aww.fts.modules.transfer.model.Client;
import com.aww.fts.modules.transfer.model.ClientConfig;
import com.aww.fts.modules.transfer.service.ClientConfigService;
import com.aww.fts.modules.transfer.service.ClientService;
import com.aww.fts.socket.netty.model.ClientConfigAckPackage;
import com.aww.fts.socket.netty.model.ClientConfigPackage;
import com.aww.fts.socket.netty.model.FTPackage;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientConfigBasePackageHandler extends BasePackageHandler {
    @Autowired
    ClientService clientService;
    @Autowired
    ClientConfigService clientConfigService;
    private static Logger logger = Logger.getLogger(ClientConfigBasePackageHandler.class);

    public ClientConfigBasePackageHandler() {
    }

    @Override
    public boolean support(short package_type) {
        return package_type == 29;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
        try {
            ClientConfigPackage pkg = (ClientConfigPackage) ftpackage;
            Client client = this.clientService.getClientById(pkg.getClientId());
            if (client != null) {
                ClientConfig clientConfig = new ClientConfig();
                clientConfig.setClientId(pkg.getClientId());
                clientConfig.setInitTimeout(pkg.getInitTimeout());
                clientConfig.setMinTimeout(pkg.getInitTimeout());
                clientConfig.setMaxTimeout(pkg.getInitTimeout());
                clientConfig.setBlockSize(pkg.getBlockSize());
                clientConfig.setPoolSize(pkg.getPoolSize());
                clientConfig.setPriority1ThreadSize(pkg.getPriority1ThreadSize());
                clientConfig.setPriority2ThreadSize(pkg.getPriority2ThreadSize());
                clientConfig.setPriority3ThreadSize(pkg.getPriority3ThreadSize());
                clientConfig.setPriority4ThreadSize(pkg.getPriority4ThreadSize());
                clientConfig.setPriority5ThreadSize(pkg.getPriority5ThreadSize());
//            clientConfig.setConfigVersion(pkg.getVersion());
                try {
                    clientConfig.setFileSaveDirectory(clientConfigService.getClientConfig(client.getId()).getFileSaveDirectory());
                } catch (Exception e) {
                    clientConfig.setFileSaveDirectory("");
                }
                int ret = this.clientConfigService.save(clientConfig);
                if (ret == 1) {
                    ClientConfigAckPackage ack = new ClientConfigAckPackage();
                    ack.setClientId(pkg.getClientId());
                    ack.setRecipient(ftpackage.getRecipient());
                    this.response(ctx, ack);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
