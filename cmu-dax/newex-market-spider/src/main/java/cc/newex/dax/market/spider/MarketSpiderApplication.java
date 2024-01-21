package cc.newex.dax.market.spider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author newex-team allen
 */
@SpringBootApplication
@EnableScheduling
public class MarketSpiderApplication {

    public static void main(final String[] args) {
        SpringApplication.run(MarketSpiderApplication.class, args);
    }
}
