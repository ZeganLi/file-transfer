package tran.server.io.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tran.common.utils.AesUtil;
import tran.server.io.netty.model.FTPackage;
import tran.server.io.netty.model.RegisterClientAckPackage;
import tran.server.io.netty.model.RegisterClientPackage;

@Component
@Slf4j
public class RegisterClientBasePackageHandler extends BasePackageHandler {
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
            log.debug("RegisterClientPackageHandler");
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
