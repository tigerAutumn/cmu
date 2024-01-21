package cc.newex.dax.market.job;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@EnableEurekaClient
@EnableScheduling
@SpringBootApplication
@Slf4j
public class MarketSchedulerApplication {
    public static void main(final String[] args) {
        SpringApplication.run(MarketSchedulerApplication.class, args);
    }
}
