package cc.newex.dax.asset.client.config;

import feign.codec.Encoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.cloud.netflix.feign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

/**
 * @author newex-team
 * @data 2018/5/6
 */
@Configuration
public class ClientConfig {
    @Bean
    public Encoder feignEncoder() {

        HttpMessageConverter jacksonConverter = new MappingJackson2HttpMessageConverter();

        ObjectFactory<HttpMessageConverters> objectFactory = () -> new HttpMessageConverters(jacksonConverter);

        return new SpringEncoder(objectFactory);

    }
}
