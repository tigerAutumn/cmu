package cc.newex.dax.extra.service.admin.customer.impl;


import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderLabelExample;
import cc.newex.dax.extra.data.customer.WorkOrderLabelRepository;
import cc.newex.dax.extra.domain.customer.WorkOrderLabel;
import cc.newex.dax.extra.service.admin.customer.WorkOrderLabelAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工单标签记录表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class WorkOrderLabelAdminServiceImpl
        extends AbstractCrudService<WorkOrderLabelRepository, WorkOrderLabel, WorkOrderLabelExample, Long>
        implements WorkOrderLabelAdminService {

    @Autowired
    private WorkOrderLabelRepository workOrderLabelRepos;

    @Override
    protected WorkOrderLabelExample getPageExample(final String fieldName, final String keyword) {
        final WorkOrderLabelExample example = new WorkOrderLabelExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}