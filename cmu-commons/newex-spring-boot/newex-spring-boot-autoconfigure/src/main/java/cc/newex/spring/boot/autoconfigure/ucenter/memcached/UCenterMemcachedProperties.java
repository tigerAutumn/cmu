package cc.newex.spring.boot.autoconfigure.ucenter.memcached;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@ConfigurationProperties("newex.ucenter.memcached")
public class UCenterMemcachedProperties {
    private String servers = "localhost:11211";
    private int poolSize = 5;
    private long connectionTimeout = 1000;
    private long opTimeout = 2000;
    private boolean primitiveAsString = false;
    private String serverName = "master";
    private String password;

    /**
     * 服务器域名
     *
     * @return 服务器域名
     */
    public String getServers() {
        return this.servers;
    }

    /**
     * 服务器域名
     *
     * @param servers 服务器域名
     */
    public void setServers(final String servers) {
        this.servers = servers;
    }

    /**
     * 线程池大小
     *
     * @return 线程池大小
     */
    public int getPoolSize() {
        return this.poolSize;
    }

    /**
     * 线程池大小
     *
     * @param poolSize 线程池大小
     */
    public void setPoolSize(final int poolSize) {
        this.poolSize = poolSize;
    }

    /**
     * 连接超时时间
     *
     * @return 连接超时时间
     */
    public long getConnectionTimeout() {
        return this.connectionTimeout;
    }

    /**
     * 连接超时时间
     *
     * @param connectionTimeout 连接超时时间
     */
    public void setConnectionTimeout(final long connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }

    /**
     * 操作超时时间
     *
     * @return 操作超时时间
     */
    public long getOpTimeout() {
        return this.opTimeout;
    }

    /**
     * 操作超时时间
     *
     * @param opTimeout 操作超时时间
     */
    public void setOpTimeout(final long opTimeout) {
        this.opTimeout = opTimeout;
    }

    /**
     * 是否将基本类型转换成String
     *
     * @return 是否将基本类型转换成String
     */
    public boolean isPrimitiveAsString() {
        return this.primitiveAsString;
    }

    /**
     * 是否将基本类型转换成String
     *
     * @param primitiveAsString 是否将基本类型转换成String
     */
    public void setPrimitiveAsString(final boolean primitiveAsString) {
        this.primitiveAsString = primitiveAsString;
    }

    /**
     * 服务器名称
     *
     * @return 服务器名称
     */
    public String getServerName() {
        return this.serverName;
    }

    /**
     * 服务器名称
     *
     * @param serverName 服务器名称
     */
    public void setServerName(final String serverName) {
        this.serverName = serverName;
    }

    /**
     * 认证密码
     *
     * @return 认证密码
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * 认证密码
     *
     * @param password 认证密码
     */
    public void setPassword(final String password) {
        this.password = password;
    }
}
