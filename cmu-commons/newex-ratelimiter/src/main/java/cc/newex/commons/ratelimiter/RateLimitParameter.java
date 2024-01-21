package cc.newex.commons.ratelimiter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2018-05-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RateLimitParameter {
    /**
     * 唯一key
     */
    private String rateLimitKey;

    /**
     * 最大请求次数
     */
    private long maxRequestTimes;

    /**
     * 计量秒(用于限制每多少seconds时间内的maxRequestTimes（如：10/2 表示2秒内最多请求10次）
     */
    private long seconds;

    /**
     * 当前已经请求的次数
     */
    private long used;
}
