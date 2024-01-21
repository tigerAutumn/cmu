package cc.newex.spring.boot.autoconfigure.openapi;

import cc.newex.commons.support.properties.InterceptorProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

/**
 * @author newex-team
 * @date 2017/11/20
 */
@ConfigurationProperties(prefix = "newex.openapi")
public class OpenApiProperties {
    private OpenApiInterceptorProperties interceptor = new OpenApiInterceptorProperties();
    private OpenApiRateLimiterProperties rateLimiter = new OpenApiRateLimiterProperties();

    public OpenApiInterceptorProperties getInterceptor() {
        return this.interceptor;
    }

    public void setInterceptor(final OpenApiInterceptorProperties interceptor) {
        this.interceptor = interceptor;
    }

    public OpenApiRateLimiterProperties getRateLimiter() {
        return this.rateLimiter;
    }

    public void setRateLimiter(final OpenApiRateLimiterProperties rateLimiter) {
        this.rateLimiter = rateLimiter;
    }

    /**
     * open api 拦截器配置
     */
    public static class OpenApiInterceptorProperties {
        @NestedConfigurationProperty
        private InterceptorProperty auth = new InterceptorProperty("/api/v1/**", "");
        @NestedConfigurationProperty
        private InterceptorProperty rateLimit = new InterceptorProperty("/api/v1/**", "");

        public InterceptorProperty getAuth() {
            return this.auth;
        }

        public void setAuth(final InterceptorProperty auth) {
            this.auth = auth;
        }

        public InterceptorProperty getRateLimit() {
            return this.rateLimit;
        }

        public void setRateLimit(final InterceptorProperty rateLimit) {
            this.rateLimit = rateLimit;
        }
    }

    public static class OpenApiRateLimiterProperties {
        @NestedConfigurationProperty
        private OpenApiRateLimiterConfigProperties ipRateLimiter = new OpenApiRateLimiterConfigProperties();
        @NestedConfigurationProperty
        private OpenApiRateLimiterConfigProperties apiKeyRateLimiter = new OpenApiRateLimiterConfigProperties();

        public OpenApiRateLimiterConfigProperties getIpRateLimiter() {
            return this.ipRateLimiter;
        }

        public void setIpRateLimiter(final OpenApiRateLimiterConfigProperties ipRateLimiter) {
            this.ipRateLimiter = ipRateLimiter;
        }

        public OpenApiRateLimiterConfigProperties getApiKeyRateLimiter() {
            return this.apiKeyRateLimiter;
        }

        public void setApiKeyRateLimiter(final OpenApiRateLimiterConfigProperties apiKeyRateLimiter) {
            this.apiKeyRateLimiter = apiKeyRateLimiter;
        }
    }

    public static class OpenApiRateLimiterConfigProperties {
        private int maxRequestTimes = 10;
        private int seconds = 1;

        public int getMaxRequestTimes() {
            return this.maxRequestTimes;
        }

        public void setMaxRequestTimes(final int maxRequestTimes) {
            this.maxRequestTimes = maxRequestTimes;
        }

        public int getSeconds() {
            return this.seconds;
        }

        public void setSeconds(final int seconds) {
            this.seconds = seconds;
        }
    }
}
