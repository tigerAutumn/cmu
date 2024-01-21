package cc.newex.dax.market.rest.common.config.mvc;

import java.util.List;

import cc.newex.commons.support.resolver.CurrentUserMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * spring mvc 配置类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(currentUserMethodArgumentResolver());
    }

    @Bean
    public HandlerMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }
}
