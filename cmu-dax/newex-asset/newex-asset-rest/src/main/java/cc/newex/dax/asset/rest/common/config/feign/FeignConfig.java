package cc.newex.dax.asset.rest.common.config.feign;

import cc.newex.spring.boot.autoconfigure.feign.HeaderRequestInterceptor;
import feign.RequestTemplate;
import org.springframework.context.annotation.Configuration;

/**
 * @author lilaizhen
 */
@Configuration
public class FeignConfig extends HeaderRequestInterceptor {
    @Override
    public void apply(RequestTemplate template) {
        super.apply(template);
    }
}
