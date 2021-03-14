//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io.netty.handler;

import com.aww.fts.file.FTSFileInputStream;
import com.aww.fts.modules.sys.service.SysParamService;
import com.aww.fts.modules.transfer.model.Client;
import com.aww.fts.modules.transfer.model.UploadFile;
import com.aww.fts.modules.transfer.service.ClientService;
import com.aww.fts.modules.transfer.service.UploadFileService;
import com.aww.fts.socket.netty.model.FTPackage;
import com.aww.fts.socket.netty.model.UploadCancelAckPackage;
import com.aww.fts.socket.netty.model.UploadCancelPackage;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class UploadCancelBasePackageHandler extends BasePackageHandler {
    @Autowired
    ClientService clientService;
    @Autowired
    UploadFileService uploadFileService;
    @Autowired
    SysParamService sysParamService;

    public UploadCancelBasePackageHandler() {
    }

    @Override
    public boolean support(short package_type) {
        return package_type == 33;
    }

    @Override
    public void handle(ChannelHandlerContext ctx, FTPackage ftpackage) {
        try {
            UploadCancelPackage pkg = (UploadCancelPackage) ftpackage;
            Client client = this.clientService.selectByPrimaryKey(pkg.getClientId());
            if (client != null) {
                UploadFile uploadFile = this.uploadFileService.getFileById(pkg.getFileId());
                if (uploadFile != null) {
                    this.uploadFileService.deleteFile(pkg.getFileId());
                    String temp_dir = this.sysParamService.getParamValueByName("temp_dir");
                    deleteTempFile(temp_dir, uploadFile);
                }

                UploadCancelAckPackage ack = new UploadCancelAckPackage();
                ack.setClientId(pkg.getClientId());
                ack.setFileId(pkg.getFileId());
                ack.setRecipient(ftpackage.getRecipient());
                this.response(ctx, ack);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteTempFile(String temp_dir, UploadFile file) {
        try {
            int block = file.getBlock() == null ? -1 : file.getBlock();

            while(true) {
                ++block;
                String path = temp_dir + File.separator + "U" + file.getClientId() + "_" + file.getId() + "_" + block + ".tmp";
                File block_file = new File(path);
                if (!block_file.exists()) {
                    break;
                }

                FTSFileInputStream.remove("U" + file.getClientId() + "_" + file.getId() + "_" + block);
                if (block_file.exists()) {
                    block_file.delete();
                }
            }
        } catch (Exception var5) {
            var5.printStackTrace();
        }

    }
}
