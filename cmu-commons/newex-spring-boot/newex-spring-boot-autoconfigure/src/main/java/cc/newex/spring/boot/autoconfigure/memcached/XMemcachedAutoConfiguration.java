package cc.newex.spring.boot.autoconfigure.memcached;

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
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Configuration
@ConditionalOnClass(MemcachedClient.class)
@ConditionalOnProperty(prefix = "newex.xmemcached", name = "servers")
@EnableConfigurationProperties(XMemcachedProperties.class)
public class XMemcachedAutoConfiguration {
    private final XMemcachedProperties properties;

    public XMemcachedAutoConfiguration(final XMemcachedProperties properties) {
        this.properties = properties;
    }

    @Primary
    @Bean(name = "xmemcachedClient")
    public MemcachedClient memcachedClient() throws IOException {
        final List<InetSocketAddress> addresses = AddrUtil.getAddresses(this.properties.getServers());
        final XMemcachedClientBuilder builder = new XMemcachedClientBuilder(addresses);
        if (StringUtils.isNotEmpty(this.properties.getPassword())) {
            builder.addAuthInfo(AddrUtil.getOneAddress(this.properties.getServers()),
                    AuthInfo.plain(this.properties.getServerName(), this.properties.getPassword()));
        }
        builder.setCommandFactory(new BinaryCommandFactory());
        builder.setSessionLocator(new KetamaMemcachedSessionLocator());
        builder.setConnectionPoolSize(this.properties.getPoolSize());
        final MemcachedClient client = builder.build();
        client.setConnectTimeout(this.properties.getConnectionTimeout());
        client.setOpTimeout(this.properties.getOpTimeout());
        client.setPrimitiveAsString(this.properties.isPrimitiveAsString());
        return client;
    }

    @Primary
    @Bean(name = "xmemcachedRepository")
    public MemcachedRepository createMemcachedRepository(@Qualifier("xmemcachedClient") final MemcachedClient memcachedClient) {
        return new XMemcachedRepository(memcachedClient);
    }
}
