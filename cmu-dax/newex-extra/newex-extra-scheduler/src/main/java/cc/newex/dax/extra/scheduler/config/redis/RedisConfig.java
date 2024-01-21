package cc.newex.dax.extra.scheduler.config.redis;

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
    public RedisTemplate<?, ?> getRedisTemlate(final RedisConnectionFactory connectionFactory) {
        final RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }

}
