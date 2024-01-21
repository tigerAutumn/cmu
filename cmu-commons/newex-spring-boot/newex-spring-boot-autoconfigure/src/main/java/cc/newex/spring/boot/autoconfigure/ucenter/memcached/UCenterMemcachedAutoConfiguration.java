package cc.newex.spring.boot.autoconfigure.ucenter.memcached;

import cc.newex.commons.memcached.repository.MemcachedRepository;
import cc.newex.commons.memcached.repository.XMemcachedRepository;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.auth.AuthInfo;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.impl.KetamaMemcachedSessionLocator;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Configuration
@ConditionalOnProperty(value = "newex.ucenter.memcached.servers")
@EnableConfigurationProperties(UCenterMemcachedProperties.class)
public class UCenterMemcachedAutoConfiguration {
    private final UCenterMemcachedProperties properties;

    public UCenterMemcachedAutoConfiguration(final UCenterMemcachedProperties properties) {
        this.properties = properties;
    }

    @Bean(name = "ucenterMemcachedClient")
    public MemcachedClient ucenterMemcachedClient() throws IOException {
        final List<InetSocketAddress> addresses = AddrUtil.getAddresses(this.properties.getServers());
        final XMemcachedClientBuilder builder = new XMemcachedClientBuilder(addresses);
        if (StringUtils.isNotEmpty(this.properties.getPassword())) {
            builder.addAuthInfo(AddrUtil.getOneAddress(this.properties.getServers()),
                    AuthInfo.plain(this.properties.getServerName(), this.properties.getPassword()));
        }
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        builder.setConnectionPoolSize(this.properties.getPoolSize());
        builder.setCommandFactory(new BinaryCommandFactory());

        final MemcachedClient client = builder.build();
        client.setConnectTimeout(this.properties.getConnectionTimeout());
        client.setOpTimeout(this.properties.getOpTimeout());
        client.setPrimitiveAsString(this.properties.isPrimitiveAsString());
        return client;
    }

    @Bean(name = "ucenterMemcachedRepository")
    public MemcachedRepository createMemcachedRepository(@Qualifier("ucenterMemcachedClient") final MemcachedClient memcachedClient) {
        return new XMemcachedRepository(memcachedClient);
    }
}
