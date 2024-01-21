package cc.newex.spring.boot.autoconfigure.ucenter;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author newex-team
 * @date 2018-06-11
 */
@Configuration
@EnableConfigurationProperties(UCenterProperties.class)
public class UCenterAutoConfiguration {
    private final UCenterProperties properties;

    public UCenterAutoConfiguration(final UCenterProperties properties) {
        this.properties = properties;
    }
}


