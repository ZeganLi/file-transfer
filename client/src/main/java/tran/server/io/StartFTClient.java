package tran.server.io;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import tran.server.io.netty.FtClient;

/**
 * ApplicationListener: 当容器初始化完成之后进行调用
 * InitializingBean：在所有bean被实例化完成之后调用
 */
@Slf4j
public class StartFTClient implements ApplicationListener<ContextRefreshedEvent> {

    @Value("${ftc.port}")
    private int port;
    public StartFTClient() {
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
//        FtClient.startClient(port);
    }
}
