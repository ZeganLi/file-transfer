//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.handler;

import com.aww.fts.modules.sys.service.SysParamService;
import com.aww.fts.modules.transfer.model.Client;
import com.aww.fts.modules.transfer.model.UploadFile;
import com.aww.fts.modules.transfer.service.BussinessService;
import com.aww.fts.modules.transfer.service.ClientService;
import com.aww.fts.modules.transfer.service.UploadFileService;
import com.aww.fts.socket.netty.model.FTPackage;
import com.aww.fts.socket.netty.model.UploadNewFileAckPackage;
import com.aww.fts.socket.netty.model.UploadNewFilePackage;
import io.netty.channel.ChannelHandlerContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UploadNewFileBasePackageHandler extends BasePackageHandler {
    @Autowired
    ClientService clientService;
    @Autowired
    UploadFileService uploadFileService;
    @Autowired
    SysParamService sysParamService;
    @Autowired
    BussinessService bussinessService;
    private static Logger logger = Logger.getLogger(UploadNewFileBasePackageHandler.class);

    public UploadNewFileBasePackageHandler() {
    }

    @Override
    public boolean support(short package_type) {
        return package_type == 2;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
        try {
            UploadNewFilePackage newFilePackage = (UploadNewFilePackage) ftpackage;
            Client client = this.clientService.selectByPrimaryKey(newFilePackage.getClientId());
            if (client != null) {
                String upload_dir = this.sysParamService.getParamValueByName("upload_dir");
                UploadFile file = this.uploadFileService.getUploadFileByPackage(newFilePackage, upload_dir, client);

                if (file != null) {
                    UploadNewFileAckPackage ack = new UploadNewFileAckPackage();
                    ack.setClientId(file.getClientId());
                    ack.setFileId(file.getId());
                    ack.setBlock(file.getBlock() == null ? -1 : file.getBlock());
                    ack.setRecipient(ftpackage.getRecipient());
                /*if (newFilePackage.getNewKey() != null && newFilePackage.getNewKey() != ""){
                    ack.setKey(0);
                    AesUtil.KEY = newFilePackage.getNewKey();
                }*/

                    this.response(ctx, ack);
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
