package cc.newex.dax.extra.service.customer;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderExample;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import cc.newex.dax.extra.dto.customer.WorkOrderDTO;

import java.util.List;

/**
 * 工单表 服务接口
 *
 * @author newex-team
 * @date 2018-06-08
 */
public interface WorkOrderService
        extends CrudService<WorkOrder, WorkOrderExample, Long> {

    /**
     * limit 10, 按条件查询工单
     *
     * @param workOrderDTO
     * @return
     */
    List<WorkOrder> listByExample(WorkOrderDTO workOrderDTO);

    /**
     * 统计工单数
     *
     * @param workOrderDTO
     * @return
     */
    Integer getOrderNumber(WorkOrderDTO workOrderDTO);

    /**
     * 用户确认工单已解决
     *
     * @param workOrderId
     * @return
     */
    int confirm(Long workOrderId);

    /**
     * 根据用户Id获得工单列表
     *
     * @param userId
     * @param workOrderDTO
     * @return
     */
    List<WorkOrder> getWorkOrderByUserId(Long userId, WorkOrderDTO workOrderDTO);
}
