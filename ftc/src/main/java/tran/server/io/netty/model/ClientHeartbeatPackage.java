package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class ClientHeartbeatPackage extends FTPackage {
    private int clientId;
    private long syncId;

    public ClientHeartbeatPackage(ByteBuf message) throws Exception {
        this.clientId = message.readInt();
        this.syncId = message.readLong();
        message.release();
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public long getSyncId() { return this.syncId; }

    public void setSyncId(long pSyncId) {
        this.syncId = pSyncId;
    }
}
