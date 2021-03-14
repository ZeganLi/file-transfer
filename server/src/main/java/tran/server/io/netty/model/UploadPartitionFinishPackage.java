//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class UploadPartitionFinishPackage extends FTPackage {
    private int clientId;
    private int fileId;
    private int size = 0;
    private int block = 0;
    private byte[] md5 = null;

    public UploadPartitionFinishPackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < 32) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.fileId = message.readInt();
            this.size = message.readInt();
            this.block = message.readInt();
            this.md5 = this.readBufferBytes(message, 16);
            message.release();
        }
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

    public int getSize() {
        return this.size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public byte[] getMd5() {
        return this.md5;
    }

    public void setMd5(byte[] md5) {
        this.md5 = md5;
    }

    public int getBlock() {
        return this.block;
    }

    public void setBlock(int block) {
        this.block = block;
    }
}
