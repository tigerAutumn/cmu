package cc.newex.dax.boss.web;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author newex-team
 */
@EnableEurekaClient
@SpringBootApplication
public class BossApplication {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(BossApplication.class).run(args);
    }
}
