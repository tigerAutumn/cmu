package cc.newex.dax.portal.common.config.mvc;

import cc.newex.dax.portal.common.aop.interceptor.AppendAttributeInterceptor;
import nz.net.ultraq.thymeleaf.LayoutDialect;
import nz.net.ultraq.thymeleaf.decorators.strategies.GroupingStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * spring mvc 配置类
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {
    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect(new GroupingStrategy());
    }

    @Bean
    public AppendAttributeInterceptor appendAttributeInterceptor() {
        return new AppendAttributeInterceptor();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.appendAttributeInterceptor());
    }
}
