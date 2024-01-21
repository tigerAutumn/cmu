package cc.newex.commons.memcached.repository;

import lombok.extern.slf4j.Slf4j;
import net.rubyeye.xmemcached.MemcachedClient;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.Objects;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@Slf4j
public class XMemcachedRepository implements MemcachedRepository {
    private final MemcachedClient client;

    public XMemcachedRepository(final MemcachedClient client) {
        this.client = client;
    }

    @Override
    public Object get(final String key) {
        try {
            return this.client.get(key);
        } catch (final Exception ex) {
            log.error("get key {} ERROR: {}", key, ex.getMessage());
            return null;
        }
    }

    @Override
    public void put(final String key, final Object obj) {
        try {
            this.put(key, obj, 0);
        } catch (final Exception ex) {
            log.error("put key {} ERROR: {}", key, ex.getMessage());
        }
    }

    @Override
    public void put(final String key, final Object obj, final long exp) {
        try {
            if (obj != null) {
                final int e = (int) (exp / 1000);
                this.client.set(key, e, obj);
            }
        } catch (final Exception ex) {
            log.error("put key {} ERROR: {}", key, ex.getMessage());
        }
    }

    @Override
    public void putAll(final String key, final Object obj) {
        try {
            if (obj != null) {
                this.client.set(key, 0, obj);
            }
        } catch (final Exception ex) {
            log.error("putAll key {} ERROR: {}", key, ex.getMessage());
        }
    }

    @Override
    public void remove(final String key) {
        try {
            this.client.delete(key);
        } catch (final Exception ex) {
            log.error("remove key {} ERROR: {}", key, ex.getMessage());
        }
    }

    @Override
    public void clear() {
        try {
            this.client.flushAll();
        } catch (final Exception ex) {
            log.error("clear ERROR: {}", ex.getMessage());
        }
    }

    @Override
    public long incr(final String key, final long by, final long def) {
        try {
            return this.client.incr(key, by, def, 2000, 20);
        } catch (final Exception ex) {
            log.error("inc key: {} by: {} def: {} ERROR: {}", key, by, def, ex.getMessage());
            return 0;
        }

    }

    @Override
    public long incr(final String key, final long by, final long def, final int exp) {
        try {
            return this.client.incr(key, by, def, 2000, exp);
        } catch (final Exception ex) {
            log.error("inc key: {} by: {} def: {} exp:{} ERROR: {}", key, by, def, exp, ex.getMessage());
            return 0;
        }

    }

    @Override
    public long incr(final String key) {
        try {
            return this.client.incr(key, 1, 0);
        } catch (final Exception ex) {
            log.error("incr key: {} ERROR: {}", key, ex.getMessage());
            return 0;
        }
    }

    @Override
    public long getWithDefaultValue(final String key) {
        try {
            final Object obj = this.client.get(key);
            return NumberUtils.toLong(Objects.toString(obj, null), -1);
        } catch (final Exception ex) {
            log.error("key: {} ERROR: {}", key, ex.getMessage());
            return 0;
        }
    }
}
