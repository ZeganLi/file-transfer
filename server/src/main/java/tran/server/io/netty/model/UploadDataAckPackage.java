//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UploadDataAckPackage extends FTPackage {
    private short block;
    private int clientId;
    private int fileId;
    private int position;
    private int resendPosition;
    private byte[] key;

    public UploadDataAckPackage() {
        this.setPackageLength((short)20);
    }

    public ByteBuf getByteBuf() {
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeShort(this.block);
        buffer.writeInt(this.clientId);
        buffer.writeInt(this.fileId);
        buffer.writeInt(this.position);
        buffer.writeInt(this.resendPosition);
        if (this.key != null){
        buffer.writeBytes(this.key);
        }
        return buffer;
    }

    public short getBlock() {
        return this.block;
    }

    public void setBlock(short block) {
        this.block = block;
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

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getResendPosition() {
        return this.resendPosition;
    }

    public void setResendPosition(int resendPosition) {
        this.resendPosition = resendPosition;
    }

    public byte[] getKey() {
        return key;
    }

    public void setKey(byte[] key) {
        this.key = key;
    }
}


