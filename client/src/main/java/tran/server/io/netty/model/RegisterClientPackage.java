package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import tran.common.utils.AesUtil;

import java.nio.charset.StandardCharsets;

public class RegisterClientPackage extends FTPackage {
    private int keyLength;
    private String key;
    private String mac;
    private final String newKey;

    public RegisterClientPackage(ByteBuf message) throws Exception {
        if (message.readableBytes() < 14) {
            throw new Exception("package error");
        } else {
            this.keyLength = message.readInt();
            this.mac = this.readBufferString(message, 12);

            if (message.readableBytes() < this.keyLength) {
                throw new Exception("package error");
            } else {
                this.key = this.readBufferString(message, this.keyLength);
            }

            int newKeyLength = message.readInt();
            if (message.readableBytes() < newKeyLength) {
                throw new Exception("package error");
            } else {
                byte[] tmp = new byte[newKeyLength];
                message.readBytes(tmp);
                this.newKey = new String(AesUtil.aesDecrypt(tmp,AesUtil.KEY), StandardCharsets.UTF_8);
                message.release();
            }
        }
    }

    public int getKeyLength() {
        return this.keyLength;
    }

    public void setKeyLength(int keyLength) {
        this.keyLength = keyLength;
    }

    public String getKey() {
        return this.key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMac() {
        return this.mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getNewKey() {
        return newKey;
    }
}
