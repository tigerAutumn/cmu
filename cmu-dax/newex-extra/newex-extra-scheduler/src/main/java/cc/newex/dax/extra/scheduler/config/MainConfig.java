package cc.newex.dax.extra.scheduler.config;

import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.web.CommonErrorController;
import org.modelmapper.ModelMapper;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
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
        "cc.newex.commons.support",
        "cc.newex.commons.mybatis.readwrite",
        "cc.newex.dax.extra"},
        excludeFilters = @ComponentScan.Filter(
                type = FilterType.ASSIGNABLE_TYPE,
                classes = {CommonErrorController.class, LocaleUtils.class})
)
@EnableFeignClients(basePackages = {
        "cc.newex.dax.spot.client",
        "cc.newex.dax.users.client"
})
public class MainConfig {

    @Bean
    ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
