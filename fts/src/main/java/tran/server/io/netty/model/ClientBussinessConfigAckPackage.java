//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ClientBussinessConfigAckPackage extends FTPackage {
    private short bussinessLength;
    private int clientId;
    private byte[] bussiness;

    public ClientBussinessConfigAckPackage() {
        this.setPackageType((short)32);
    }

    public ByteBuf getByteBuf() {
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeShort(this.bussinessLength);
        buffer.writeInt(this.clientId);
        buffer.writeBytes(this.bussiness);
        return buffer;
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public short getBussinessLength() {
        return this.bussinessLength;
    }

    public void setBussinessLength(short bussinessLength) {
        this.bussinessLength = bussinessLength;
    }

    public byte[] getBussiness() {
        return this.bussiness;
    }

    public void setBussiness(byte[] bussiness) {
        this.bussiness = bussiness;
        this.bussinessLength = (short)this.bussiness.length;
    }
}
