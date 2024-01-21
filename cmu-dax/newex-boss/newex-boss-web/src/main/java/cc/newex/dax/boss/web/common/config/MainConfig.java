package cc.newex.dax.boss.web.common.config;

import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * 根配置类，自动扫描basePackages下的所有bean
 *
 * @author newex -team
 * @date 2017 -03-18
 */
@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = {
        "cc.newex.commons",
        "cc.newex.dax.boss"})
@EnableFeignClients(basePackages = {
        "cc.newex.dax.spot.client",
        "cc.newex.dax.asset.client",
        "cc.newex.dax.extra.client",
        "cc.newex.dax.users.client",
        "cc.newex.dax.c2c.client",
        "cc.newex.dax.integration.client",
        "cc.newex.dax.activity.client",
        "cc.newex.dax.market.client",
        "cc.newex.dax.portfolio.client",
        "cc.newex.dax.perpetual.client"
})
public class MainConfig {


    /**
     * Model mapper model mapper.
     *
     * @return the model mapper
     */
    @Bean
    @ConditionalOnMissingBean(name = "modelMapper")
    @ConditionalOnClass(value = ModelMapper.class)
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
