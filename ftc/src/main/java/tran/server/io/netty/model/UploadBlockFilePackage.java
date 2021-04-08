package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class UploadBlockFilePackage extends FTPackage {
    private int clientId;
    private int fileId;
    private int length;
    private short block;
    private short isCompressed = 0;

    public UploadBlockFilePackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < 16) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.fileId = message.readInt();
            this.length = message.readInt();
            this.block = message.readShort();
            this.isCompressed = message.readShort();
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

    public short getBlock() {
        return this.block;
    }

    public void setBlock(short block) {
        this.block = block;
    }

    public short getIsCompressed() {
        return this.isCompressed;
    }

    public void setIsCompressed(short isCompressed) {
        this.isCompressed = isCompressed;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }
}
