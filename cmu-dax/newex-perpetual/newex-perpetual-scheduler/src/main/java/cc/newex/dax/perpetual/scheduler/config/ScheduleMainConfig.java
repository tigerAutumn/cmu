package cc.newex.dax.perpetual.scheduler.config;

import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = {
        "cc.newex.commons.support",
        "cc.newex.commons.mybatis.readwrite"})
@EnableFeignClients(basePackages = {
        "cc.newex.dax.asset.client",
        "cc.newex.dax.perpetual",
        "cc.newex.dax.integration.client",
        "cc.newex.dax.users.client",
        "cc.newex.dax.market.client"
})
public class ScheduleMainConfig {
}
