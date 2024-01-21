package cc.newex.dax.boss.admin.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.admin.criteria.EventExample;
import cc.newex.dax.boss.admin.domain.Event;
import org.springframework.stereotype.Repository;

/**
 * 后台系统用户操作事件表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface EventRepository
        extends CrudRepository<Event, EventExample, Integer> {
}