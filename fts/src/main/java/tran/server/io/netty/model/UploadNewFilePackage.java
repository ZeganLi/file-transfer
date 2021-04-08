package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class UploadNewFilePackage extends FTPackage {
    private int clientId;
    private short isCompressed;
    private short fileNameLength;
    private String fileName;
    private short priority;
    private long length;
    private long compressedLength;
    private long totalFileLength;

    public UploadNewFilePackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < 26) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.length = message.readLong();
            this.compressedLength = message.readLong();
            this.totalFileLength = message.readLong();
            this.isCompressed = message.readShort();
            this.priority = message.readShort();
            this.fileNameLength = message.readShort();
            //this.newKeyLength = message.readShort();
            if (message.readableBytes() < this.fileNameLength) {
                throw new Exception("package error");
            } else {
                this.fileName = this.readBufferString(message, this.fileNameLength);
                message.release();
            }
        }
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public short getFileNameLength() {
        return this.fileNameLength;
    }

    public void setFileNameLength(short fileNameLength) {
        this.fileNameLength = fileNameLength;
    }

    public String getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public short getIsCompressed() {
        return this.isCompressed;
    }

    public void setIsCompressed(short isCompressed) {
        this.isCompressed = isCompressed;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public long getCompressedLength() {
        return this.compressedLength;
    }

    public void setCompressedLength(long compressedLength) {
        this.compressedLength = compressedLength;
    }

    public short getPriority() {
        return this.priority;
    }

    public void setPriority(short priority) {
        this.priority = priority;
    }

    public long getTotalFileLength() {
        return this.totalFileLength;
    }

    public void setTotalFileLength(long length) {
        this.totalFileLength = length;
    }
}
