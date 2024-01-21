package cc.newex.commons.ratelimiter.limiter;

import cc.newex.commons.ratelimiter.AbstractRateLimiter;
import cc.newex.commons.ratelimiter.RateLimitParameter;
import cc.newex.commons.ratelimiter.RateLimiter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 * @date 2018-04-28
 */
@Slf4j
public class RedisRateLimiter
        extends AbstractRateLimiter implements RateLimiter {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private volatile Object mutexDoNotUseDirectly;

    @Override
    public boolean canAcquire(final RateLimitParameter parameter) {
        final String key = parameter.getRateLimitKey();
        final long seconds = parameter.getSeconds();
        final long max = parameter.getMaxRequestTimes();

        final long current = NumberUtils.toLong(this.stringRedisTemplate.opsForValue().get(key), 0);
        if (current >= max) {
            log.error("canAcquire: false, key: {}, seconds: {}, max: {}, current: {}", key, seconds, max, current);

            this.setExpireTime(key, seconds);

            return false;
        }

        Long incr = this.stringRedisTemplate.opsForValue().increment(key, 1);

        this.setExpireTime(key, seconds);

        incr = ObjectUtils.defaultIfNull(incr, 0L);

        parameter.setUsed(incr);

        if (incr > max) {
            log.error("canAcquire: false, key: {}, seconds: {}, max: {}, current: {}", key, seconds, max, incr);

            this.setExpireTime(key, seconds);

            return false;
        }

        return true;
    }

    private void setExpireTime(final String key, final long seconds) {
        // 返回key的剩余生存时间
        final Long expire = this.stringRedisTemplate.getExpire(key, TimeUnit.SECONDS);

        if (expire == null || expire < 0 || expire > seconds) {
            log.info("set expire time, key: {}, seconds: {}", key, seconds);

            this.stringRedisTemplate.expire(key, seconds, TimeUnit.SECONDS);
        }
    }

    @Override
    public void clear(final String key) {
        this.stringRedisTemplate.delete(key);
    }
}
