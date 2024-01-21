package cc.newex.dax.extra.service.admin.customer;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderOplogExample;
import cc.newex.dax.extra.domain.customer.WorkOrderOplog;

/**
 * 工单操作记录表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface WorkOrderOplogAdminService
        extends CrudService<WorkOrderOplog, WorkOrderOplogExample, Long> {
}
