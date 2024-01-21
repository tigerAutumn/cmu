package cc.newex.dax.users.rest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@EnableEurekaClient
@EnableCircuitBreaker
@SpringBootApplication
public class UsersApplication {

    public static void main(final String[] args) {
        new SpringApplicationBuilder(UsersApplication.class).run(args);
    }
}
