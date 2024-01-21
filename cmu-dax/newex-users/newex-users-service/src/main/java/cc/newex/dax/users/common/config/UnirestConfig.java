package cc.newex.dax.users.common.config;

import com.mashape.unirest.http.Unirest;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * Unirest超时设置
 *
 * @author newex-team
 * @date 2018-11-07
 */
@Configuration
public class UnirestConfig {
    @PostConstruct
    void init() {
        Unirest.setTimeouts(10000, 10000);
    }
}
