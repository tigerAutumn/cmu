package cc.newex.dax.boss.admin.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.admin.criteria.EventExample;
import cc.newex.dax.boss.admin.domain.Event;

/**
 * 后台系统用户操作事件表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface EventService
        extends CrudService<Event, EventExample, Integer> {

    /**
     * 清除所有事件
     */
    void clear();
}
