package cc.newex.commons.ratelimiter;

/**
 * @author newex-team
 * @date 2018-04-28
 */
public interface RateLimiter {
    /**
     * @param parameter
     * @return
     */
    boolean canAcquire(RateLimitParameter parameter);

    /**
     * 清除记数器
     *
     * @param key
     */
    void clear(String key);
}