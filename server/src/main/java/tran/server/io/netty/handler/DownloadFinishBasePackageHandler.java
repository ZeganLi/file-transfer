//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.handler;

import com.aww.common.utils.Utility;
import com.aww.fts.modules.transfer.model.DownloadFile;
import com.aww.fts.modules.transfer.service.DownloadFileService;
import com.aww.fts.modules.transfer.service.DownloadService;
import com.aww.fts.socket.netty.model.DownloadFinishAckPackage;
import com.aww.fts.socket.netty.model.DownloadFinishAckSizeChangePackage;
import com.aww.fts.socket.netty.model.DownloadFinishPackage;
import com.aww.fts.socket.netty.model.FTPackage;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DownloadFinishBasePackageHandler extends BasePackageHandler {
    @Autowired
    DownloadFileService downloadFileService;
    @Autowired
    DownloadService downloadService;
    private static Logger logger = Logger.getLogger(DownloadFinishBasePackageHandler.class);

    public DownloadFinishBasePackageHandler() {
    }

    @Override
    public boolean support(short package_type) {
        return package_type == 20;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
        try {
            DownloadFinishPackage downloadFinishPackage = (DownloadFinishPackage) ftpackage;
            DownloadFile downloadFile = this.downloadFileService.getFileById(downloadFinishPackage.getFileId());
            if (downloadFile != null && downloadFinishPackage.getClientId() == downloadFile.getClientId()) {
                this.downloadService.removeFile(downloadFile.getKey());
                this.downloadFileService.updateStatus(downloadFinishPackage.getFileId(), DownloadFile.DOWNLOAD_FILE_STATUS_TRANSLATE_COMPLETE);
                File file = new File(downloadFile.getPath());
                if (file.exists()) {
                    if (file.length() != downloadFinishPackage.getLength()) {
                        this.sizechange(ctx, downloadFinishPackage, file.length());
                    } else {
                        this.ok(ctx, downloadFinishPackage, downloadFile);
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void ok(ChannelHandlerContext ctx, DownloadFinishPackage downloadRequestPackage, DownloadFile downloadFile) {
        DownloadFinishAckPackage ack = new DownloadFinishAckPackage();
        ack.setPackageType((short)22);
        ack.setClientId(downloadRequestPackage.getClientId());
        ack.setFileId(downloadRequestPackage.getFileId());
        ack.setMd5(Utility.hexStringToBytes(downloadFile.getMd5()));
        ack.setRecipient(downloadRequestPackage.getRecipient());
        this.response(ctx, ack);
    }

    private void sizechange(ChannelHandlerContext ctx, DownloadFinishPackage downloadRequestPackage, long length) {
        DownloadFinishAckSizeChangePackage ack = new DownloadFinishAckSizeChangePackage();
        ack.setClientId(downloadRequestPackage.getClientId());
        ack.setFileId(downloadRequestPackage.getFileId());
        ack.setLength(length);
        ack.setRecipient(downloadRequestPackage.getRecipient());
        this.response(ctx, ack);
    }
}
