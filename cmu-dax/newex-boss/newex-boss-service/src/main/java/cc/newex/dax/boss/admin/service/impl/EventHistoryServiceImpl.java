package cc.newex.dax.boss.admin.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.admin.criteria.EventHistoryExample;
import cc.newex.dax.boss.admin.data.EventHistoryRepository;
import cc.newex.dax.boss.admin.domain.EventHistory;
import cc.newex.dax.boss.admin.service.EventHistoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台系统用户历史操作事件表 服务实现
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class EventHistoryServiceImpl
        extends AbstractCrudService<EventHistoryRepository, EventHistory, EventHistoryExample, Integer>
        implements EventHistoryService {

    @Autowired
    private EventHistoryRepository eventHistoryRepos;

    @Override
    protected EventHistoryExample getPageExample(final String fieldName, final String keyword) {
        final EventHistoryExample example = new EventHistoryExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}