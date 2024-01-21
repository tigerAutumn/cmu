package cc.newex.dax.extra.data.customer;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.customer.WorkOrderLabelExample;
import cc.newex.dax.extra.domain.customer.WorkOrderLabel;
import org.springframework.stereotype.Repository;

/**
 * 工单标签记录表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface WorkOrderLabelRepository
        extends CrudRepository<WorkOrderLabel, WorkOrderLabelExample, Long> {
}