package tran.server.io.netty.decoder;

import io.netty.channel.socket.DatagramPacket;
import tran.server.io.netty.model.FTPackage;
import tran.server.io.netty.model.RegisterClientPackage;

import java.util.HashMap;

/**
 * @Description 包类型编码表
 * @Author mrliz
 * @Date 2021/3/21 12:47
 */
public class PackageTypeCode {

    private static final HashMap<Short, FTPackage> packageCodeMap = new HashMap<>(50);

//    public static FTPackage getFTPackage(short code, DatagramPacket msg, int len){
//        packageCodeMap.get(code);
//        return null;
//    }

    public static final short PACKAGE_HEAD_LENGTH = 4;

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
}
