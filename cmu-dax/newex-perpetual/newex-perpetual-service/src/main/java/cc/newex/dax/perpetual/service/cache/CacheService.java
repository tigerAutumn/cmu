package cc.newex.dax.perpetual.service.cache;

import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 * @date 2018/7/19
 */
public interface CacheService {
    /**
     * redis pub
     *
     * @param channel
     * @param message
     */
    void convertAndSend(String channel, Object message);

    /**
     * redis set
     *
     * @param key
     * @param value
     */
    void setCacheValue(String key, String value);

    /**
     * get redis value
     *
     * @param key
     * @return
     */
    String getCacheValue(String key);

    /**
     * get redis value
     *
     * @param key
     * @return
     */
    <T> T getCacheObject(String key, Class<T> clazz);

    /**
     * get redis value
     *
     * @param key
     * @return
     */
    <T> List<T> getCacheList(String key, Class<T> clazz);

    /**
     * getSet redis value
     *
     * @param key
     * @return
     */
    String getSetCacheValue(String key, String value);

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

    /**
     * redis set
     *
     * @param key
     * @param field
     * @param value
     */
    void hsetCacheValue(String key, String field, String value);

    /**
     * map 获取
     *
     * @param key
     * @param field
     * @param clazz
     * @param <T>
     * @return
     */
    <T> T hgetCacheObject(String key, String field, Class<T> clazz);

    void hdelCacheObject(String key, String ... field);

    /**
     * map 获取
     *
     * @param key
     * @param field
     * @param clazz
     * @param <T>
     * @return
     */
    <T> List<T> hmgetCacheValue(String key, Class<T> clazz, String... field);

    <T> Map<String, T> hmgetAllCacheValue(String key, Class<T> clazz);

    /**
     * redis set
     *
     * @param key
     * @param fvMap
     */
    void hmsetCacheValue(String key, Map<String, String> fvMap);

    /**
     * SortedSet
     *
     * @param key
     * @param tuples
     */
    long zsetAddCacheValue(final String key, Set<ZSetOperations.TypedTuple<String>> tuples);
}
