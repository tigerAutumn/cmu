package cc.newex.dax.extra.common.config;

import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * HttpClient连接池配置
 *
 * @author better
 * @date create in 2018-12-13 11:21
 */
@Configuration
public class HttpClientPoolConfig {

    /**
     * Pooling http client connection manager pooling http client connection manager.
     *
     * @return the pooling http client connection manager
     */
    @Bean
    @ConditionalOnClass(value = PoolingHttpClientConnectionManager.class)
    @ConditionalOnMissingBean(value = PoolingHttpClientConnectionManager.class)
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        final PoolingHttpClientConnectionManager pool = new PoolingHttpClientConnectionManager();
        pool.setMaxTotal(200);
        pool.setDefaultMaxPerRoute(20);
        return pool;
    }
}
