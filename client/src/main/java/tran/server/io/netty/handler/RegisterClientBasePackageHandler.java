package tran.server.io.netty.handler;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tran.common.utils.AesUtil;
import tran.modules.fts.client.entity.ClientEntity;
import tran.modules.fts.client.service.ClientService;
import tran.server.io.netty.model.FTPackage;
import tran.server.io.netty.model.RegisterClientAckPackage;
import tran.server.io.netty.model.RegisterClientPackage;

import java.util.List;

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

            QueryWrapper<ClientEntity> clientEntityQueryWrapper = new QueryWrapper<>();
            ClientEntity client = this.clientService.getOne(clientEntityQueryWrapper.eq("client_key", registerClientPackage.getMac()));

            RegisterClientAckPackage ack;
            if (client == null) {
                ack = new RegisterClientAckPackage();
                ack.setCode(0L);
                ack.setClientId(0L);
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
                ClientEntity byId = this.clientService.getById(client);
                ack.setCode(byId.getId());
                ack.setClientName(client.getClientName());
                ack.setRecipient(ftpackage.getRecipient());
                this.response(ctx, ack);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
