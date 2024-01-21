package cc.newex.dax.extra.rest.common.config;

import cc.newex.spring.boot.autoconfigure.broker.EnableBrokerConfig;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 根配置类，自动扫描basePackages下的所有bean
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Configuration
@EnableTransactionManagement
@EnableBrokerConfig
@ComponentScan(basePackages = {
        "cc.newex.commons",
        "cc.newex.dax.extra"})
@EnableFeignClients(basePackages = {
        "cc.newex.dax.spot.client",
        "cc.newex.dax.asset.client",
        "cc.newex.dax.users.client"
})
public class MainConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
