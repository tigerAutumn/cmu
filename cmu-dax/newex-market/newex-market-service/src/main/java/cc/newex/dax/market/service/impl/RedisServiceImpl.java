package cc.newex.dax.market.service.impl;

import cc.newex.dax.market.service.RedisService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service("redisService")
public class RedisServiceImpl implements RedisService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 发布消息
     *
     * @param channel
     * @param data
     */
    @Override
    public void publish(final String channel, final String data) {
        this.stringRedisTemplate.convertAndSend(channel, data);
    }

    @Override
    public void setInfo(final String key, final String data) {
        this.stringRedisTemplate.opsForValue().set(key, data);
    }

    @Override
    public String getInfo(final String key) {
        return this.stringRedisTemplate.opsForValue().get(key);
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


    @Override
    public void putCache(final int marketFrom, final int type, final List<String[]> list) {
        final String key = "marketdataarraycache_" + marketFrom + "_" + type;
        final String json = RedisServiceImpl.convertedString(list);
        this.setInfo(key, json);
    }

    private static String convertedString(final List<String[]> list) {
        final StringBuffer str = new StringBuffer("");
        if (!CollectionUtils.isEmpty(list)) {
            for (final String[] strings : list) {
                for (final String string : strings) {
                    str.append(string + (char) 129);
                }
                str.deleteCharAt(str.length() - 1);
                str.append(";");
            }
            str.deleteCharAt(str.length() - 1);
        }
        return str.toString();
    }

    private static Map<String, LocalCache> cacheMap;

    /**
     * 随机get
     */
    @Override
    public String getRandom(final String key) {
        if (RedisServiceImpl.cacheMap == null) {
            RedisServiceImpl.cacheMap = new ConcurrentHashMap<>();
        }
        final String value;
        try {
            LocalCache localCache = RedisServiceImpl.cacheMap.get(key);
            final long cacheTime = localCache != null ? (System.currentTimeMillis() - localCache.getCacheTime()) : 0;
            if (localCache == null || cacheTime > 200) {
                value = this.getInfo(key);
                if (localCache == null) {
                    localCache = new LocalCache();
                }
                localCache.setCacheTime(System.currentTimeMillis());
                localCache.setCacheValue(value);
                RedisServiceImpl.cacheMap.put(key, localCache);
            } else {
                value = localCache.getCacheValue();
            }
            return value;
        } catch (final Exception ex) {
            RedisServiceImpl.log.error("getRandom key ERROR: " + key + ex.getMessage());
            return null;
        }
    }

    @Data
    class LocalCache {
        private String cacheValue;
        private long cacheTime;
    }
}
