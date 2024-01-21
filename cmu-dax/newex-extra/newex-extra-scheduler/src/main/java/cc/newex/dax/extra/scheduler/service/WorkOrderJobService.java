package cc.newex.dax.extra.scheduler.service;

/**
 * @author newex-team
 * @date 2018/6/15
 */
public interface WorkOrderJobService {

    /**
     * 分配工单
     */
    void allotWorkOrder();

    /**
     * 完成未评价的工单（超过30分钟）
     */
    void completeWorkOrder();

}
