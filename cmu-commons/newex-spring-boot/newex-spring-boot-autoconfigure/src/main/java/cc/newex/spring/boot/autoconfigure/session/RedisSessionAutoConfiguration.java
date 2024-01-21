package cc.newex.spring.boot.autoconfigure.session;

import cc.newex.commons.ucenter.data.SessionRepository;
import cc.newex.commons.ucenter.data.redis.RedisSessionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author newex-team
 * @date 2018-06-11
 */
@Configuration
@ConditionalOnProperty(value = "newex.ucenter.redis.host")
public class RedisSessionAutoConfiguration {

    public RedisSessionAutoConfiguration() {
    }

    @Bean(name = "redisSessionRepository")
    public SessionRepository createRedisSessionRepository() {
        return new RedisSessionRepository();
    }
}
