package cc.newex.dax.extra.service.customer.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderTemplateExample;
import cc.newex.dax.extra.data.customer.WorkOrderTemplateRepository;
import cc.newex.dax.extra.domain.customer.WorkOrderTemplate;
import cc.newex.dax.extra.service.customer.WorkOrderTemplateService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 工单问题模版表 服务实现
 *
 * @author newex-team
 * @date 2018-06-08
 */
@Slf4j
@Service
public class WorkOrderTemplateServiceImpl
        extends AbstractCrudService<WorkOrderTemplateRepository, WorkOrderTemplate, WorkOrderTemplateExample, Integer>
        implements WorkOrderTemplateService {

    @Autowired
    private WorkOrderTemplateRepository workOrderTemplateRepos;

    @Override
    protected WorkOrderTemplateExample getPageExample(String fieldName, String keyword) {
        WorkOrderTemplateExample example = new WorkOrderTemplateExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }


}