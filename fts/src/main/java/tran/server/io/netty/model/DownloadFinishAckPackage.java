//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DownloadFinishAckPackage extends FTPackage {
    private int clientId;
    private int fileId;
    private byte[] md5;

    public DownloadFinishAckPackage() {
        this.setPackageType((short)22);
        this.setPackageLength((short)20);
    }

    public ByteBuf getByteBuf() {
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeShort(this.getPackageLength());
        buffer.writeInt(this.clientId);
        buffer.writeInt(this.fileId);
        buffer.writeBytes(this.md5);
        return buffer;
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

    public void setMd5(byte[] md5) {
        this.md5 = md5;
    }
}
