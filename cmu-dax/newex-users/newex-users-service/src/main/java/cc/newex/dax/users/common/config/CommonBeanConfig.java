package cc.newex.dax.users.common.config;

import cc.newex.dax.users.common.util.GoogleAuthenticator;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Configuration
public class CommonBeanConfig {
    @Bean
    public GoogleAuthenticator googleAuthenticator() {
        return new GoogleAuthenticator();
    }

    @Bean
    public RestTemplate restTemplate(final RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .messageConverters(new MappingJackson2HttpMessageConverter())
                .build();
    }
}
