package cc.newex.dax.perpetual.service.cache.impl;

import cc.newex.dax.perpetual.service.cache.CacheService;
import com.alibaba.fastjson.JSON;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 * @date 2018/7/18
 */
@Service
public class RedisCacheServiceImpl implements CacheService {


    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * redis pub
     *
     * @param channel
     * @param message
     */
    @Override
    public void convertAndSend(final String channel, final Object message) {
        this.stringRedisTemplate.convertAndSend(channel, message);
    }

    /**
     * redis set
     *
     * @param key
     * @param value
     */
    @Override
    public void setCacheValue(final String key, final String value) {
        this.stringRedisTemplate.opsForValue().set(key, value);
    }

    /**
     * get redis value
     *
     * @param key
     * @return
     */
    @Override
    public String getCacheValue(final String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public <T> T getCacheObject(final String key, final Class<T> clazz) {
        final String str = this.stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(str)) {
            return JSON.parseObject(str, clazz);
        }
        return null;
    }

    @Override
    public <T> List<T> getCacheList(final String key, final Class<T> clazz) {
        final String str = this.stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isNotBlank(str)) {
            return JSON.parseArray(str, clazz);
        }
        return null;
    }

    @Override
    public String getSetCacheValue(final String key, final String value) {
        return this.stringRedisTemplate.opsForValue().getAndSet(key, value);
    }

    @Override
    public void setCacheValueExpireTime(final String key, final String value, final long timeout, final TimeUnit unit) {
        this.stringRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    /**
     * redis string keys
     *
     * @param keys
     * @return
     */
    @Override
    public Set<String> keys(final String keys) {
        return this.stringRedisTemplate.keys(keys);
    }

    /**
     * redis delete by key
     *
     * @param key
     */
    @Override
    public void delete(final String key) {
        this.stringRedisTemplate.delete(key);
    }


    /**
     * redis set
     *
     * @param key
     * @param field
     * @param value
     */
    @Override
    public void hsetCacheValue(final String key, final String field, final String value) {
        this.stringRedisTemplate.opsForHash().put(key, field, value);
    }

    @Override
    public <T> T hgetCacheObject(final String key, final String field, final Class<T> clazz) {
        final String value = (String) this.stringRedisTemplate.opsForHash().get(key, field);
        return JSON.parseObject(value, clazz);
    }

    @Override
    public void hdelCacheObject(String key, String... field) {
        this.stringRedisTemplate.opsForHash().delete(key, field);
    }

    @Override
    public <T> List<T> hmgetCacheValue(final String key, final Class<T> clazz, final String... field) {
        final List<Object> objectList = this.stringRedisTemplate.opsForHash().multiGet(key, Arrays.asList(field));
        final List<T> result = new ArrayList<>();
        for (final Object o : objectList) {
            result.add(JSON.parseObject((String) o, clazz));
        }
        return result;
    }

    @Override
    public <T> Map<String, T> hmgetAllCacheValue(final String key, final Class<T> clazz) {
        final Map<Object, Object> entries = this.stringRedisTemplate.opsForHash().entries(key);
        if (MapUtils.isEmpty(entries)) {
            return new HashMap<>();
        }

        final Set<Map.Entry<Object, Object>> entrySet = entries.entrySet();
        final Map<String, T> result = new HashMap<>();
        for (final Map.Entry<Object, Object> entry : entrySet) {
            result.put((String) entry.getKey(), JSON.parseObject((String) entry.getValue(), clazz));
        }
        return result;
    }

    /**
     * redis set
     *
     * @param key
     * @param fvMap
     */
    @Override
    public void hmsetCacheValue(final String key, final Map<String, String> fvMap) {
        this.stringRedisTemplate.opsForHash().putAll(key, fvMap);
    }

    /**
     * SortedSet
     *
     * @param key
     * @param tuples
     */
    @Override
    public long zsetAddCacheValue(final String key, final Set<ZSetOperations.TypedTuple<String>> tuples) {
        return this.stringRedisTemplate.opsForZSet().add(key, tuples);
    }
}
