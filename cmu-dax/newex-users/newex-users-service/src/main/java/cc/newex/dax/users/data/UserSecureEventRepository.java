package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserSecureEventExample;
import cc.newex.dax.users.domain.UserSecureEvent;
import org.springframework.stereotype.Repository;

/**
 * 用户安全事件表 数据访问类
 *
 * @author newex-team
 * @date 2018-04-06
 */
@Repository
public interface UserSecureEventRepository
        extends CrudRepository<UserSecureEvent, UserSecureEventExample, Long> {
}