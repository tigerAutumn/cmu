package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserLimitedBehaviorExample;
import cc.newex.dax.users.domain.UserLimitedBehavior;
import org.springframework.stereotype.Repository;

/**
 * 用户受限制行为表 数据访问类
 *
 * @author newex-team
 * @date 2018-04-06
 */
@Repository
public interface UserLimitedBehaviorRepository
        extends CrudRepository<UserLimitedBehavior, UserLimitedBehaviorExample, Long> {
}