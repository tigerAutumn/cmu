package cc.newex.spring.boot.autoconfigure.feign;

import feign.Feign;
import feign.RequestInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author newex-team
 * @date 2018-05-04
 */
@Configuration
@ConditionalOnClass(Feign.class)
@EnableConfigurationProperties(MyFeignProperties.class)
public class MyFeignAutoConfiguration {

    @Bean
    public RequestInterceptor headerRequestInterceptor() {
        return new HeaderRequestInterceptor();
    }
}
