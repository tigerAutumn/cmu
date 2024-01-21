package cc.newex.dax.extra.data.customer;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.customer.WorkOrderExample;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 工单表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface WorkOrderRepository
        extends CrudRepository<WorkOrder, WorkOrderExample, Long> {

    /**
     * 按条件获取工单数
     *
     * @param workOrderExample
     * @return
     */
    List<WorkOrder> listByExample(@Param("example") WorkOrderExample workOrderExample);


    /**
     * 按条件统计工单数
     *
     * @param workOrderExample
     * @return
     */
    Integer getOrderNumber(@Param("example") WorkOrderExample workOrderExample);

    /**
     * 按工单类型查询
     * @param workOrderExample
     * @return
     */
    List<WorkOrder> getListByExample(@Param("example") WorkOrderExample workOrderExample);
}