package cc.newex.dax.market.job.config;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.web.CommonErrorController;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
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
        "cc.newex.dax.market"},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {CommonErrorController.class, LocaleUtils.class})
)
@EnableFeignClients(basePackages = {
        "cc.newex.dax.integration.client"
})
public class MainConfig {
}
