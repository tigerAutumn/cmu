package cc.newex.spring.boot.autoconfigure.ratelimiter;

import cc.newex.commons.ratelimiter.RateLimiter;
import cc.newex.commons.ratelimiter.aop.IpRequestRateLimitInterceptor;
import cc.newex.commons.ratelimiter.config.RateLimiterConfig;
import com.google.common.base.Splitter;
import com.google.common.collect.Iterables;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
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
@Order(90)
@Configuration
@EnableConfigurationProperties({RateLimiterProperties.class})
@ConditionalOnClass(RateLimiter.class)
public class RateLimiterAutoConfiguration
        extends WebMvcConfigurerAdapter implements ApplicationContextAware {

    private static final String IP_REQUEST_RATE_LIMIT_INTERCEPTOR_BEAN_NAME = "ipRequestRateLimitInterceptor";

    private ApplicationContext applicationContext;

    private final RateLimiterProperties properties;

    public RateLimiterAutoConfiguration(final RateLimiterProperties properties) {
        this.properties = properties;
    }

    @Bean
    public RateLimiterConfig rateLimiterConfig() {
        return RateLimiterConfig.builder()
                .ipRequestRateLimiter(RateLimiterConfig.RateLimiterConfigItem.builder()
                        .maxRequestTimes(this.properties.getIpRateLimiter().getMaxRequestTimes())
                        .seconds(this.properties.getIpRateLimiter().getSeconds())
                        .build()
                ).build();
    }

    @Bean(name = "ipRequestRateLimitInterceptor")
    @ConditionalOnMissingBean(name = "ipRequestRateLimitInterceptor")
    @ConditionalOnProperty("newex.ratelimiter.ip-rate-limiter.enabled")
    public IpRequestRateLimitInterceptor ipRequestRateLimitInterceptor() {
        return new IpRequestRateLimitInterceptor();
    }

    @Override
    public void addInterceptors(final InterceptorRegistry registry) {
        final String[] ipIncludeUrlPatterns = Iterables.toArray(Splitter.on(",")
                .omitEmptyStrings()
                .trimResults()
                .split(this.properties.getIpRateLimiter().getInterceptor().getIncludeUrlPatterns()), String.class);
        final String[] ipExcludePathPatterns = Iterables.toArray(Splitter.on(",")
                .omitEmptyStrings()
                .trimResults()
                .split(this.properties.getIpRateLimiter().getInterceptor().getExcludeUrlPatterns()), String.class);

        if (this.applicationContext.containsBean(IP_REQUEST_RATE_LIMIT_INTERCEPTOR_BEAN_NAME)) {
            registry.addInterceptor(this.ipRequestRateLimitInterceptor())
                    .addPathPatterns(ipIncludeUrlPatterns)
                    .excludePathPatterns(ipExcludePathPatterns);
        }
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
