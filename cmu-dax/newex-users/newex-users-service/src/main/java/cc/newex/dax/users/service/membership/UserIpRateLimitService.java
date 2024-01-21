package cc.newex.dax.users.service.membership;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.users.criteria.UserIpRateLimitExample;
import cc.newex.dax.users.domain.UserIpRateLimit;

/**
 * Api流量限制配置表 服务接口
 *
 * @author newex-team
 * @date 2018-06-18
 */
public interface UserIpRateLimitService
        extends CrudService<UserIpRateLimit, UserIpRateLimitExample, Integer> {

    UserIpRateLimit getUserIpRateLimitByIp(final Long ip);

    void loadAllUserIpRateLimitToCache();
}
