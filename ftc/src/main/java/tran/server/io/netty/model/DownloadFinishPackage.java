package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class DownloadFinishPackage extends FTPackage {
    private int clientId;
    private int fileId;
    private long length;

    public DownloadFinishPackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < 16) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.fileId = message.readInt();
            this.length = message.readLong();
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

    public long getLength() {
        return this.length;
    }

    public void setLength(long length) {
        this.length = length;
    }
}
