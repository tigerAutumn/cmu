package cc.newex.dax.market.common.config.redis;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Configuration
public class RedisConfig {
    @Bean
    public RedisTemplate<?, ?> getRedisTemplate(final RedisConnectionFactory connectionFactory) {
        final RedisTemplate<?, ?> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        return template;
    }


    @Bean(name = "spring.jedis.pool")
    @Autowired
    public JedisPool jedisPool(@Qualifier("spring.redis.pool") final JedisPoolConfig config,
                               @Value("${spring.redis.host}") final String host,
                               @Value("${spring.redis.password}") final String password,
                               @Value("${spring.redis.timeout}") final int timeout,
                               @Value("${spring.redis.port}") final int port) {
        if (StringUtils.isBlank(password)) {
            return new JedisPool(config, host, port, timeout);
        }
        return new JedisPool(config, host, port, timeout, password);
    }

    @Bean(name = "spring.redis.pool")
    public JedisPoolConfig jedisPoolConfig(
            @Value("${spring.redis.pool.max-active}") final int maxActivei,
            @Value("${spring.redis.pool.max-idle}") final int maxIdle,
            @Value("${spring.redis.pool.max-wait}") final int maxWaitMillis) {
        final JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(maxActivei);
        config.setMaxIdle(maxIdle);
        config.setMaxWaitMillis(maxWaitMillis);
        return config;
    }

}
