package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserLoginEventExample;
import cc.newex.dax.users.domain.UserLoginEvent;
import org.springframework.stereotype.Repository;

/**
 * 用户登录事件表 数据访问类
 *
 * @author newex-team
 * @date 2018-04-06
 */
@Repository
public interface UserLoginEventRepository
        extends CrudRepository<UserLoginEvent, UserLoginEventExample, Long> {
}