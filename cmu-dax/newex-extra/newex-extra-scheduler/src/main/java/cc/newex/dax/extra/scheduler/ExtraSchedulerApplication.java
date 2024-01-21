package cc.newex.dax.extra.scheduler;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@SpringBootApplication
@EnableEurekaClient
public class ExtraSchedulerApplication {

    public static void main(final String[] args) {
        final SpringApplication application = new SpringApplication(ExtraSchedulerApplication.class);
        application.run(args);
    }
}
