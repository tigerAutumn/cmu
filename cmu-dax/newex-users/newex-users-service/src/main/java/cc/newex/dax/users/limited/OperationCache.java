package cc.newex.dax.users.limited;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * 操作限制服务实现（使用第三方缓存来实现）
 */
@Slf4j
public class OperationCache {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 记录IP地址操作行为，锁定操作时间和次数
     *
     * @param ipAddress
     * @param behavior
     * @return
     */
    public boolean saveOperationIp(final long ipAddress, final BehaviorTheme behavior) {
        final String key = this.getKeyByIpAddress(ipAddress, behavior);
        if (key == null) {
            return false;
        }
        this.redisTemplate.opsForValue().set(key, System.currentTimeMillis() + "", 12 * 60 * 60, TimeUnit.SECONDS);
        log.info("[operation] ip:{}", key);
        return true;
    }

    /**
     * 记录 username 地址操作行为，锁定操作时间
     *
     * @param loginName
     * @param behavior
     * @return
     */
    public boolean saveOperationLoginName(final String loginName, final BehaviorTheme behavior) {
        final String key = this.getKeyByLoginName(loginName, behavior);
        if (key == null) {
            return false;
        }
        this.redisTemplate.opsForValue().set(key, System.currentTimeMillis() + "", 12 * 60 * 60, TimeUnit.SECONDS);
        log.info("[operation] :{}", key);
        return true;
    }

    /**
     * 该IP 指定行为是否操作太频繁
     *
     * @param ipAddress
     * @param behavior
     * @param interval  minute
     * @return
     */
    public boolean isFrequentlyByIp(final long ipAddress, final BehaviorTheme behavior, final int interval) {
        try {
            final String key = this.getKeyByIpAddress(ipAddress, behavior);
            if (key == null) {
                return true;
            }

            // 最后操作的时间
            final Long lastOptTime = StringUtils.isEmpty(this.redisTemplate.opsForValue().get(key)) ?
                    null :
                    new Long(this.redisTemplate.opsForValue().get(key));
            if (lastOptTime != null) {
                // 如果间隔时间大于指定时间则返回没操作频繁
                return (System.currentTimeMillis() - lastOptTime) / 1000 >= interval * 60;
            }
        } catch (final Exception e) {
            log.error("isFrequentlyByIp");
        }
        return false;
    }

    /**
     * 该用户指定行为是否操作太频繁
     *
     * @param loginName
     * @param behavior
     * @param interval  minute
     * @return
     */
    public boolean isFrequentlyByLoginName(final String loginName, final BehaviorTheme behavior, final int interval) {
        try {
            final String key = this.getKeyByLoginName(loginName, behavior);
            if (key == null) {
                return true;
            }
            // 最后操作的时间
            final Long lastOptTime = StringUtils.isEmpty(this.redisTemplate.opsForValue().get(key)) ?
                    null :
                    new Long(this.redisTemplate.opsForValue().get(key));
            if (lastOptTime != null) {
                // 如果间隔时间大于指定时间则返回没操作频繁
                return (System.currentTimeMillis() - lastOptTime) / 1000 >= interval * 60;
            }
        } catch (final Exception e) {
            log.error("isFrequentlyByLoginName");
        }
        return false;
    }

    private String getKeyByIpAddress(final long ipAddress, final BehaviorTheme behavior) {
        if (ipAddress <= 0 || behavior == null) {
            return null;
        }
        return "operation_" + ipAddress + "_" + behavior.getBehavior();
    }

    private String getKeyByLoginName(final String loginName, final BehaviorTheme behavior) {
        if (loginName == null || behavior == null) {
            return null;
        }
        return "operation_" + StringUtils.replaceAll(loginName, ",", "") + "_" + behavior.getBehavior();
    }
}