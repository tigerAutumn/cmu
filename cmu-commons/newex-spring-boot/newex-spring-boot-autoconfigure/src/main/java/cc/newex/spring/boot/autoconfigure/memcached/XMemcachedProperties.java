package cc.newex.spring.boot.autoconfigure.memcached;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Data
@NoArgsConstructor
@ConfigurationProperties("newex.xmemcached")
public class XMemcachedProperties {
    /**
     * 服务器域名
     */
    private String servers = "localhost:11211";

    /**
     * 线程池大小
     */
    private int poolSize = 5;

    /**
     * 连接超时时间
     */
    private long connectionTimeout = 1000;

    /**
     * 操作超时时间
     */
    private long opTimeout = 2000;

    /**
     * 是否将基本类型转换成String
     */
    private boolean primitiveAsString = false;

    /**
     * 服务器名称
     */
    private String serverName = "master";

    /**
     * 认证密码
     */
    private String password;
}
