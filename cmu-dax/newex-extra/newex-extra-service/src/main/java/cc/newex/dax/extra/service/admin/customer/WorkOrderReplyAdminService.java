package cc.newex.dax.extra.service.admin.customer;


import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderReplyExample;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import cc.newex.dax.extra.domain.customer.WorkOrderReply;
import cc.newex.dax.extra.dto.customer.WorkOrderReplyDTO;

import java.util.List;

/**
 * 工单回复表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface WorkOrderReplyAdminService
        extends CrudService<WorkOrderReply, WorkOrderReplyExample, Long> {
    /**
     * 分页查询
     *
     * @param pageInfo
     * @param workOrderReplyDTO
     * @return
     */
    List<WorkOrderReply> listByPage(PageInfo pageInfo, WorkOrderReplyDTO workOrderReplyDTO);

    /**
     * 根据工单Id查询回复
     *
     * @param workOrderId
     * @return
     */
    List<WorkOrderReply> getByWorkOrderId(Long workOrderId);

    /**
     * 客户发送回复
     *
     * @param workOrder
     * @param workOrderReply
     * @return
     */
    void saveReplay(WorkOrder workOrder, WorkOrderReply workOrderReply);
}
