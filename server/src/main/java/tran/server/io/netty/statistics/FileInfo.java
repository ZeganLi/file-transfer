package tran.server.io.netty.statistics;

import java.io.Serializable;

public class FileInfo implements Serializable {
    private long recordTime;
    private int fileId;
    private int length = 0;
    private long fileLength = 0L;
    private int transmissionRate = 0;

    public FileInfo() {
    }

    public int getFileId() {
        return this.fileId;
    }

    public void setFileId(int fileId) {
        this.fileId = fileId;
    }

    public int getLength() {
        return this.length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public long getFileLength() {
        return this.fileLength;
    }

    public void setFileLength(long fileLength) {
        this.fileLength = fileLength;
    }

    public long getRecordTime() {
        return this.recordTime;
    }

    public void setRecordTime(long recordTime) {
        this.recordTime = recordTime;
    }

    public int getTransmissionRate() {
        return this.transmissionRate;
    }

    public void setTransmissionRate(int transmissionRate) {
        this.transmissionRate = transmissionRate;
    }

    public void updateLength(int size) {
        this.length += size;
        this.fileLength += (long)size;
    }

    public void calcTransmissionRate() {
        long current_time = System.currentTimeMillis();
        long time = current_time - this.recordTime;
        if (time / 1000L > 0L) {
            this.transmissionRate = this.length / ((int)time / 1000);
            this.length = 0;
            this.recordTime = current_time;
        }
    }

    public String getTransmissionRateStr() {
        long rate = (long)(this.transmissionRate * 8);

        if (rate > 1000000L)
            return  (int)(rate/1000000L) + "Mbps";
        else if (rate > 1000L)
            return (int)(rate/1000L) + "Kbps";
        else
            return (int)rate + "bps";
    }
}
