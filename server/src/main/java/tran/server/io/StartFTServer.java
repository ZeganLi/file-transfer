//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io;

import com.aww.fts.modules.sys.service.SysParamService;
import com.aww.fts.socket.netty.NettyServer;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

public class StartFTServer implements ApplicationListener<ContextRefreshedEvent> {
    private static Logger logger = Logger.getLogger(com.aww.fts.socket.StartFTServer.class);
    @Autowired
    SysParamService sysParamService;

    public StartFTServer() {
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        String port = this.sysParamService.getParamValueByName("port");
        NettyServer.startServer(Integer.parseInt(port));
    }
}
