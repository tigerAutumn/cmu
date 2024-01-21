package cc.newex.commons.ucenter.data.redis;

import cc.newex.commons.ucenter.config.SessionConfig;
import cc.newex.commons.ucenter.consts.SessionConsts;
import cc.newex.commons.ucenter.data.SessionRepository;
import cc.newex.commons.ucenter.model.SessionInfo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 * @author newex-team
 * @date 2018-06-28
 */
public class RedisSessionRepository
        implements SessionRepository<SessionInfo> {

    @Resource(name = "ucenterStringRedisTemplate")
    private StringRedisTemplate redisTemplate;
    @Resource
    private SessionConfig sessionConfig;

    @Override
    public void setMaxInactiveInterval(final long seconds) {
        this.redisTemplate.opsForValue().set(SessionConsts.SESSION_MAX_INACTIVE_INTERVAL, String.valueOf(seconds));
    }

    @Override
    public void save(final String sessionIdKey, final SessionInfo session) {
        if (session == null || session.getUserId() == null) {
            return;
        }

        final String userIdKey = SessionConsts.SESSION_USER_ID_PREFIX + session.getUserId();
        this.redisTemplate.opsForValue().set(
                sessionIdKey,
                session.getUserId().toString(),
                this.getMaxInactiveInterval(),
                TimeUnit.SECONDS
        );
        this.updateByUserIdKey(userIdKey, session);
    }

    @Override
    public void refresh(final String sessionIdKey) {
        this.redisTemplate.expire(
                sessionIdKey,
                this.getMaxInactiveInterval(),
                TimeUnit.SECONDS
        );
    }

    @Override
    public SessionInfo findBySessionIdKey(final String sessionIdKey) {
        final String userId = this.redisTemplate.opsForValue().get(sessionIdKey);
        if (StringUtils.isNotEmpty(userId)) {
            final String userIdKey = SessionConsts.SESSION_USER_ID_PREFIX + userId;
            return this.findByUserIdKey(userIdKey);
        }
        return null;
    }

    @Override
    public SessionInfo findByUserIdKey(final String userIdKey) {
        final String json = this.redisTemplate.opsForValue().get(userIdKey);
        if (StringUtils.isNotEmpty(json)) {
            return JSON.parseObject(json, SessionInfo.class);
        }
        return null;
    }

    @Override
    public void updateByUserIdKey(final String userIdKey, final SessionInfo session) {
        this.redisTemplate.opsForValue().set(userIdKey, JSON.toJSONString(session));
    }

    @Override
    public void delete(final String sessionIdKey) {
        this.redisTemplate.delete(sessionIdKey);
    }

    @Override
    public void deleteSessionUser(final String userIdKey) {
        this.redisTemplate.delete(userIdKey);
    }

    @Override
    public void setGlobalStatus(final String key, final Integer status) {
        this.redisTemplate.opsForValue().set(key, status.toString());
    }

    @Override
    public Integer getGlobalStatus(final String key) {
        return NumberUtils.toInt(this.redisTemplate.opsForValue().get(key), 0);
    }

    @Override
    public void deleteGlobalStatus(final String key) {
        this.redisTemplate.delete(key);
    }

    private long getMaxInactiveInterval() {
        final String value = this.redisTemplate.opsForValue().get(SessionConsts.SESSION_MAX_INACTIVE_INTERVAL);
        return NumberUtils.toLong(value, this.sessionConfig.getMaxInactiveInterval().getSeconds());
    }
}
