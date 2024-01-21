package cc.newex.dax.extra.service.customer;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderOplogExample;
import cc.newex.dax.extra.domain.customer.WorkOrderOplog;

/**
 * 工单操作记录表 服务接口
 *
 * @author newex-team
 * @date 2018-06-08
 */
public interface WorkOrderOplogService
        extends CrudService<WorkOrderOplog, WorkOrderOplogExample, Long> {
}
