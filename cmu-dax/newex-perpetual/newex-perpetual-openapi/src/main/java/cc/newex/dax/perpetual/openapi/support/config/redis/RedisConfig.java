package cc.newex.dax.perpetual.openapi.support.config.redis;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author newex-team
 * @date 2018/11/01
 */
@Configuration
public class RedisConfig {
    @Bean
    public static RedisTemplate<?, ?> getRedisTemplate(final RedisConnectionFactory connectionFactory) {
        final RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }
}
