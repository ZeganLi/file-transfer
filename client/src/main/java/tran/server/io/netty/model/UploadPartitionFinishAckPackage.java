//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class UploadPartitionFinishAckPackage extends FTPackage {
    private short block;
    private int clientId;
    private int fileId;

    public UploadPartitionFinishAckPackage() {
        this.setPackageLength((short)12);
    }

    public ByteBuf getByteBuf() {
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeShort(this.block);
        buffer.writeInt(this.clientId);
        buffer.writeInt(this.fileId);
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
}
