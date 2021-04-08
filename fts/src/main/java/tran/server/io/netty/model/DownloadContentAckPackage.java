//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DownloadContentAckPackage extends FTPackage {
    private int clientId;
    private int fileId;
    private long position;
    private short crc;
    private byte[] data;

    public DownloadContentAckPackage() {
        this.setPackageType((short)19);
    }

    public ByteBuf getByteBuf() {
        this.setPackageLength((short)(this.data.length + 22));
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeShort(this.crc);
        buffer.writeInt(this.clientId);
        buffer.writeInt(this.fileId);
        buffer.writeLong(this.position);
        buffer.writeShort(this.data.length);
        buffer.writeBytes(this.data);
        return buffer;
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getFileId() {
        return this.fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public long getPosition() {
        return this.position;
    }

    public void setPosition(long position) {
        this.position = position;
    }

    public short getCrc() {
        return this.crc;
    }

    public void setCrc(short crc) {
        this.crc = crc;
    }

    public byte[] getData() {
        return this.data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
