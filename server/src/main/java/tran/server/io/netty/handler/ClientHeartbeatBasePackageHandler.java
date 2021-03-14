//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.handler;

import com.aww.common.utils.DateUtils;
import com.aww.fts.modules.transfer.model.Client;
import com.aww.fts.modules.transfer.service.ClientConfigService;
import com.aww.fts.modules.transfer.service.ClientService;
import com.aww.fts.socket.netty.model.ClientHeartbeatAckPackage;
import com.aww.fts.socket.netty.model.ClientHeartbeatPackage;
import com.aww.fts.socket.netty.model.FTPackage;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ClientHeartbeatBasePackageHandler extends BasePackageHandler {
    @Autowired
    ClientService clientService;
    @Autowired
    ClientConfigService clientConfigService;
    private static Logger logger = Logger.getLogger(ClientHeartbeatBasePackageHandler.class);

    private int test = 0;

    public ClientHeartbeatBasePackageHandler() {
    }

    @Override
    public boolean support(short package_type) {
        return package_type == 25;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
        try {
            ClientHeartbeatPackage pkg = (ClientHeartbeatPackage) ftpackage;
            Client client = this.clientService.getClientById(pkg.getClientId());

            if (client != null) {
                ClientHeartbeatAckPackage ack = new ClientHeartbeatAckPackage();
                ack.setClientId(client.getId());
                ack.setSyncId(pkg.getSyncId());
                ack.setRecipient(ftpackage.getRecipient());
                this.response(ctx, ack);

                client.setStatus(Client.CLIENT_STATUS_ONLINE);
                client.setUpdateTime(DateUtils.now());
                this.clientService.updateByPrimaryKey(client);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
