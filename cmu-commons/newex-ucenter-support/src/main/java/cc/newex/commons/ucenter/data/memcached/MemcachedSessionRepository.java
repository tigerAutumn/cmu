package cc.newex.commons.ucenter.data.memcached;

import cc.newex.commons.memcached.repository.MemcachedRepository;
import cc.newex.commons.ucenter.config.SessionConfig;
import cc.newex.commons.ucenter.consts.SessionConsts;
import cc.newex.commons.ucenter.data.SessionRepository;
import cc.newex.commons.ucenter.model.SessionInfo;
import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.math.NumberUtils;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author newex-team
 * @date 2018-06-28
 */
public class MemcachedSessionRepository
        implements SessionRepository<SessionInfo> {

    @Resource(name = "ucenterMemcachedRepository")
    private MemcachedRepository memcachedRepository;
    @Resource
    private SessionConfig sessionConfig;

    @Override
    public void setMaxInactiveInterval(final long seconds) {
        this.memcachedRepository.put(SessionConsts.SESSION_MAX_INACTIVE_INTERVAL, seconds);
    }

    @Override
    public void save(final String sessionIdKey, final SessionInfo session) {
        if (session == null || session.getUserId() == null) {
            return;
        }

        final String userIdKey = SessionConsts.SESSION_USER_ID_PREFIX + session.getUserId();
        this.memcachedRepository.put(
                sessionIdKey,
                session.getUserId().toString(),
                this.getMaxInactiveInterval() * 1000
        );
        this.updateByUserIdKey(userIdKey, session);
    }

    @Override
    public void refresh(final String sessionIdKey) {
        final SessionInfo session = this.findBySessionIdKey(sessionIdKey);
        if (Objects.nonNull(session)) {
            this.save(sessionIdKey, session);
        }
    }

    @Override
    public SessionInfo findBySessionIdKey(final String sessionIdKey) {
        final Object value = this.memcachedRepository.get(sessionIdKey);
        if (Objects.nonNull(value)) {
            final String userIdKey = SessionConsts.SESSION_USER_ID_PREFIX + value.toString();
            return this.findByUserIdKey(userIdKey);
        }
        return null;
    }

    @Override
    public SessionInfo findByUserIdKey(final String userIdKey) {
        final Object value = this.memcachedRepository.get(userIdKey);
        if (Objects.nonNull(value)) {
            return JSON.parseObject(value.toString(), SessionInfo.class);
        }
        return null;
    }

    @Override
    public void updateByUserIdKey(final String userIdKey, final SessionInfo session) {
        this.memcachedRepository.put(
                userIdKey,
                JSON.toJSONString(session),
                this.getMaxInactiveInterval() * 1000
        );
    }

    @Override
    public void delete(final String sessionIdKey) {
        this.memcachedRepository.remove(sessionIdKey);
    }

    @Override
    public void deleteSessionUser(final String userIdKey) {
        this.memcachedRepository.remove(userIdKey);
    }

    @Override
    public void setGlobalStatus(final String key, final Integer status) {
        this.memcachedRepository.put(key, status);
    }

    @Override
    public Integer getGlobalStatus(final String key) {
        return (Integer) this.memcachedRepository.get(key);
    }

    @Override
    public void deleteGlobalStatus(final String key) {
        this.memcachedRepository.remove(key);
    }

    private long getMaxInactiveInterval() {
        final Object value = this.memcachedRepository.get(SessionConsts.SESSION_MAX_INACTIVE_INTERVAL);
        if (value == null) {
            return this.sessionConfig.getMaxInactiveInterval().getSeconds();
        }
        return NumberUtils.toLong(value.toString(), this.sessionConfig.getMaxInactiveInterval().getSeconds());
    }
}
