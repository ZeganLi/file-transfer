package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

public class ClientBussinessConfigSyncPackage extends FTPackage {
    private int clientId;
    private short bussinessLength;
    private String bussiness;

    public ClientBussinessConfigSyncPackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < 6) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.bussinessLength = message.readShort();
            if (message.readableBytes() < this.bussinessLength) {
                throw new Exception("package error");
            } else {
                this.bussiness = this.readBufferString(message, this.bussinessLength);
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

    public short getBussinessLength() {
        return this.bussinessLength;
    }

    public void setBussinessLength(short bussinessLength) {
        this.bussinessLength = bussinessLength;
    }

    public String getBussiness() {
        return this.bussiness;
    }

    public void setBussiness(String bussiness) {
        this.bussiness = bussiness;
    }
}
