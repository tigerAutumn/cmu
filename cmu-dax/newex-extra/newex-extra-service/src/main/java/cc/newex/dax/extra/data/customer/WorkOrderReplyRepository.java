package cc.newex.dax.extra.data.customer;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.customer.WorkOrderReplyExample;
import cc.newex.dax.extra.domain.customer.WorkOrderReply;
import org.springframework.stereotype.Repository;

/**
 * 工单回复表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface WorkOrderReplyRepository
        extends CrudRepository<WorkOrderReply, WorkOrderReplyExample, Long> {
}