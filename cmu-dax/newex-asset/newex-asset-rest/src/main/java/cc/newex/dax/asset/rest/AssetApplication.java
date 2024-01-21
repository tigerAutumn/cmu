package cc.newex.dax.asset.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author newex-team
 */
@SpringBootApplication(scanBasePackages = {"cc.newex.commons", "cc.newex.dax"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"cc.newex.wallet", "cc.newex.dax", "cc.newex.dax.extra.client.inner"})
@EnableScheduling
@EnableRetry
public class AssetApplication {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(AssetApplication.class).run(args);
    }

}
