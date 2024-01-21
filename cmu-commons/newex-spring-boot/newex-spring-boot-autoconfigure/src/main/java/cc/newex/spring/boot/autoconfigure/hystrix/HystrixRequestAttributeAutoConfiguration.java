package cc.newex.spring.boot.autoconfigure.hystrix;

import com.netflix.hystrix.Hystrix;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author newex-team
 * @date 2018-07-14
 */
@Configuration
@ConditionalOnClass({Hystrix.class})
@ConditionalOnProperty(value = "newex.hystrix.request-attributes.enabled")
@EnableConfigurationProperties(HystrixRequestAttributeProperties.class)
public class HystrixRequestAttributeAutoConfiguration {

    @Bean
    public RequestAttributeHystrixConcurrencyStrategy hystrixRequestAutoConfiguration() {
        return new RequestAttributeHystrixConcurrencyStrategy();
    }
}