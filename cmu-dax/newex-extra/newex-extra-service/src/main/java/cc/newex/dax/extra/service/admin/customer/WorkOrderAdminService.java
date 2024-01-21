package cc.newex.dax.extra.service.admin.customer;


import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.customer.WorkOrderExample;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import cc.newex.dax.extra.dto.customer.WorkOrderDTO;

import java.util.List;

/**
 * 工单表 服务接口
 *
 * @author newex-team
 * @date 2018-05-30
 */
public interface WorkOrderAdminService
        extends CrudService<WorkOrder, WorkOrderExample, Long> {
    /**
     * 分页查询
     *
     * @param pageInfo
     * @param workOrderDTO
     * @return
     */
    List<WorkOrder> listByPage(PageInfo pageInfo, WorkOrderDTO workOrderDTO);

    /**
     * 个人工单按条件查询
     *
     * @param pageInfo
     * @param workOrderDTO
     * @return
     */
    List<WorkOrder> getListByPage(PageInfo pageInfo, WorkOrderDTO workOrderDTO);

    /**
     * 获取工单(admin)
     *
     * @param workOrderDTO
     * @return
     */
    List<WorkOrder> listByAdminUser(WorkOrderDTO workOrderDTO);
}
