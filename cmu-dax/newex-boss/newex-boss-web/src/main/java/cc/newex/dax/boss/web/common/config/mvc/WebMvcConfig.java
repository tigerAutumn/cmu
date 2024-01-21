package cc.newex.dax.boss.web.common.config.mvc;

import cc.newex.commons.support.resolver.CurrentUserMethodArgumentResolver;
import cc.newex.dax.boss.web.common.aop.interceptor.SetRequestAttributeInterceptor;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.Arrays;
import java.util.List;

/**
 * spring mvc 配置类
 *
 * @author newex-team
 * @date 2017-03-18
 **/
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public HandlerMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect(new GroupingStrategy());
    }

    @Bean
    public SetRequestAttributeInterceptor setRequestAttributeInterceptor() {
        return new SetRequestAttributeInterceptor();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.setRequestAttributeInterceptor());
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(this.currentUserMethodArgumentResolver());
    }

    @Bean(name = "customMessageConverter")
    public HttpMessageConverter messageConverter() {
        final FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        final FastJsonConfig fastJsonConfig = new FastJsonConfig();
        converter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_JSON,
                MediaType.valueOf("feign/json")
        ));
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }

    @Override
    public void addResourceHandlers(final ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/")
                .setCachePeriod(31556926);
        registry
                .addResourceHandler("/custom/**")
                .addResourceLocations("classpath:/static/custom/")
                .setCachePeriod(31556926);
        registry
                .addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/")
                .setCachePeriod(31556926);
        registry
                .addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/")
                .setCachePeriod(31556926);
        registry
                .addResourceHandler("/vendor/**")
                .addResourceLocations("classpath:/static/vendor/")
                .setCachePeriod(31556926);
    }
}

