package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;

public class FTPackage implements Serializable {
    private short packageType;
    private short packageLength;
    private InetSocketAddress recipient;

    public FTPackage() {
    }

    public short getPackageType() {
        return this.packageType;
    }

    public void setPackageType(short packageType) {
        this.packageType = packageType;
    }

    public short getPackageLength() {
        return this.packageLength;
    }

    public void setPackageLength(short packageLength) {
        this.packageLength = packageLength;
    }

    public byte[] readBufferBytes(ByteBuf buffer, int length) {
        byte[] tmp = new byte[length];
        buffer.readBytes(tmp);
        return tmp;
    }

    public String readBufferString(ByteBuf buffer, int length) {
        byte[] tmp = new byte[length];
        buffer.readBytes(tmp);

        try {
            return new String(tmp, "UTF-8");
        } catch (UnsupportedEncodingException var5) {
            return "";
        }
    }

    public byte[] readBufferBytes(ByteBuf buffer) {
        int length = buffer.readableBytes();
        if (length > 0) {
            byte[] tmp = new byte[length];
            buffer.readBytes(tmp);
            return tmp;
        } else {
            return new byte[0];
        }
    }

    public InetSocketAddress getRecipient() {
        return this.recipient;
    }

    public void setRecipient(InetSocketAddress recipient) {
        this.recipient = recipient;
    }

    public ByteBuf getByteBuf() {
        return null;
    }
}
