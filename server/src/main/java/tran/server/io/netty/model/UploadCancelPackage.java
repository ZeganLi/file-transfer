package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class UploadCancelPackage extends FTPackage {
    private int clientId;
    private int fileId;

    public UploadCancelPackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < 8) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.fileId = message.readInt();
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
}
