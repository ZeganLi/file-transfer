//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.handler;

import com.aww.common.utils.DateUtils;
import com.aww.fts.file.FTSFileInputStream;
import com.aww.fts.modules.transfer.model.DownloadFile;
import com.aww.fts.modules.transfer.service.DownloadFileService;
import com.aww.fts.modules.transfer.service.DownloadService;
import com.aww.fts.socket.netty.model.DownloadCheckMD5AckPackage;
import com.aww.fts.socket.netty.model.DownloadCheckMD5Package;
import com.aww.fts.socket.netty.model.FTPackage;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DownloadCheckMD5BasePackageHandler extends BasePackageHandler {
    @Autowired
    DownloadFileService downloadFileService;
    @Autowired
    DownloadService downloadService;
    private static Logger logger = Logger.getLogger(DownloadCheckMD5BasePackageHandler.class);

    public DownloadCheckMD5BasePackageHandler() {
    }

    @Override
    public boolean support(short package_type) {
        return package_type == 23;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
        try {
            DownloadCheckMD5Package checkMD5Package = (DownloadCheckMD5Package) ftpackage;
            DownloadFile downloadFile = this.downloadFileService.getFileById(checkMD5Package.getFileId());
            if (downloadFile != null && checkMD5Package.getClientId() == downloadFile.getClientId()) {
                this.downloadService.removeFile(downloadFile.getKey());
                downloadFile.setAvgTr(checkMD5Package.getAvgTr());
                downloadFile.setMinTr(checkMD5Package.getMinTr());
                downloadFile.setMaxTr(checkMD5Package.getMaxTr());
                downloadFile.setTraffic(checkMD5Package.getTranffic());
                downloadFile.setStatus(DownloadFile.DOWNLOAD_FILE_STATUS_COMPLETE);
                downloadFile.setCompleteTime(DateUtils.now());
                this.downloadFileService.complete(downloadFile);
                FTSFileInputStream.close(downloadFile.getKey());
                this.ok(ctx, checkMD5Package);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void ok(ChannelHandlerContext ctx, DownloadCheckMD5Package checkMD5Package) {
        DownloadCheckMD5AckPackage ack = new DownloadCheckMD5AckPackage();
        ack.setClientId(checkMD5Package.getClientId());
        ack.setFileId(checkMD5Package.getFileId());
        ack.setRecipient(checkMD5Package.getRecipient());
        this.response(ctx, ack);
    }
}
