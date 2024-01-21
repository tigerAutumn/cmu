package cc.newex.dax.users.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.users.criteria.UserNoticeEventExample;
import cc.newex.dax.users.domain.UserNoticeEvent;
import org.springframework.stereotype.Repository;

/**
 * 用户通知事件表 数据访问类
 *
 * @author newex-team
 * @date 2018-04-06
 */
@Repository
public interface UserNoticeEventRepository
        extends CrudRepository<UserNoticeEvent, UserNoticeEventExample, Long> {
}