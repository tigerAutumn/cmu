package cc.newex.dax.extra.service.customer;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderReplyExample;
import cc.newex.dax.extra.domain.customer.WorkOrderReply;

/**
 * 工单回复表 服务接口
 *
 * @author newex-team
 * @date 2018-06-08
 */
public interface WorkOrderReplyService
        extends CrudService<WorkOrderReply, WorkOrderReplyExample, Long> {


}
