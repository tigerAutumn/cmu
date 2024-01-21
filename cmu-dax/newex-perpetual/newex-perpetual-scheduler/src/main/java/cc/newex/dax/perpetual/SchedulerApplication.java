package cc.newex.dax.perpetual;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableCircuitBreaker
@EnableEurekaClient
@EnableScheduling
@SpringBootApplication
public class SchedulerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(SchedulerApplication.class, args);
    }
}
