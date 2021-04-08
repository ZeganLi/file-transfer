package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class DownloadCheckMD5Package extends FTPackage {
    private int clientId;
    private int fileId;
    private int avgTr;
    private int minTr;
    private int maxTr;
    private long tranffic;

    public DownloadCheckMD5Package(ByteBuf message) throws Exception {
        if (message.readableBytes() < 28) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.fileId = message.readInt();
            this.avgTr = message.readInt();
            this.maxTr = message.readInt();
            this.minTr = message.readInt();
            this.tranffic = message.readLong();
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

    public int getAvgTr() {
        return this.avgTr;
    }

    public void setAvgTr(int avgTr) {
        this.avgTr = avgTr;
    }

    public int getMinTr() {
        return this.minTr;
    }

    public void setMinTr(int minTr) {
        this.minTr = minTr;
    }

    public int getMaxTr() {
        return this.maxTr;
    }

    public void setMaxTr(int maxTr) {
        this.maxTr = maxTr;
    }

    public long getTranffic() {
        return this.tranffic;
    }

    public void setTranffic(long tranffic) {
        this.tranffic = tranffic;
    }
}
