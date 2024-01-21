package cc.newex.dax.users.rest.common.config.redis;

import cc.newex.commons.ratelimiter.RateLimiter;
import cc.newex.commons.ratelimiter.limiter.RedisRateLimiter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(final RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean
    public RateLimiter redisRateLimiter() {
        return new RedisRateLimiter();
    }
}
