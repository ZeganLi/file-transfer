//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UploadNewFileAckPackage extends FTPackage {
    private int clientId;
    private int fileId;
    private int block;
    private int key;//0 服务端完成 1 没完成
    public void setKey(int key) {
        this.key = key;
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

    public int getBlock() {
        return this.block;
    }

    public void setBlock(int block) {
        this.block = block;
    }

    public UploadNewFileAckPackage() {
        this.setPackageType((short)3);
        this.setPackageLength((short)16);
    }

    public ByteBuf getByteBuf() {
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeShort(this.getPackageLength());
        buffer.writeInt(this.clientId);
        buffer.writeInt(this.fileId);
        buffer.writeInt(this.block);
        return buffer;
    }
}
