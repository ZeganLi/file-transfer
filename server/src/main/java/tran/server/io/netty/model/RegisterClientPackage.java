//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import com.aww.common.utils.AesUtil;
import io.netty.buffer.ByteBuf;

public class RegisterClientPackage extends FTPackage {
    private int keyLength = 0;
    private String key = null;
    private String mac = null;
    private int newKeyLength = 0;
    private String newKey = null;

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

            this.newKeyLength = message.readInt();
            if (message.readableBytes() < this.newKeyLength) {
                throw new Exception("package error");
            } else {
                byte[] tmp = new byte[this.newKeyLength];
                message.readBytes(tmp);
                this.newKey = new String(AesUtil.aesDecrypt(tmp,AesUtil.KEY), "UTF-8");
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
