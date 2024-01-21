package cc.newex.dax.market.spider.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 根配置类，自动扫描basePackages下的所有bean
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Configuration
@ComponentScan(basePackages = {
        "cc.newex.dax.market.spider"
})
public class MainConfig {

}
