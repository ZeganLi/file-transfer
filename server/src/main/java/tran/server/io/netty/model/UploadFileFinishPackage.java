//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class UploadFileFinishPackage extends FTPackage {
    private int clientId;
    private int fileId;
    private byte[] md5 = null;
    private int avgTr;
    private int minTr;
    private int maxTr;
    private long tranffic;
    private long position;
    private long fileLength;

    public UploadFileFinishPackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < 44) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.fileId = message.readInt();
            this.md5 = this.readBufferBytes(message, 16);
            this.avgTr = message.readInt();
            this.maxTr = message.readInt();
            this.minTr = message.readInt();
            this.tranffic = message.readLong();
            this.position = message.readLong();
            this.fileLength = message.readLong();
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

    public byte[] getMd5() {
        return this.md5;
    }

    public void setMd5(byte[] md5) {
        this.md5 = md5;
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

    public  long getPosition(){ return  this.position;}
    public  long getFileLength(){ return  this.fileLength;}
}
