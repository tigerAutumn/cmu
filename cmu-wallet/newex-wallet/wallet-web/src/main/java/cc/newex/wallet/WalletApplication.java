package cc.newex.wallet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author newex-team
 * @data 26/03/2018
 */
@SpringBootApplication(scanBasePackages = {"cc.newex.wallet", "cc.newex.commons"})
@EnableEurekaClient
@EnableFeignClients(basePackages = {"cc.newex.wallet", "cc.newex.dax"})
@EnableScheduling
public class WalletApplication {
    public static void main(final String[] args) {
        SpringApplication.run(WalletApplication.class, args);
    }
}
