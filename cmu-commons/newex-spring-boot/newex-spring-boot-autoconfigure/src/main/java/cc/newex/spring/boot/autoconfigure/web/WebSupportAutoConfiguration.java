package cc.newex.spring.boot.autoconfigure.web;

import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.converter.XssFastJsonHttpMessageConverter;
import cc.newex.commons.support.filter.ContextInitDataFilter;
import cc.newex.commons.support.i18n.CustomCookieLocaleResolver;
import cc.newex.commons.support.i18n.MultiTerminalLocaleChangeInterceptor;
import cc.newex.commons.support.interceptor.ApiEnabledForInterceptor;
import cc.newex.commons.support.resolver.CurrentUserMethodArgumentResolver;
import cc.newex.commons.support.resolver.JwtCurrentUserIdMethodArgumentResolver;
import cc.newex.commons.support.resolver.JwtCurrentUserMethodArgumentResolver;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.boot.autoconfigure.web.ErrorProperties.IncludeStacktrace;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import javax.servlet.Servlet;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Order(100)
@Configuration
@ConditionalOnClass({
        Servlet.class,
        DispatcherServlet.class,
        WebMvcConfigurerAdapter.class
})
@ConditionalOnProperty(value = "newex.web.support.enabled", matchIfMissing = true)
@EnableConfigurationProperties({
        WebAppEnvProperties.class,
        WebSupportProperties.class
})
public class WebSupportAutoConfiguration
        extends WebMvcConfigurerAdapter implements InitializingBean {

    private final WebAppEnvProperties webAppEnvProperties;
    private final WebSupportProperties webSupportProperties;

    public WebSupportAutoConfiguration(final WebAppEnvProperties webAppEnvProperties,
                                       final WebSupportProperties webSupportProperties) {
        this.webAppEnvProperties = webAppEnvProperties;
        this.webSupportProperties = webSupportProperties;
    }

    @Bean
    @Primary
    @ConditionalOnProperty(prefix = "newex.web.support.custom-error", name = "enabled", matchIfMissing = true)
    public CustomErrorController customErrorController(final ErrorAttributes errorAttributes) {
        return new CustomErrorController(errorAttributes, this.errorProperties());
    }

    /**
     * 在系统启动时加一些初始化数据到request上下文中
     *
     * @return FilterRegistrationBean
     */
    @Bean
    @ConditionalOnProperty(prefix = "newex.web.support.init-data-filter", name = "enabled")
    public FilterRegistrationBean contextInitDataFilterRegistrationBean() {
        final FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new ContextInitDataFilter());
        registrationBean.addUrlPatterns("/*");
        registrationBean.addInitParameter(AppEnvConsts.APP_NAME_ITEM, this.webAppEnvProperties.getName());
        registrationBean.addInitParameter(AppEnvConsts.VERSION_ITEM, this.webAppEnvProperties.getVersion());
        registrationBean.addInitParameter(AppEnvConsts.RANDOM_ITEM, String.valueOf(RandomUtils.nextFloat(0, 1)));
        registrationBean.setName("contextInitDataFilter");
        return registrationBean;
    }

    @Bean
    @ConditionalOnProperty(prefix = "newex.web.support.servlet-container", name = "enabled", matchIfMissing = true)
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/customError/401"));
            container.addErrorPages(new ErrorPage(HttpStatus.FORBIDDEN, "/customError/403"));
            container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/customError/404"));
            container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/customError"));
        };
    }

    @Bean
    @ConditionalOnMissingBean
    public ErrorProperties errorProperties() {
        final ErrorProperties properties = new ErrorProperties();
        properties.setIncludeStacktrace(
                AppEnvConsts.isProductionMode() ? IncludeStacktrace.NEVER : IncludeStacktrace.ALWAYS
        );
        return properties;
    }

    @Bean
    @ConditionalOnProperty(prefix = "newex.web.support.message-converter", name = "enabled", matchIfMissing = true)
    public XssFastJsonHttpMessageConverter messageConverter() {
        final XssFastJsonHttpMessageConverter converter = new XssFastJsonHttpMessageConverter();
        final FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteBigDecimalAsPlain);
        converter.setSupportedMediaTypes(Arrays.asList(
                MediaType.APPLICATION_JSON,
                MediaType.valueOf("feign/json")
        ));
        converter.setFastJsonConfig(fastJsonConfig);
        return converter;
    }

    @Bean
    @ConditionalOnMissingBean
    public LocaleResolver localeResolver() {
        final CookieLocaleResolver localeResolver = new CustomCookieLocaleResolver();
        //保存7天有效
        localeResolver.setLanguageTagCompliant(true);
        localeResolver.setCookieMaxAge(604800);
        localeResolver.setDefaultLocale(Locale.CHINA);
        localeResolver.setCookieName("locale");
        localeResolver.setCookiePath("/");
        return localeResolver;
    }

    @Bean
    @ConditionalOnMissingBean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        return new MultiTerminalLocaleChangeInterceptor();
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiEnabledForInterceptor apiEnabledForInterceptor() {
        return new ApiEnabledForInterceptor(this.webAppEnvProperties.getDomain());
    }

    @Bean
    @ConditionalOnMissingBean(name = "currentUserMethodArgumentResolver")
    public HandlerMethodArgumentResolver currentUserMethodArgumentResolver() {
        return new CurrentUserMethodArgumentResolver();
    }

    @Bean
    @ConditionalOnMissingBean(name = "jwtCurrentUserMethodArgumentResolver")
    public HandlerMethodArgumentResolver jwtCurrentUserMethodArgumentResolver() {
        return new JwtCurrentUserMethodArgumentResolver();
    }

    @Bean
    @ConditionalOnMissingBean(name = "JwtCurrentUserIdMethodArgumentResolver")
    public HandlerMethodArgumentResolver jwtCurrentUserIdMethodArgumentResolver() {
        return new JwtCurrentUserIdMethodArgumentResolver();
    }

    @Override
    public void addArgumentResolvers(final List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(this.currentUserMethodArgumentResolver());
        argumentResolvers.add(this.jwtCurrentUserMethodArgumentResolver());
        argumentResolvers.add(this.jwtCurrentUserIdMethodArgumentResolver());
    }

    @Override
    public void configureDefaultServletHandling(final DefaultServletHandlerConfigurer configurer) {
        configurer.enable();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        registry.addInterceptor(this.localeChangeInterceptor());
        registry.addInterceptor(this.apiEnabledForInterceptor());
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        AppEnvConsts.setEnvName(this.webAppEnvProperties.getEnv().getName());
        AppEnvConsts.setAppName(this.webAppEnvProperties.getName());
        AppEnvConsts.setDomain(this.webAppEnvProperties.getDomain());
        AppEnvConsts.setRandom(RandomUtils.nextFloat(0, 2));
        AppEnvConsts.setSupportedLocales(
                StringUtils.split(this.webAppEnvProperties.getSupportedLocales().toLowerCase(), '|')
        );
    }
}
