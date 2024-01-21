package cc.newex.dax.portal.common.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 根配置类，自动扫描basePackages下的所有bean
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Configuration
@ComponentScan({
        "cc.newex.commons",
        "cc.newex.dax.portal"
})
public class MainConfig {

}
