//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.handler;

import com.aww.common.utils.AesUtil;
import com.aww.fts.modules.transfer.model.Client;
import com.aww.fts.modules.transfer.service.ClientService;
import com.aww.fts.socket.netty.model.FTPackage;
import com.aww.fts.socket.netty.model.RegisterClientAckPackage;
import com.aww.fts.socket.netty.model.RegisterClientPackage;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RegisterClientBasePackageHandler extends BasePackageHandler {
    private static Logger logger = Logger.getLogger(RegisterClientBasePackageHandler.class);
    @Autowired
    ClientService clientService;

    public RegisterClientBasePackageHandler() {
    }

    @Override
    public boolean support(short package_type) {
        return package_type == 0;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
        try {
            logger.debug("RegisterClientPackageHandler");
            RegisterClientPackage registerClientPackage = (RegisterClientPackage) ftpackage;

            Client client = this.clientService.getClientByMAC(registerClientPackage.getMac());
            RegisterClientAckPackage ack;
            if (client == null) {
                ack = new RegisterClientAckPackage();
                ack.setCode((short) 0);
                ack.setClientId(0);
                ack.setClientName("");
                ack.setRecipient(ftpackage.getRecipient());
                this.response(ctx, ack);
            } else {
                if (registerClientPackage.getNewKey() != null && registerClientPackage.getNewKey() != "") {
                    AesUtil.clientKey.put(client.getId(), registerClientPackage.getNewKey());
                }

                ack = new RegisterClientAckPackage();
                ack.setClientId(client.getId());
                client.setSecurityKey(registerClientPackage.getKey());
                int ret = this.clientService.updateByPrimaryKey(client);
                ack.setCode((short) ret);
                ack.setClientName(client.getName());
                ack.setRecipient(ftpackage.getRecipient());
                this.response(ctx, ack);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
