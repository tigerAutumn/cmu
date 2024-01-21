package cc.newex.dax.extra.service.customer.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderMenuExample;
import cc.newex.dax.extra.data.customer.WorkOrderMenuRepository;
import cc.newex.dax.extra.domain.customer.WorkOrderMenu;
import cc.newex.dax.extra.service.customer.WorkOrderMenuService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 工单菜单表 服务实现
 *
 * @author newex-team
 * @date 2018-06-08
 */
@Slf4j
@Service
public class WorkOrderMenuServiceImpl
        extends AbstractCrudService<WorkOrderMenuRepository, WorkOrderMenu, WorkOrderMenuExample, Integer>
        implements WorkOrderMenuService {

    @Override
    protected WorkOrderMenuExample getPageExample(String fieldName, String keyword) {
        WorkOrderMenuExample example = new WorkOrderMenuExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}