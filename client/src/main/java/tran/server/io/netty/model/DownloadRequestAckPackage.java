//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.model;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class DownloadRequestAckPackage extends FTPackage {
    private int clientId;
    private int fileId;
    private long length;
    private short isCompressed;
    private short isMessage;
    private short level;
    private long oldLength;

    private short senderLength;
    private byte[] sender;
    private short receiverLength;
    private byte[] receiver;

    private byte[] fileName;

    public DownloadRequestAckPackage() {
        this.setPackageType((short)17);
    }

    public ByteBuf getByteBuf() {
        this.setPackageLength((short)(this.fileName.length + 32));
        ByteBuf buffer = Unpooled.directBuffer(this.getPackageLength());
        buffer.writeShort(this.getPackageType());
        buffer.writeShort(this.getPackageLength());
        buffer.writeInt(this.clientId);
        buffer.writeInt(this.fileId);
        buffer.writeLong(this.length);
        buffer.writeLong(this.oldLength);
        buffer.writeShort(this.isCompressed);

//        buffer.writeShort(this.isMessage);
//        buffer.writeShort(this.senderLength);
//        buffer.writeBytes(this.sender);
//        buffer.writeShort(this.receiverLength);
//        buffer.writeBytes(this.receiver);

        buffer.writeShort(this.level);
        buffer.writeBytes(this.fileName);
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

    public long getLength() {
        return this.length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public short getIsCompressed() {
        return this.isCompressed;
    }

    public void setIsCompressed(short isCompressed) {
        this.isCompressed = isCompressed;
    }

    public short getIsMessage(){
        return this.isMessage;
    }

    public void setIsMessage(short isMessage){
        this.isMessage = isMessage;
    }

    public short getLevel() {
        return this.level;
    }

    public void setLevel(short level) {
        this.level = level;
    }

    public byte[] getFileName() {
        return this.fileName;
    }

    public void setFileName(String fileName) {
        try {
            this.fileName = fileName.getBytes("UTF-8");
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    public long getOldLength() {
        return this.oldLength;
    }

    public void setOldLength(long oldLength) {
        this.oldLength = oldLength;
    }

    public short getSenderLength(){
        return this.senderLength;
    }

    public void setSenderLength(short senderLength){
        this.senderLength = senderLength;
    }

    public byte[] getSender(){
        return this.sender;
    }

    public void setSender(String sender){
        try {
            this.sender = sender.getBytes("UTF-8");
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    public short getReceiverLength(){
        return this.receiverLength;
    }

    public void setReceiverLength(short receiverLength){
        this.receiverLength = receiverLength;
    }

    public byte[] getReceiver(){
        return this.receiver;
    }

    public void setReceiver(String receiver){
        try {
            this.receiver = receiver.getBytes("UTF-8");
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }
}
