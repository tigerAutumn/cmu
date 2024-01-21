package cc.newex.dax.extra.service.customer.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderOplogExample;
import cc.newex.dax.extra.data.customer.WorkOrderOplogRepository;
import cc.newex.dax.extra.domain.customer.WorkOrderOplog;
import cc.newex.dax.extra.service.customer.WorkOrderOplogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工单操作记录表 服务实现
 *
 * @author newex-team
 * @date 2018-06-08
 */
@Slf4j
@Service
public class WorkOrderOplogServiceImpl
        extends AbstractCrudService<WorkOrderOplogRepository, WorkOrderOplog, WorkOrderOplogExample, Long>
        implements WorkOrderOplogService {

    @Autowired
    private WorkOrderOplogRepository workOrderOplogRepos;

    @Override
    protected WorkOrderOplogExample getPageExample(final String fieldName, final String keyword) {
        final WorkOrderOplogExample example = new WorkOrderOplogExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}