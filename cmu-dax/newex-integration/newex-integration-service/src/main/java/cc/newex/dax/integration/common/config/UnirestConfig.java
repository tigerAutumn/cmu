package cc.newex.dax.integration.common.config;

import com.mashape.unirest.http.Unirest;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class UnirestConfig {
    @PostConstruct
    void init() {
        Unirest.setTimeouts(5000, 5000);
    }
}
