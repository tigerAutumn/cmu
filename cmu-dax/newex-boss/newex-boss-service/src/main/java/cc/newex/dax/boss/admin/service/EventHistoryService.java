package cc.newex.dax.boss.admin.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.admin.criteria.EventHistoryExample;
import cc.newex.dax.boss.admin.domain.EventHistory;

/**
 * 后台系统用户历史操作事件表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface EventHistoryService
        extends CrudService<EventHistory, EventHistoryExample, Integer> {
}
