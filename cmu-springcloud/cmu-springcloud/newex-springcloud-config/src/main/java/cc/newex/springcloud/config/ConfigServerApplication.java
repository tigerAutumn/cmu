package cc.newex.springcloud.config;

import cc.newex.springcloud.config.encryption.NotRemoveCipherEnvironmentEncryptor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.config.server.encryption.EnvironmentEncryptor;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableConfigServer
@EnableEurekaClient
public class ConfigServerApplication {

    public static void main(final String[] args) {
        SpringApplication.run(ConfigServerApplication.class, args);
    }

    @Bean
    public EnvironmentEncryptor environmentEncryptor() {
        return new NotRemoveCipherEnvironmentEncryptor();
    }
}
