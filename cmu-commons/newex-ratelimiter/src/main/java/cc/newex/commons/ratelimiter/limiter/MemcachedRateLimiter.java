package cc.newex.commons.ratelimiter.limiter;

import cc.newex.commons.ratelimiter.AbstractRateLimiter;
import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.ratelimiter.RateLimiter;

/**
 * @author newex-team
 * @date 2018-04-28
 */
public class MemcachedRateLimiter
        extends AbstractRateLimiter implements RateLimiter {

    @Override
    public boolean canAcquire(final RateLimitParameter parameter) {
        return false;
    }

    @Override
    public void clear(String key) {

    }
}
