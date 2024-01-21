package cc.newex.dax.boss.admin.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.admin.criteria.EventExample;
import cc.newex.dax.boss.admin.data.EventRepository;
import cc.newex.dax.boss.admin.domain.Event;
import cc.newex.dax.boss.admin.service.EventService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台系统用户操作事件表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class EventServiceImpl
        extends AbstractCrudService<EventRepository, Event, EventExample, Integer>
        implements EventService {

    @Autowired
    private EventRepository eventRepos;

    @Override
    protected EventExample getPageExample(final String fieldName, final String keyword) {
        final EventExample example = new EventExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public void clear() {
        this.eventRepos.deleteByExample(null);
    }
}