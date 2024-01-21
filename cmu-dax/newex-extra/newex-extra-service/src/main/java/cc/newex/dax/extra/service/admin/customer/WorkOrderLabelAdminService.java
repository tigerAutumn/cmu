package cc.newex.dax.extra.service.admin.customer;


import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderLabelExample;
import cc.newex.dax.extra.domain.customer.WorkOrderLabel;

/**
 * 工单标签记录表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface WorkOrderLabelAdminService
        extends CrudService<WorkOrderLabel, WorkOrderLabelExample, Long> {
}
