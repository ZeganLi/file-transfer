package tran.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tran.server.io.StartFTClient;

/**
 * @Description 加载spring bean的初始化
 * @Author mrliz
 * @Date 2021/3/20 8:57
 */
@Configuration
public class SpringBeanConfig {

    @Bean("startFTServer")
    public StartFTClient startFTServer(){
        return new StartFTClient();
    }
}
