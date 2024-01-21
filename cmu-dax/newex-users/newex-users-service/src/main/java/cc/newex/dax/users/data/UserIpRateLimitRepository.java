package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserIpRateLimitExample;
import cc.newex.dax.users.domain.UserIpRateLimit;
import org.springframework.stereotype.Repository;

/**
 * Api流量限制配置表 数据访问类
 *
 * @author newex-team
 * @date 2018-06-18
 */
@Repository
public interface UserIpRateLimitRepository
        extends CrudRepository<UserIpRateLimit, UserIpRateLimitExample, Integer> {
}