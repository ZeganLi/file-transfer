//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ClientConfigPackage extends FTPackage {
    private int clientId;
    private int initTimeout;
    private int minTimeout;
    private int maxTimeout;
    private int blockSize;
    private int poolSize;
    private int priority1ThreadSize;
    private int priority2ThreadSize;
    private int priority3ThreadSize;
    private int priority4ThreadSize;
    private int priority5ThreadSize;
    private int version = 0;

    public ClientConfigPackage() {
        this.setPackageType((short)29);
        this.setPackageLength((short)44);
    }

    public ClientConfigPackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < this.getPackageLength()) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.initTimeout = message.readInt();
            this.minTimeout = message.readInt();
            this.maxTimeout = message.readInt();
            this.blockSize = message.readInt();
            this.poolSize = message.readInt();
            this.priority1ThreadSize = message.readInt();
            this.priority2ThreadSize = message.readInt();
            this.priority3ThreadSize = message.readInt();
            this.priority4ThreadSize = message.readInt();
            this.priority5ThreadSize = message.readInt();
//            this.version = message.readInt();
            message.release();
        }
    }

    public ByteBuf getByteBuf() {
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeShort(this.getPackageLength());
        buffer.writeInt(this.clientId);
        buffer.writeInt(this.initTimeout);
        buffer.writeInt(this.minTimeout);
        buffer.writeInt(this.maxTimeout);
        buffer.writeInt(this.blockSize);
        buffer.writeInt(this.poolSize);
        buffer.writeInt(this.priority1ThreadSize);
        buffer.writeInt(this.priority2ThreadSize);
        buffer.writeInt(this.priority3ThreadSize);
        buffer.writeInt(this.priority4ThreadSize);
        buffer.writeInt(this.priority5ThreadSize);
        buffer.writeInt(this.version);
        return buffer;
    }

    public int getClientId() {
        return this.clientId;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public int getInitTimeout() {
        return this.initTimeout;
    }

    public void setInitTimeout(int initTimeout) {
        this.initTimeout = initTimeout;
    }

    public int getMinTimeout() {
        return this.minTimeout;
    }

    public void setMinTimeout(int minTimeout) {
        this.minTimeout = minTimeout;
    }

    public int getMaxTimeout() {
        return this.maxTimeout;
    }

    public void setMaxTimeout(int maxTimeout) {
        this.maxTimeout = maxTimeout;
    }

    public int getBlockSize() {
        return this.blockSize;
    }

    public void setBlockSize(int blockSize) {
        this.blockSize = blockSize;
    }

    public int getPoolSize() { return poolSize;}

    public void setPoolSize(int poolSize) {this.poolSize = poolSize;}

    public int getPriority1ThreadSize() {
        return this.priority1ThreadSize;
    }

    public void setPriority1ThreadSize(int priority1ThreadSize) {
        this.priority1ThreadSize = priority1ThreadSize;
    }

    public int getPriority2ThreadSize() {
        return this.priority2ThreadSize;
    }

    public void setPriority2ThreadSize(int priority2ThreadSize) {
        this.priority2ThreadSize = priority2ThreadSize;
    }

    public int getPriority3ThreadSize() {
        return this.priority3ThreadSize;
    }

    public void setPriority3ThreadSize(int priority3ThreadSize) {
        this.priority3ThreadSize = priority3ThreadSize;
    }

    public int getPriority4ThreadSize() {
        return this.priority4ThreadSize;
    }

    public void setPriority4ThreadSize(int priority4ThreadSize) {
        this.priority4ThreadSize = priority4ThreadSize;
    }

    public int getPriority5ThreadSize() {
        return this.priority5ThreadSize;
    }

    public void setPriority5ThreadSize(int priority5ThreadSize) {
        this.priority5ThreadSize = priority5ThreadSize;
    }

    public int getVersion() {
        return this.version;
    }

    public void setVersion(int version) {
        this.version = version;
    }


}
