package cc.newex.spring.boot.autoconfigure.openapi;

import cc.newex.commons.openapi.specs.auth.OpenApiAuthManager;
import cc.newex.commons.openapi.support.aop.OpenApiAuthInterceptor;
import cc.newex.commons.openapi.support.aop.OpenApiRateLimitInterceptor;
import cc.newex.commons.openapi.support.auth.DefaultOpenApiAuthManager;
import cc.newex.commons.openapi.support.config.OpenApiConfig;
import cc.newex.commons.ratelimiter.RateLimiter;
import cc.newex.commons.ratelimiter.limiter.RedisRateLimiter;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author newex-team
 * @date 2017/11/20
 */
@Order(101)
@Configuration
@EnableConfigurationProperties({OpenApiProperties.class})
@ConditionalOnClass(DefaultOpenApiAuthManager.class)
public class OpenApiAutoConfiguration
        extends WebMvcConfigurerAdapter implements ApplicationContextAware {
    private static final String RATE_LIMIT_INTERCEPTOR_BEAN_NAME = "openApiRateLimitInterceptor";
    private static final String AUTH_INTERCEPTOR_BEAN_NAME = "openApiAuthInterceptor";

    private ApplicationContext applicationContext;
    private final OpenApiProperties openApiProperties;

    public OpenApiAutoConfiguration(final OpenApiProperties openApiProperties) {
        this.openApiProperties = openApiProperties;
    }

    @Bean
    public OpenApiConfig openApiConfig() {
        return OpenApiConfig.builder()
                .apiKeyRateLimiter(OpenApiConfig.OpenApiRateLimiterConfig.builder()
                        .maxRequestTimes(this.openApiProperties.getRateLimiter().getApiKeyRateLimiter().getMaxRequestTimes())
                        .seconds(this.openApiProperties.getRateLimiter().getApiKeyRateLimiter().getSeconds())
                        .build()
                )
                .ipRateLimiter(OpenApiConfig.OpenApiRateLimiterConfig.builder()
                        .maxRequestTimes(this.openApiProperties.getRateLimiter().getIpRateLimiter().getMaxRequestTimes())
                        .seconds(this.openApiProperties.getRateLimiter().getIpRateLimiter().getSeconds())
                        .build()
                ).build();
    }

    @Bean
    @ConditionalOnMissingBean(name = "customOpenApiAuthManager")
    public OpenApiAuthManager openApiAuthManager() {
        return new DefaultOpenApiAuthManager();
    }

    @Bean
    @ConditionalOnMissingBean(name = "customRateLimiter")
    public RateLimiter redisRateLimiter() {
        return new RedisRateLimiter();
    }

    @Bean(name = "openApiRateLimitInterceptor")
    @ConditionalOnMissingBean(name = "customOpenApiRateLimitInterceptor")
    public OpenApiRateLimitInterceptor openApiRateLimitInterceptor() {
        return new OpenApiRateLimitInterceptor();
    }

    @Bean(name = "openApiAuthInterceptor")
    @ConditionalOnMissingBean(name = "customOpenApiAuthInterceptor")
    public OpenApiAuthInterceptor openApiAuthInterceptor() {
        return new OpenApiAuthInterceptor();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final String[] authIncludeUrlPatterns = Iterables.toArray(Splitter.on(",")
                .omitEmptyStrings()
                .trimResults()
                .split(this.openApiProperties.getInterceptor().getAuth().getIncludeUrlPatterns()), String.class);
        final String[] authExcludePathPatterns = Iterables.toArray(Splitter.on(",")
                .omitEmptyStrings()
                .trimResults()
                .split(this.openApiProperties.getInterceptor().getAuth().getExcludeUrlPatterns()), String.class);

        final String[] rateLimitIncludeUrlPatterns = Iterables.toArray(Splitter.on(",")
                .omitEmptyStrings()
                .trimResults()
                .split(this.openApiProperties.getInterceptor().getRateLimit().getIncludeUrlPatterns()), String.class);
        final String[] rateLimitExcludePathPatterns = Iterables.toArray(Splitter.on(",")
                .omitEmptyStrings()
                .trimResults()
                .split(this.openApiProperties.getInterceptor().getRateLimit().getExcludeUrlPatterns()), String.class);

        if (this.applicationContext.containsBean(AUTH_INTERCEPTOR_BEAN_NAME)) {
            registry.addInterceptor(this.openApiAuthInterceptor())
                    .addPathPatterns(authIncludeUrlPatterns)
                    .excludePathPatterns(authExcludePathPatterns);
        }

        if (this.applicationContext.containsBean(RATE_LIMIT_INTERCEPTOR_BEAN_NAME)) {
            registry.addInterceptor(this.openApiRateLimitInterceptor())
                    .addPathPatterns(rateLimitIncludeUrlPatterns)
                    .excludePathPatterns(rateLimitExcludePathPatterns);
        }
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
