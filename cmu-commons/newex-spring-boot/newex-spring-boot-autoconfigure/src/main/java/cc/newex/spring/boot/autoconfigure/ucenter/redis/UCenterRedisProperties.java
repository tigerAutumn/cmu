package cc.newex.spring.boot.autoconfigure.ucenter.redis;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@ConfigurationProperties("newex.ucenter.redis")
public class UCenterRedisProperties {
    /**
     * Database index used by the connection factory.
     */
    private int database = 0;

    /**
     * Redis url, which will overrule host, port and password if set.
     */
    private String url;

    /**
     * Redis server host.
     */
    private String host = "localhost";

    /**
     * Login password of the redis server.
     */
    private String password;

    /**
     * Redis server port.
     */
    private int port = 6379;

    /**
     * Enable SSL.
     */
    private boolean ssl;

    /**
     * Connection timeout in milliseconds.
     */
    private int timeout;

    private Pool pool;

    private Sentinel sentinel;

    private Cluster cluster;

    public int getDatabase() {
        return this.database;
    }

    public void setDatabase(final int database) {
        this.database = database;
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(final String url) {
        this.url = url;
    }

    public String getHost() {
        return this.host;
    }

    public void setHost(final String host) {
        this.host = host;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(final int port) {
        this.port = port;
    }

    public boolean isSsl() {
        return this.ssl;
    }

    public void setSsl(final boolean ssl) {
        this.ssl = ssl;
    }

    public void setTimeout(final int timeout) {
        this.timeout = timeout;
    }

    public int getTimeout() {
        return this.timeout;
    }

    public Sentinel getSentinel() {
        return this.sentinel;
    }

    public void setSentinel(final Sentinel sentinel) {
        this.sentinel = sentinel;
    }

    public Pool getPool() {
        return this.pool;
    }

    public void setPool(final Pool pool) {
        this.pool = pool;
    }

    public Cluster getCluster() {
        return this.cluster;
    }

    public void setCluster(final Cluster cluster) {
        this.cluster = cluster;
    }

    /**
     * Pool properties.
     */
    public static class Pool {

        /**
         * Max number of "idle" connections in the pool. Use a negative value to indicate
         * an unlimited number of idle connections.
         */
        private int maxIdle = 8;

        /**
         * Target for the minimum number of idle connections to maintain in the pool. This
         * setting only has an effect if it is positive.
         */
        private int minIdle = 0;

        /**
         * Max number of connections that can be allocated by the pool at a given time.
         * Use a negative value for no limit.
         */
        private int maxActive = 8;

        /**
         * Maximum amount of time (in milliseconds) a connection allocation should block
         * before throwing an exception when the pool is exhausted. Use a negative value
         * to block indefinitely.
         */
        private int maxWait = -1;

        public int getMaxIdle() {
            return this.maxIdle;
        }

        public void setMaxIdle(final int maxIdle) {
            this.maxIdle = maxIdle;
        }

        public int getMinIdle() {
            return this.minIdle;
        }

        public void setMinIdle(final int minIdle) {
            this.minIdle = minIdle;
        }

        public int getMaxActive() {
            return this.maxActive;
        }

        public void setMaxActive(final int maxActive) {
            this.maxActive = maxActive;
        }

        public int getMaxWait() {
            return this.maxWait;
        }

        public void setMaxWait(final int maxWait) {
            this.maxWait = maxWait;
        }

    }

    /**
     * Cluster properties.
     */
    public static class Cluster {

        /**
         * Comma-separated list of "host:port" pairs to bootstrap from. This represents an
         * "initial" list of cluster nodes and is required to have at least one entry.
         */
        private List<String> nodes;

        /**
         * Maximum number of redirects to follow when executing commands across the
         * cluster.
         */
        private Integer maxRedirects;

        public List<String> getNodes() {
            return this.nodes;
        }

        public void setNodes(final List<String> nodes) {
            this.nodes = nodes;
        }

        public Integer getMaxRedirects() {
            return this.maxRedirects;
        }

        public void setMaxRedirects(final Integer maxRedirects) {
            this.maxRedirects = maxRedirects;
        }

    }

    /**
     * Redis sentinel properties.
     */
    public static class Sentinel {

        /**
         * Name of Redis server.
         */
        private String master;

        /**
         * Comma-separated list of host:port pairs.
         */
        private String nodes;

        public String getMaster() {
            return this.master;
        }

        public void setMaster(final String master) {
            this.master = master;
        }

        public String getNodes() {
            return this.nodes;
        }

        public void setNodes(final String nodes) {
            this.nodes = nodes;
        }

    }
}
