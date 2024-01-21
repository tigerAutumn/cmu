package cc.newex.dax.integration.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author newex-team
 */
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
@EnableScheduling
public class IntegrationApplication {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(IntegrationApplication.class).run(args);
    }
}
