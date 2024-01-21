package cc.newex.commons.openapi.support.auth;

import cc.newex.commons.dictionary.consts.ApiKeyConsts;
import cc.newex.commons.openapi.specs.auth.AbstractOpenApiKeyService;
import cc.newex.commons.openapi.specs.auth.OpenApiKeyService;
import cc.newex.commons.openapi.specs.model.OpenApiKeyInfo;
import cc.newex.commons.openapi.specs.model.OpenApiUserInfo;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

import static cc.newex.commons.dictionary.consts.ApiKeyConsts.OPEN_API_KEY_USER_ID_PREFIX;

/**
 * @author newex-team
 * @date 2018-05-01
 */
@Slf4j
public abstract class AbstractRedisOpenApiKeyService
        extends AbstractOpenApiKeyService implements OpenApiKeyService {

    @Autowired
    protected StringRedisTemplate stringRedisTemplate;

    @Override
    public OpenApiKeyInfo getApiKeyInfo(final String apiKey) {
        if (StringUtils.isBlank(apiKey)) {
            return null;
        }

        final OpenApiKeyInfo apiKeyInfo = this.getApiKeyInfoFromCache(apiKey);
        if (apiKeyInfo == null) {
            return this.getApiKeyInfoFromClient(apiKey);
        }

        return apiKeyInfo;
    }

    @Override
    public OpenApiUserInfo getApiUserInfo(final String apiKey, final long userId) {
        if (StringUtils.isBlank(apiKey)) {
            return null;
        }

        final OpenApiUserInfo userInfo = this.getApiUserInfoFromCache(apiKey);
        if (userInfo == null) {
            return this.getApiUserInfoFromClient(apiKey, userId);
        }

        return userInfo;
    }

    protected OpenApiKeyInfo getApiKeyInfoFromCache(final String apiKey) {
        final String cacheKey = StringUtils.join(ApiKeyConsts.OPEN_API_KEY_PREFIX, apiKey);

        // 返回key的剩余生存时间
        final Long expire = this.stringRedisTemplate.getExpire(cacheKey, TimeUnit.MINUTES);
        if (expire == null || expire <= 0 || expire > 10) {
            log.error("api key: {} not exist in redis, expire(mins): {}", cacheKey, expire);

            return null;
        }

        return JSON.parseObject(this.stringRedisTemplate.opsForValue().get(cacheKey), OpenApiKeyInfo.class);
    }

    protected OpenApiUserInfo getApiUserInfoFromCache(final String apiKey) {
        final String cacheKey = StringUtils.join(OPEN_API_KEY_USER_ID_PREFIX, apiKey);

        // 返回key的剩余生存时间
        final Long expire = this.stringRedisTemplate.getExpire(cacheKey, TimeUnit.MINUTES);
        if (expire == null || expire <= 0 || expire > 10) {
            log.error("api key: {} not exist in redis, expire(mins): {}", cacheKey, expire);

            return null;
        }

        return JSON.parseObject(this.stringRedisTemplate.opsForValue().get(cacheKey), OpenApiUserInfo.class);
    }

    /**
     * 从远程client中获取OpenApiKeyInfo
     *
     * @param apiKey apiKey
     * @return {@link OpenApiKeyInfo}
     */
    protected abstract OpenApiKeyInfo getApiKeyInfoFromClient(final String apiKey);

    /**
     * 从远程client中获取OpenApiUserInfo
     *
     * @param apiKey apiKey
     * @param userId apiKey 用户id
     * @return {@link OpenApiUserInfo}
     */
    protected abstract OpenApiUserInfo getApiUserInfoFromClient(final String apiKey, final long userId);

}
