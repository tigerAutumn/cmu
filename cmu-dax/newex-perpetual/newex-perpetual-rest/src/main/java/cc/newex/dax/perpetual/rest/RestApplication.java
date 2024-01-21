package cc.newex.dax.perpetual.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class RestApplication {
    public static void main(final String[] args) {
        new SpringApplicationBuilder(RestApplication.class).run(args);
    }
}
