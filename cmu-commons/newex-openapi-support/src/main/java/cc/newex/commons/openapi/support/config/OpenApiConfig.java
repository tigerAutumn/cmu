package cc.newex.commons.openapi.support.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2018-08-14
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OpenApiConfig {
    /**
     * Open API 按IP限流器配置
     */
    private OpenApiRateLimiterConfig ipRateLimiter;

    /**
     * Open API 按ApiKey限流器配置
     */
    private OpenApiRateLimiterConfig apiKeyRateLimiter;

    /**
     * 限流配置类
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpenApiRateLimiterConfig {
        private int maxRequestTimes;
        private int seconds;
    }
}
