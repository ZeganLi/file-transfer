package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class UploadDataPackage extends FTPackage {
    private int clientId;
    private int fileId;
    private int position = 0;
    private short block = 0;
    private short crc = 0;
    private byte[] data = null;

    public UploadDataPackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < 16) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.fileId = message.readInt();
            this.position = message.readInt();
            this.block = message.readShort();
            this.crc = message.readShort();
            this.data  = this.readBufferBytes(message);
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

    public int getPosition() {
        return this.position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public short getBlock() {
        return this.block;
    }

    public void setBlock(short block) {
        this.block = block;
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

    public int getDataLength() {
        return this.data.length;
    }
}
