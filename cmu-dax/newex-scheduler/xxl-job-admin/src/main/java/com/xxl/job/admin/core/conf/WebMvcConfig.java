package com.xxl.job.admin.core.conf;

import com.xxl.job.admin.controller.interceptor.CookieInterceptor;
import com.xxl.job.admin.controller.interceptor.PermissionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

/**
 * spring mvc 配置类
 *
 * @author xin.hui
 * @date 2018-03-07
 **/
@Configuration
public class WebMvcConfig extends WebMvcConfigurerAdapter {

    @Bean
    public FreeMarkerViewResolver freeMarkerViewResolver() {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("");
        resolver.setSuffix(".ftl");
        resolver.setContentType("text/html; charset=UTF-8");
        resolver.setRequestContextAttribute("request");
        return resolver;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        /**将所有/static/** 访问都映射到classpath:/static/ 目录下*/
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**权限拦截器配置*/
    @Bean
    public HandlerInterceptor getPermissionInterceptor(){
        return new PermissionInterceptor();
    }
    /**cookie拦截器配置*/
    @Bean
    public HandlerInterceptor getCookieInterceptor(){
        return new CookieInterceptor();
    }

    /**
     * addPathPatterns 用于添加拦截规则
     * excludePathPatterns 用户排除拦截
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(getPermissionInterceptor()).addPathPatterns("/**").excludePathPatterns("/**.ftl","logout");
        registry.addInterceptor(getCookieInterceptor()).addPathPatterns("/**");
        super.addInterceptors(registry);
    }

}
