package tran.server.io.netty.decoder;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;
import lombok.extern.slf4j.Slf4j;
import tran.server.io.netty.model.*;

import java.util.List;

/**
 * 文件传输解码器
 *
 * @author mrliz
 */
@Slf4j
public class FTProtocolDecoder extends MessageToMessageDecoder<DatagramPacket> {
    public static final short PACKAGE_HEAD_LENGTH = 4;

    public static int testCount = 0;

    public static final short PACKAGE_TYPE_CLIENT_REGISTER = 0;
    public static final short PACKAGE_TYPE_CLIENT_REGISTER_ACK = 1;
    public static final short PACKAGE_TYPE_UPLOAD_FILE = 2;
    public static final short PACKAGE_TYPE_UPLOAD_FILE_ACK = 3;
    public static final short PACKAGE_TYPE_UPLOAD_BLOCK_FILE = 4;
    public static final short PACKAGE_TYPE_UPLOAD_BLOCK_FILE_ACK = 5;
    public static final short PACKAGE_TYPE_UPLOAD_BLOCK_DATA = 6;
    public static final short PACKAGE_TYPE_UPLOAD_BLOCK_DATA_ACK_SUCCESS = 7;
    public static final short PACKAGE_TYPE_UPLOAD_BLOCK_DATA_ACK_RESEND = 8;
    public static final short PACKAGE_TYPE_UPLOAD_BLOCK_FILE_FINISH = 9;
    public static final short PACKAGE_TYPE_UPLOAD_BLOCK_FILE_FINISH_ACK_SUCCESS = 10;
    public static final short PACKAGE_TYPE_UPLOAD_BLOCK_FILE_FINISH_ACK_RESEND = 11;
    public static final short PACKAGE_TYPE_UPLOAD_FILE_FINISH = 12;
    public static final short PACKAGE_TYPE_UPLOAD_FILE_FINISH_ACK_SUCCESS = 13;
    public static final short PACKAGE_TYPE_UPLOAD_FILE_FINISH_ACK_RESEND = 14;
    public static final short PACKAGE_TYPE_DOWNLOAD_REQUEST = 15;
    public static final short PACKAGE_TYPE_DOWNLOAD_REQUEST_ACK_NO_FILE = 16;
    public static final short PACKAGE_TYPE_DOWNLOAD_REQUEST_ACK_FILE_INFO = 17;
    public static final short PACKAGE_TYPE_DOWNLOAD_CONTENT_REQUEST = 18;
    public static final short PACKAGE_TYPE_DOWNLOAD_CONTENT_REQUEST_ACK = 19;
    public static final short PACKAGE_TYPE_DOWNLOAD_FINISH = 20;
    public static final short PACKAGE_TYPE_DOWNLOAD_FINISH_ACK_SIZE_CHANGE = 21;
    public static final short PACKAGE_TYPE_DOWNLOAD_FINISH_ACK_SUCCESS = 22;
    public static final short PACKAGE_TYPE_DOWNLOAD_CHECK_MD5 = 23;
    public static final short PACKAGE_TYPE_DOWNLOAD_CHECK_MD5_ACK = 24;
    public static final short PACKAGE_TYPE_CLIENT_HEARTBEAT_SYNC = 25;
    public static final short PACKAGE_TYPE_CLIENT_HEARTBEAT_SYNC_ACK = 26;
    public static final short PACKAGE_TYPE_CLIENT_CONFIG_SYNC = 27;
    public static final short PACKAGE_TYPE_CLIENT_BUSSINESS_CONFIG_SYNC = 28;
    public static final short PACKAGE_TYPE_CLIENT_CONFIG = 29;
    public static final short PACKAGE_TYPE_CLIENT_BUSSINESS_CONFIG = 30;
    public static final short PACKAGE_TYPE_CLIENT_CONFIG_ACK = 31;
    public static final short PACKAGE_TYPE_CLIENT_BUSSINESS_CONFIG_ACK = 32;
    public static final short PACKAGE_TYPE_UPLOAD_CANCEL = 33;
    public static final short PACKAGE_TYPE_UPLOAD_CANCEL_ACK = 34;

    public FTProtocolDecoder() {
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List out) {
        int package_len = msg.content().readableBytes();

        //----------------------------------------------------------------
        // 消息包的头部必定有4个字节，表示消息包的类型和消息包的大小
        //----------------------------------------------------------------
        if (package_len < 4) {
            return;
        }

        short type = msg.content().readShort();
        short length = msg.content().readShort();

        //----------------------------------------------------------------
        // 接收的消息大小不能小于消息包的大小
        //----------------------------------------------------------------
        if (length <= 0 || package_len < length) {
            return;
        }

        //----------------------------------------------------------------
        // 针对不同的消息类型获取对应的消息包数据
        //----------------------------------------------------------------
        try {
            FTPackage ftPackage;

            if (type == PACKAGE_TYPE_CLIENT_REGISTER) {
                ftPackage = new RegisterClientPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_UPLOAD_FILE) {
                ftPackage = new UploadNewFilePackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_UPLOAD_BLOCK_FILE) {
                ftPackage = new UploadBlockFilePackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_UPLOAD_BLOCK_DATA) {
                ftPackage = new UploadDataPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_UPLOAD_BLOCK_FILE_FINISH) {
                ftPackage = new UploadPartitionFinishPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_UPLOAD_FILE_FINISH) {
                ftPackage = new UploadFileFinishPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_UPLOAD_CANCEL) {
                ftPackage = new UploadCancelPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_DOWNLOAD_REQUEST) {
                ftPackage = new DownloadRequestPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_DOWNLOAD_CONTENT_REQUEST) {
                ftPackage = new DownloadContentPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_DOWNLOAD_FINISH) {
                ftPackage = new DownloadFinishPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_DOWNLOAD_CHECK_MD5) {
                ftPackage = new DownloadCheckMD5Package(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_CLIENT_HEARTBEAT_SYNC) {
                ftPackage = new ClientHeartbeatPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_CLIENT_CONFIG_SYNC) {
                ftPackage = new ClientConfigSyncPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_CLIENT_BUSSINESS_CONFIG_SYNC) {
                ftPackage = new ClientBussinessConfigSyncPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_CLIENT_CONFIG) {
                ftPackage = new ClientConfigPackage(msg.content().readBytes(package_len - 4));
            } else if (type == PACKAGE_TYPE_CLIENT_BUSSINESS_CONFIG) {
                ftPackage = new ClientBussinessConfigPackage(msg.content().readBytes(package_len - 4));
            } else {
                return;
            }

            ftPackage.setPackageType(type);
            ftPackage.setRecipient(msg.sender());
            out.add(ftPackage);
        } catch (Exception ignored) {
        }
    }
}
