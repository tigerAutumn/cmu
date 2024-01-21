package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserLevelExample;
import cc.newex.dax.users.domain.UserLevel;
import org.springframework.stereotype.Repository;

/**
 * 用户等级表 数据访问类
 *
 * @author newex-team
 * @date 2018-07-07
 */
@Repository
public interface UserLevelRepository
        extends CrudRepository<UserLevel, UserLevelExample, Long> {
}