package cc.newex.dax.extra.service.cache.impl;

import cc.newex.dax.extra.service.cache.AppCacheService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 应用所有操作redis缓存调用都集中封装到该类
 *
 * @author newex-team
 * @date 2018-06-18
 */
@Slf4j
@Service
public class RedisAppCacheServiceImpl implements AppCacheService {
    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public <T> List<T> getList(final String key, final Class<T> clazz) {
        final String value = this.redisTemplate.opsForValue().get(key);
        if (StringUtils.isNotEmpty(value)) {
            final List<T> list = JSONObject.parseArray(value, clazz);
            if (CollectionUtils.isNotEmpty(list)) {
                return list;
            }
        }
        return null;
    }

    @Override
    public <T> void setList(final String key, final List<T> list) {
        this.redisTemplate.opsForValue().set(key, JSON.toJSONString(list));
    }

    @Override
    public void deleteKey(final String key) {
        this.redisTemplate.delete(key);
    }
}
