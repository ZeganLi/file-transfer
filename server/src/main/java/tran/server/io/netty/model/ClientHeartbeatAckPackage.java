//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import com.aww.fts.socket.netty.decoder.FTProtocolDecoder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ClientHeartbeatAckPackage extends FTPackage {
    private int clientId;
    private long syncId;

    public ClientHeartbeatAckPackage() {
        this.setPackageType((short) FTProtocolDecoder.PACKAGE_TYPE_CLIENT_HEARTBEAT_SYNC_ACK);
    }

    public ByteBuf getByteBuf() {
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeInt(this.clientId);
        buffer.writeLong(this.syncId);
        return buffer;
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public long getSyncId() { return this.syncId; }

    public void setSyncId(long pSyncId){
        this.syncId = pSyncId;
    }
}
