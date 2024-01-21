package cc.newex.spring.boot.autoconfigure.session;

import cc.newex.commons.ucenter.consts.SessionConsts;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author newex-team
 * @date 2017/12/09
 */
@ConfigurationProperties("newex.session")
public class SessionProperties {
    private int maxInactiveInterval = SessionConsts.DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS;

    /**
     * 获取session过期时间(default 10 minutes),如果为负数表示永不过期(单位:秒)
     */
    public int getMaxInactiveInterval() {
        return this.maxInactiveInterval < 0 ? Integer.MAX_VALUE : this.maxInactiveInterval;
    }

    /**
     * 获取session过期时间(default 10 minutes),如果为负数表示永不过期(单位:秒)
     *
     * @param maxInactiveInterval session过期时间(单位:秒)
     */
    public void setMaxInactiveInterval(final int maxInactiveInterval) {
        this.maxInactiveInterval = maxInactiveInterval;
    }
}
