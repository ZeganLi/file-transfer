package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class ClientConfigSyncPackage extends FTPackage {
    private int clientId;

    public ClientConfigSyncPackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < 4) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            message.release();
        }
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
