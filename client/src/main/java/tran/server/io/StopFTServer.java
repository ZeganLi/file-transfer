package tran.server.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import tran.server.io.netty.NettyServer;

@Slf4j
public class StopFTServer implements ApplicationListener<ContextClosedEvent> {
    public StopFTServer() {
    }

    public void onApplicationEvent(ContextClosedEvent event) {
        NettyServer.stopServer();
    }
}
