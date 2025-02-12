package cc.newex.commons.ucenter.service.impl;

import cc.newex.commons.ucenter.consts.SessionConsts;
import cc.newex.commons.ucenter.data.SessionRepository;
import cc.newex.commons.ucenter.model.SessionInfo;
import cc.newex.commons.ucenter.service.SessionService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.Validate;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * @author newex-team
 * @date 2018-06-29
 */
@Slf4j
public class SessionServiceImpl implements SessionService<SessionInfo> {
    @Resource(type = SessionRepository.class)
    private SessionRepository<SessionInfo> sessionRepository;

    @Override
    public void setMaxInactiveInterval(final long seconds) {
        this.sessionRepository.setMaxInactiveInterval(seconds);
    }

    @Override
    public void save(final String token, final SessionInfo session) {
        Validate.notNull(session);
        final String sessionIdKey = this.getSessionIdKey(token);
        this.sessionRepository.save(sessionIdKey, session);
    }

    @Override
    public void updateByUserId(final SessionInfo newSession) {
        Validate.notNull(newSession);
        Validate.notNull(newSession.getUserId());

        final String userIdKey = this.getUserIdKey(newSession.getUserId());
        final SessionInfo session = this.getByUserId(newSession.getUserId());
        if (Objects.nonNull(session)) {
            if (newSession.getStatus() != null) {
                session.setStatus(newSession.getStatus());
            }
            if (newSession.getFrozen() != null) {
                session.setFrozen(newSession.getFrozen());
            }
            if (newSession.getC2cFrozen() != null) {
                session.setC2cFrozen(newSession.getC2cFrozen());
            }
            if (newSession.getSpotFrozen() != null) {
                session.setSpotFrozen(newSession.getSpotFrozen());
            }
            if (newSession.getContractsFrozen() != null) {
                session.setContractsFrozen(newSession.getContractsFrozen());
            }
            if (newSession.getAssetFrozen() != null) {
                session.setAssetFrozen(newSession.getAssetFrozen());
            }
            if (newSession.getPerpetualProtocolFlag() != null) {
                session.setPerpetualProtocolFlag(newSession.getPerpetualProtocolFlag());
            }
            this.sessionRepository.updateByUserIdKey(userIdKey, session);
        } else {
            log.warn("userId:{} session not found", newSession.getUserId());
        }
    }

    @Override
    public void refresh(final String token) {
        final String sessionIdKey = this.getSessionIdKey(token);
        this.sessionRepository.refresh(sessionIdKey);
    }

    @Override
    public SessionInfo getByToken(final String token) {
        final String sessionIdKey = this.getSessionIdKey(token);
        return this.sessionRepository.findBySessionIdKey(sessionIdKey);
    }

    @Override
    public SessionInfo getByUserId(final long userId) {
        return this.sessionRepository.findByUserIdKey(this.getUserIdKey(userId));
    }

    @Override
    public void remove(final String token) {
        final String sessionIdKey = this.getSessionIdKey(token);
        this.sessionRepository.delete(sessionIdKey);
    }

    @Override
    public void removeSessionUser(final long userId) {
        final String userIdKey = this.getUserIdKey(userId);
        this.sessionRepository.deleteSessionUser(userIdKey);
    }

    @Override
    public String getSessionIdKey(final String token) {
        return SessionConsts.SESSION_ID_PREFIX + DigestUtils.sha256Hex(token);
    }

    @Override
    public String getUserIdKey(final long userId) {
        return SessionConsts.SESSION_USER_ID_PREFIX + userId;
    }

    @Override
    public void setGlobalStatus(final String key, final Integer status) {
        this.sessionRepository.setGlobalStatus(key, status);
    }

    @Override
    public Integer getGlobalStatus(final String key) {
        return this.sessionRepository.getGlobalStatus(key);
    }

    @Override
    public void deleteGlobalStatus(final String key) {
        this.sessionRepository.deleteGlobalStatus(key);
    }
}
