package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserBehaviorExample;
import cc.newex.dax.users.domain.UserBehavior;
import org.springframework.stereotype.Repository;

/**
 * 行为操作配置表 数据访问类
 *
 * @author newex-team
 * @date 2018-04-13
 */
@Repository
public interface UserBehaviorRepository
        extends CrudRepository<UserBehavior, UserBehaviorExample, Integer> {
}