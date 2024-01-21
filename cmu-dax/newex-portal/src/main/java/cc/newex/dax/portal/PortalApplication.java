package cc.newex.dax.portal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class PortalApplication {

    public static void main(final String[] args) {
        SpringApplication.run(PortalApplication.class, args);
    }
}
