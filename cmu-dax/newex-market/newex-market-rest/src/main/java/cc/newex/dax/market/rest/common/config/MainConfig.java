package cc.newex.dax.market.rest.common.config;

import cc.newex.dax.market.common.config.MarketProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 根配置类，自动扫描basePackages下的所有bean
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
@Configuration
@EnableTransactionManagement
@EnableConfigurationProperties({MarketProperties.class})
@ComponentScan(basePackages = {
        "cc.newex.commons",
        "cc.newex.dax.market"})
public class MainConfig {
}
