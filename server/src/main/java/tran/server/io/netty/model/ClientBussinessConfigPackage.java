package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.UnsupportedEncodingException;

public class ClientBussinessConfigPackage extends FTPackage {
    private int clientId;
    private short bussinessLength;
    private short bussinessConfigLength;
    private byte[] bussiness;
    private byte[] bussinessConfig;

    public ClientBussinessConfigPackage() {
        this.setPackageType((short)30);
    }

    public ClientBussinessConfigPackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < this.getPackageLength()) {
            throw new Exception("package error");
        } else {
            this.clientId = message.readInt();
            this.bussinessLength = message.readShort();
            this.bussinessConfigLength = message.readShort();
            this.bussiness = this.readBufferBytes(message, this.bussinessLength);
            this.bussinessConfig = this.readBufferBytes(message, this.bussinessConfigLength);
            message.release();
        }
    }

    public ByteBuf getByteBuf() {
        this.setPackageLength((short)(12 + this.bussinessLength + this.bussinessConfigLength));
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeShort(this.getPackageLength());
        buffer.writeInt(this.clientId);
        buffer.writeShort(this.bussinessLength);
        buffer.writeShort(this.bussinessConfigLength);
        buffer.writeBytes(this.bussiness);
        buffer.writeBytes(this.bussinessConfig);
        return buffer;
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

    public short getBussinessConfigLength() {
        return this.bussinessConfigLength;
    }

    public void setBussinessConfigLength(short bussinessConfigLength) {
        this.bussinessConfigLength = bussinessConfigLength;
    }

    public byte[] getBussiness() {
        return this.bussiness;
    }

    public void setBussiness(byte[] bussiness) {
        this.bussiness = bussiness;
    }

    public byte[] getBussinessConfig() {
        return this.bussinessConfig;
    }

    public void setBussinessConfig(byte[] bussinessConfig) {
        this.bussinessConfig = bussinessConfig;
    }

    public void setBussinessConfig(String bussinessConfig) {
        try {
            this.bussinessConfig = bussinessConfig.getBytes("UTF-8");
        } catch (Exception var3) {
            this.bussinessConfig = new byte[0];
        }

        this.bussinessConfigLength = (short)this.bussinessConfig.length;
    }

    public void setBussiness(String bussiness) {
        try {
            this.bussiness = bussiness.getBytes("UTF-8");
        } catch (Exception var3) {
            this.bussiness = new byte[0];
        }

        this.bussinessLength = (short)this.bussiness.length;
    }

    public String getBussinessStr() {
        try {
            return new String(this.bussiness, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return "";
        }
    }

    public String getBussinessConfigStr() {
        try {
            return new String(this.bussinessConfig, "UTF-8");
        } catch (UnsupportedEncodingException var2) {
            return "";
        }
    }
}
