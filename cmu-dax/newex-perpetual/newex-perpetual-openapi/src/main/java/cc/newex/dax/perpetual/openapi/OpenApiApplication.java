package cc.newex.dax.perpetual.openapi;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author newex-team
 * @date 2018-12-03
 */
@Slf4j
@EnableScheduling
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class OpenApiApplication {

    public static void main(final String[] args) {
        SpringApplication.run(OpenApiApplication.class, args);
    }

}
