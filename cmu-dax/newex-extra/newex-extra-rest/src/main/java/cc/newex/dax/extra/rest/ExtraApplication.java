package cc.newex.dax.extra.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * The type Extra application.
 *
 * @author newex -team
 */
@EnableCircuitBreaker
@EnableEurekaClient
@SpringBootApplication
public class ExtraApplication {

    /**
     * Main.
     *
     * @param args the args
     */
    public static void main(final String[] args) {
        new SpringApplicationBuilder(ExtraApplication.class).run(args);
    }
}
