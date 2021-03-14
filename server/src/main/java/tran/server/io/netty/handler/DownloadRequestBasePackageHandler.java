//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.handler;

import com.aww.fts.modules.transfer.model.Client;
import com.aww.fts.modules.transfer.model.DownloadFile;
import com.aww.fts.modules.transfer.service.ClientService;
import com.aww.fts.modules.transfer.service.DownloadFileService;
import com.aww.fts.socket.netty.model.*;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class DownloadRequestBasePackageHandler extends BasePackageHandler {
    @Autowired
    ClientService clientService;
    @Autowired
    DownloadFileService downloadFileService;
    private static Logger logger = Logger.getLogger(DownloadRequestBasePackageHandler.class);

    public DownloadRequestBasePackageHandler() {
    }

    @Override
    public boolean support(short package_type) {
        return package_type == 15;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
        try {
            DownloadRequestPackage downloadRequestPackage = (DownloadRequestPackage) ftpackage;
            Client client = this.clientService.getClientById(downloadRequestPackage.getClientId());
            if (client != null) {
                DownloadFile downloadFile = this.downloadFileService.getNewDownloadFile(client.getId());
                if (downloadFile != null) {
                    this.fileInfo(ctx, downloadRequestPackage, downloadFile);
                } else {
                    this.nofile(ctx, downloadRequestPackage);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void fileInfo(ChannelHandlerContext ctx, DownloadRequestPackage downloadRequestPackage, DownloadFile downloadFile) {
        DownloadRequestAckPackage ack = new DownloadRequestAckPackage();
        ack.setPackageType((short)17);
        ack.setClientId(downloadRequestPackage.getClientId());
        ack.setFileId(downloadFile.getId());
        ack.setFileName(downloadFile.getName());
        File file = new File(downloadFile.getPath());
        ack.setLength(file.length());
        ack.setIsCompressed((short)downloadFile.getIsCompressed().shortValue());

//        ack.setIsMessage((short)downloadFile.getIsMessage().shortValue());
//        ack.setSenderLength((short)downloadFile.getSender().length());
//        ack.setSender(downloadFile.getSender());
//        ack.setReceiverLength((short)downloadFile.getReceiver().length());
//        ack.setReceiver(downloadFile.getReceiver());

        ack.setLevel((short)downloadFile.getPriority().shortValue());
        ack.setRecipient(downloadRequestPackage.getRecipient());
        ack.setOldLength(downloadFile.getLength());
        this.response(ctx, ack);
    }

    private void nofile(ChannelHandlerContext ctx, DownloadRequestPackage downloadRequestPackage) {
        DownloadRequestAckNoFilePackage ack = new DownloadRequestAckNoFilePackage();
        ack.setPackageType((short)16);
        ack.setClientId(downloadRequestPackage.getClientId());
        ack.setRecipient(downloadRequestPackage.getRecipient());
        this.response(ctx, ack);
    }
}
