package cc.newex.spring.boot.autoconfigure.session;

import cc.newex.commons.ucenter.data.SessionRepository;
import cc.newex.commons.ucenter.data.memcached.MemcachedSessionRepository;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Configuration
@ConditionalOnProperty(value = "newex.ucenter.memcached.servers")
public class MemcachedSessionAutoConfiguration {

    public MemcachedSessionAutoConfiguration() {
    }

    @Bean(name = "memcachedSessionRepository")
    public SessionRepository createMemcachedSessionRepository() {
        return new MemcachedSessionRepository();
    }
}
