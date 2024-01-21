package cc.newex.spring.boot.autoconfigure.ucenter.redis;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisNode;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import redis.clients.jedis.JedisPoolConfig;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author newex-team
 * @date 2018-06-11
 */
@Configuration
@ConditionalOnProperty(value = "newex.ucenter.redis.host")
@EnableConfigurationProperties(UCenterRedisProperties.class)
public class UCenterRedisAutoConfiguration {
    private final UCenterRedisProperties properties;
    private final RedisSentinelConfiguration sentinelConfiguration;
    private final RedisClusterConfiguration clusterConfiguration;

    public UCenterRedisAutoConfiguration(
            final UCenterRedisProperties properties,
            final ObjectProvider<RedisSentinelConfiguration> sentinelConfiguration,
            final ObjectProvider<RedisClusterConfiguration> clusterConfiguration) {
        this.properties = properties;
        this.sentinelConfiguration = sentinelConfiguration.getIfAvailable();
        this.clusterConfiguration = clusterConfiguration.getIfAvailable();
    }

    @Bean("ucenterRedisConnectionFactory")
    public JedisConnectionFactory redisConnectionFactory() {
        return this.applyProperties(this.createJedisConnectionFactory());
    }

    @Bean("ucenterRedisTemplate")
    public RedisTemplate<Object, Object> redisTemplate(
            @Qualifier("ucenterRedisConnectionFactory") final RedisConnectionFactory redisConnectionFactory) {
        final RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    @Bean("ucenterStringRedisTemplate")
    public StringRedisTemplate ucenterStringRedisTemplate(
            @Qualifier("ucenterRedisConnectionFactory") final RedisConnectionFactory redisConnectionFactory) {
        final StringRedisTemplate template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }

    protected final JedisConnectionFactory applyProperties(
            final JedisConnectionFactory factory) {
        this.configureConnection(factory);
        if (this.properties.isSsl()) {
            factory.setUseSsl(true);
        }
        factory.setDatabase(this.properties.getDatabase());
        if (this.properties.getTimeout() > 0) {
            factory.setTimeout(this.properties.getTimeout());
        }
        return factory;
    }

    private void configureConnection(final JedisConnectionFactory factory) {
        if (StringUtils.hasText(this.properties.getUrl())) {
            this.configureConnectionFromUrl(factory);
        } else {
            factory.setHostName(this.properties.getHost());
            factory.setPort(this.properties.getPort());
            if (this.properties.getPassword() != null) {
                factory.setPassword(this.properties.getPassword());
            }
        }
    }

    private void configureConnectionFromUrl(final JedisConnectionFactory factory) {
        final String url = this.properties.getUrl();
        if (url.startsWith("rediss://")) {
            factory.setUseSsl(true);
        }
        try {
            final URI uri = new URI(url);
            factory.setHostName(uri.getHost());
            factory.setPort(uri.getPort());
            if (uri.getUserInfo() != null) {
                String password = uri.getUserInfo();
                final int index = password.indexOf(":");
                if (index >= 0) {
                    password = password.substring(index + 1);
                }
                factory.setPassword(password);
            }
        } catch (final URISyntaxException ex) {
            throw new IllegalArgumentException("Malformed 'spring.redis.url' " + url,
                    ex);
        }
    }

    protected final RedisSentinelConfiguration getSentinelConfig() {
        if (this.sentinelConfiguration != null) {
            return this.sentinelConfiguration;
        }
        final UCenterRedisProperties.Sentinel sentinelProperties = this.properties.getSentinel();
        if (sentinelProperties != null) {
            final RedisSentinelConfiguration config = new RedisSentinelConfiguration();
            config.master(sentinelProperties.getMaster());
            config.setSentinels(this.createSentinels(sentinelProperties));
            return config;
        }
        return null;
    }

    /**
     * Create a {@link RedisClusterConfiguration} if necessary.
     *
     * @return {@literal null} if no cluster settings are set.
     */
    protected final RedisClusterConfiguration getClusterConfiguration() {
        if (this.clusterConfiguration != null) {
            return this.clusterConfiguration;
        }
        if (this.properties.getCluster() == null) {
            return null;
        }
        final UCenterRedisProperties.Cluster clusterProperties = this.properties.getCluster();
        final RedisClusterConfiguration config = new RedisClusterConfiguration(
                clusterProperties.getNodes());

        if (clusterProperties.getMaxRedirects() != null) {
            config.setMaxRedirects(clusterProperties.getMaxRedirects());
        }
        return config;
    }

    private List<RedisNode> createSentinels(final UCenterRedisProperties.Sentinel sentinel) {
        final List<RedisNode> nodes = new ArrayList<>();
        for (final String node : StringUtils
                .commaDelimitedListToStringArray(sentinel.getNodes())) {
            try {
                final String[] parts = StringUtils.split(node, ":");
                Assert.state(parts.length == 2, "Must be defined as 'host:port'");
                nodes.add(new RedisNode(parts[0], Integer.valueOf(parts[1])));
            } catch (final RuntimeException ex) {
                throw new IllegalStateException(
                        "Invalid redis sentinel " + "property '" + node + "'", ex);
            }
        }
        return nodes;
    }

    private JedisConnectionFactory createJedisConnectionFactory() {
        final JedisPoolConfig poolConfig = (this.properties.getPool() != null
                ? this.jedisPoolConfig() : new JedisPoolConfig());

        if (this.getSentinelConfig() != null) {
            return new JedisConnectionFactory(this.getSentinelConfig(), poolConfig);
        }
        if (this.getClusterConfiguration() != null) {
            return new JedisConnectionFactory(this.getClusterConfiguration(), poolConfig);
        }
        return new JedisConnectionFactory(poolConfig);
    }

    private JedisPoolConfig jedisPoolConfig() {
        final JedisPoolConfig config = new JedisPoolConfig();
        final UCenterRedisProperties.Pool props = this.properties.getPool();
        config.setMaxTotal(props.getMaxActive());
        config.setMaxIdle(props.getMaxIdle());
        config.setMinIdle(props.getMinIdle());
        config.setMaxWaitMillis(props.getMaxWait());
        return config;
    }
}
