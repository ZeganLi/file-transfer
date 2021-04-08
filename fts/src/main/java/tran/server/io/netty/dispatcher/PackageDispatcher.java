package tran.server.io.netty.dispatcher;

import io.netty.channel.ChannelHandlerContext;
import tran.common.utils.SpringContextUtils;
import tran.server.io.netty.handler.*;
import tran.server.io.netty.model.FTPackage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import static tran.server.io.netty.decoder.PackageTypeCode.*;

public class PackageDispatcher {
    private static final ExecutorService m_ThreadPool = Executors.newCachedThreadPool();
    private static final long messageCount = 0;

    public PackageDispatcher() {
    }

    public static void dispatch(ChannelHandlerContext ctx, Object message) {

        try {
            //----------------------------------------------------------------
            // 消息为空时不处理
            //----------------------------------------------------------------
            if (message == null) {
                return;
            }

            //----------------------------------------------------------------
            // 获取消息类型
            //----------------------------------------------------------------
            FTPackage ftsPackage = (FTPackage) message;
            int packageType = ftsPackage.getPackageType();

            //----------------------------------------------------------------
            // 获取不同类型消息的处理器对象
            //----------------------------------------------------------------
            BasePackageHandler basePackageHandler;

            if (packageType == PACKAGE_TYPE_CLIENT_REGISTER) {                      // 终端注册
                basePackageHandler = (RegisterClientBasePackageHandler) SpringContextUtils.getBean("registerClientPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_UPLOAD_FILE) {               // 上传文件
//                basePackageHandler = (UploadNewFileBasePackageHandler) SpringContextUtils.getBean("uploadNewFilePackageHandler");
//
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_UPLOAD_BLOCK_FILE) {             // 上传文件块
//                basePackageHandler = (UploadBlockFileBasePackageHandler) SpringContextUtils.getBean("uploadBlockFilePackageHandler");
//
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_UPLOAD_BLOCK_DATA) {                   // 上传文件块数据
//                basePackageHandler = (UploadDataBasePackageHandler) SpringContextUtils.getBean("uploadDataPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_UPLOAD_BLOCK_FILE_FINISH) {       // 上传文件块完成
//                basePackageHandler = (UploadPartitionFinishBasePackageHandler) SpringContextUtils.getBean("uploadPartitionFinishPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_UPLOAD_FILE_FINISH) {            // 上传文件完成
//                basePackageHandler = (UploadFileFinishBasePackageHandler) SpringContextUtils.getBean("uploadFileFinishPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_UPLOAD_CANCEL) {                 // 上传文件取消
//                basePackageHandler = (UploadCancelBasePackageHandler) SpringContextUtils.getBean("uploadCancelPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_DOWNLOAD_REQUEST) {
//                basePackageHandler = (DownloadRequestBasePackageHandler) SpringContextUtils.getBean("downloadRequestPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_DOWNLOAD_CONTENT_REQUEST) {
//                basePackageHandler = (DownloadContentBasePackageHandler) SpringContextUtils.getBean("downloadContentPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_DOWNLOAD_FINISH) {
//                basePackageHandler = (DownloadFinishBasePackageHandler) SpringContextUtils.getBean("downloadFinishPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_DOWNLOAD_CHECK_MD5) {
//                basePackageHandler = (DownloadCheckMD5BasePackageHandler) SpringContextUtils.getBean("downloadCheckMD5PackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_CLIENT_HEARTBEAT_SYNC) {
//                basePackageHandler = (ClientHeartbeatBasePackageHandler) SpringContextUtils.getBean("clientHeartbeatPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_CLIENT_CONFIG_SYNC) {
//                basePackageHandler = (ClientConfigSyncBasePackageHandler) SpringContextUtils.getBean("clientConfigSyncPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_CLIENT_BUSSINESS_CONFIG_SYNC) {
//                basePackageHandler = (ClientBussinessConfigSyncBasePackageHandler) SpringContextUtils.getBean("clientBussinessConfigSyncPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_CLIENT_CONFIG) {
//                basePackageHandler = (ClientConfigBasePackageHandler) SpringContextUtils.getBean("clientConfigPackageHandler");
//            } else if (packageType == FTProtocolDecoder.PACKAGE_TYPE_CLIENT_BUSSINESS_CONFIG) {
//                basePackageHandler = (ClientBussinessConfigBasePackageHandler) SpringContextUtils.getBean("clientBussinessConfigPackageHandler");
            } else {
                return;
            }

            //----------------------------------------------------------------
            BasePackageHandler finalBasePackageHandler = basePackageHandler;

            m_ThreadPool.submit(() -> finalBasePackageHandler.handle(ctx, ftsPackage));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
