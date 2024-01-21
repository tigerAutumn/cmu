package cc.newex.dax.boss.admin.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.admin.criteria.EventHistoryExample;
import cc.newex.dax.boss.admin.domain.EventHistory;
import org.springframework.stereotype.Repository;

/**
 * 后台系统用户历史操作事件表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface EventHistoryRepository
        extends CrudRepository<EventHistory, EventHistoryExample, Integer> {
}