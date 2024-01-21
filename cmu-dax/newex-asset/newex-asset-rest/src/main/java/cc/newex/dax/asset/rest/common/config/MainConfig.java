package cc.newex.dax.asset.rest.common.config;

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
@ComponentScan(basePackages = {
        "cc.newex.commons",
        "cc.newex.dax.asset"})
public class MainConfig {

}
