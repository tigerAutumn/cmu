package cc.newex.dax.extra.data.customer;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.customer.WorkOrderOplogExample;
import cc.newex.dax.extra.domain.customer.WorkOrderOplog;
import org.springframework.stereotype.Repository;

/**
 * 工单操作记录表 数据访问类
 *
 * @author newex-team
 * @date 2018-06-14
 */
@Repository
public interface WorkOrderOplogRepository
        extends CrudRepository<WorkOrderOplog, WorkOrderOplogExample, Long> {
}