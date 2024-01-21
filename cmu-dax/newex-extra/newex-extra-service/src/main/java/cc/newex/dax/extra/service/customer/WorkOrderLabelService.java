package cc.newex.dax.extra.service.customer;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderLabelExample;
import cc.newex.dax.extra.domain.customer.WorkOrderLabel;

/**
 * 工单标签记录表 服务接口
 *
 * @author newex-team
 * @date 2018-06-08
 */
public interface WorkOrderLabelService
        extends CrudService<WorkOrderLabel, WorkOrderLabelExample, Long> {
}
