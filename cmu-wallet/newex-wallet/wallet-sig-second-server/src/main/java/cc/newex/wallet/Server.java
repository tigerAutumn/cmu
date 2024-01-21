package cc.newex.wallet;

import cc.newex.wallet.config.MasterNodeConfig;
import cc.newex.wallet.signature.KeyConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * Hello world!
 */
@SpringBootApplication(scanBasePackages = {"cc.newex.wallet"})
@EnableScheduling
@Slf4j
public class Server {
    public static void main(final String[] args) {
        SpringApplication.run(Server.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner() {

        return new ApplicationRunner() {
            @Override
            public void run(final ApplicationArguments args) throws Exception {
                KeyConfig.init(MasterNodeConfig.masterNode);
            }
        };
    }
}
