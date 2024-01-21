package cc.newex.dax.market.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author allen
 */
@SpringBootApplication
@EnableCircuitBreaker
@EnableEurekaClient
public class MarketApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MarketApplication.class, args);
    }
}
