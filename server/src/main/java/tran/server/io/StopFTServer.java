//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package tran.server.io;

import com.aww.fts.socket.netty.NettyServer;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

public class StopFTServer implements ApplicationListener<ContextClosedEvent> {
    private static Logger logger = Logger.getLogger(com.aww.fts.socket.StopFTServer.class);

    public StopFTServer() {
    }

    public void onApplicationEvent(ContextClosedEvent event) {
        NettyServer.stopServer();
    }
}
