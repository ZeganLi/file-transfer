package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import java.io.UnsupportedEncodingException;

import static tran.server.io.netty.decoder.PackageTypeCode.PACKAGE_TYPE_CLIENT_REGISTER_ACK;

public class RegisterClientAckPackage extends FTPackage {
    private Long code;
    private Long clientId;
    private short nameLength;
    private byte[] clientName;

    public RegisterClientAckPackage() {
        this.setPackageType(PACKAGE_TYPE_CLIENT_REGISTER_ACK);
    }

    public ByteBuf getByteBuf() {
        this.setPackageLength((short)(10 + this.getNameLength()));
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeLong(this.getCode());
        buffer.writeLong(this.getClientId());
        buffer.writeShort(this.getNameLength());
        buffer.writeBytes(this.getClientName());
        return buffer;
    }

    public Long getClientId() {
        return this.clientId;
    }

    public void setClientId(Long clientId) {
        this.clientId = clientId;
    }

    public Long getCode() {
        return this.code;
    }

    public void setCode(Long code) {
        this.code = code;
    }

    public short getNameLength() {
        return this.nameLength;
    }

    public void setNameLength(short nameLength) {
        this.nameLength = nameLength;
    }

    public byte[] getClientName() {
        return this.clientName;
    }

    public void setClientName(byte[] clientName) {
        this.clientName = clientName;
    }

    public void setClientName(String clientName) {
        try {
            this.clientName = clientName.getBytes("UTF-8");
            this.nameLength = (short)this.clientName.length;
        } catch (UnsupportedEncodingException var3) {
            var3.printStackTrace();
        }

    }
}
