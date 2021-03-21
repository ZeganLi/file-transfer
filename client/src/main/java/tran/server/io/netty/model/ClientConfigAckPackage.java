//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ClientConfigAckPackage extends FTPackage {
    private int clientId;

    public ClientConfigAckPackage() {
        this.setPackageType((short)31);
        this.setPackageLength((short)8);
    }

    public ByteBuf getByteBuf() {
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeShort(this.getPackageLength());
        buffer.writeInt(this.clientId);
        return buffer;
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }
}
