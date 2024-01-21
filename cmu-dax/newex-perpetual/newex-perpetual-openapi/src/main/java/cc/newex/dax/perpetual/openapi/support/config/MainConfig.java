package cc.newex.dax.perpetual.openapi.support.config;

import cc.newex.spring.boot.autoconfigure.broker.EnableBrokerConfig;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 根配置类，自动扫描basePackages下的所有bean
 *
 * @author newex-team
 * @date 2018/11/01
 */
@Configuration
@EnableTransactionManagement
@EnableBrokerConfig
@ComponentScan(basePackages = {
        "cc.newex.commons",
        "cc.newex.dax.perpetual"})
@EnableFeignClients(basePackages = {
        "cc.newex.dax.users.client",
        "cc.newex.dax.asset.client",
        "cc.newex.dax.integration.client",
        "cc.newex.dax.market.client"
})
public class MainConfig {
}
