package tran.server.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import tran.server.io.netty.NettyServer;

/**
 * ApplicationListener:在所有bean创建完成之后调用
 */
@Slf4j
public class StartFTServer implements ApplicationListener<ContextRefreshedEvent> {
    @Value("ft.port")
    private int port;

    public StartFTServer() {
    }

    public void onApplicationEvent(ContextRefreshedEvent event) {
        NettyServer.startServer(port);
    }
}
