package cc.newex.commons.ratelimiter.config;

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
public class RateLimiterConfig {
    /**
     * 通用IP限流器配置
     */
    private RateLimiterConfigItem ipRequestRateLimiter;

    /**
     * 限流配置类
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RateLimiterConfigItem {
        private int maxRequestTimes;
        private int seconds;
    }
}
