package cc.newex.dax.market.service;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface RedisService {


    void publish(String channel, String data);

    void setInfo(String key, String data);

    String getInfo(String key);

    /**
     * set cache value to expire time
     *
     * @param key
     * @param value
     * @param timeout
     * @param unit
     */
    void setCacheValueExpireTime(String key, String value, long timeout, TimeUnit unit);

    /**
     * redis string keys
     *
     * @param keys
     * @return
     */
    Set<String> keys(String keys);

    /**
     * redis delete by key
     *
     * @param key
     */
    void delete(String key);


    void putCache(final int marketFrom, final int type, final List<String[]> list);

    /**
     * 随机get
     */
    String getRandom(final String key);
}
