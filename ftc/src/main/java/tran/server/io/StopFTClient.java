package tran.server.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import tran.server.io.netty.FtClient;

@Slf4j
public class StopFTClient implements ApplicationListener<ContextClosedEvent> {
    public StopFTClient() {
    }

    public void onApplicationEvent(ContextClosedEvent event) {
//        FtClient.stopClient();
    }
}
