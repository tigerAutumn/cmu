package cc.newex.dax.extra.service.customer;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderMenuExample;
import cc.newex.dax.extra.domain.customer.WorkOrderMenu;

/**
 * 工单菜单表 服务接口
 *
 * @author newex-team
 * @date 2018-06-08
 */
public interface WorkOrderMenuService
        extends CrudService<WorkOrderMenu, WorkOrderMenuExample, Integer> {
}
