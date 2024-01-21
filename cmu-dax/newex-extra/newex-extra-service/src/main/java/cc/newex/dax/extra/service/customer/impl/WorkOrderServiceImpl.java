package cc.newex.dax.extra.service.customer.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.common.enums.WorkOrderStatusEnum;
import cc.newex.dax.extra.criteria.customer.WorkOrderExample;
import cc.newex.dax.extra.data.customer.WorkOrderRepository;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import cc.newex.dax.extra.dto.customer.WorkOrderDTO;
import cc.newex.dax.extra.service.customer.WorkOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 工单表 服务实现
 *
 * @author newex-team
 * @date 2018-06-08
 */
@Slf4j
@Service
public class WorkOrderServiceImpl
        extends AbstractCrudService<WorkOrderRepository, WorkOrder, WorkOrderExample, Long>
        implements WorkOrderService {

    @Autowired
    private WorkOrderRepository workOrderRepos;

    @Override
    protected WorkOrderExample getPageExample(String fieldName, String keyword) {
        WorkOrderExample example = new WorkOrderExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * limit 10, 按条件查询工单
     *
     * @param workOrderDTO
     * @return
     */
    @Override
    public List<WorkOrder> listByExample(WorkOrderDTO workOrderDTO) {
        WorkOrderExample workOrderExample = new WorkOrderExample();
        WorkOrderExample.Criteria criteria = workOrderExample.createCriteria();
        if (Objects.nonNull(workOrderDTO.getStatus())) {
            criteria.andStatusEqualTo(workOrderDTO.getStatus());
        }
        return this.workOrderRepos.listByExample(workOrderExample);

    }

    /**
     * 统计工单数
     *
     * @param workOrderDTO
     * @return
     */
    @Override
    public Integer getOrderNumber(WorkOrderDTO workOrderDTO) {
        WorkOrderExample workOrderExample = new WorkOrderExample();
        WorkOrderExample.Criteria criteria = workOrderExample.createCriteria();
        if (Objects.nonNull(workOrderDTO.getUserId())) {
            criteria.andUserIdEqualTo(workOrderDTO.getUserId());
        }
        if (Objects.nonNull(workOrderDTO.getStatus())) {
            criteria.andStatusEqualTo(workOrderDTO.getStatus());
        }
        return this.workOrderRepos.getOrderNumber(workOrderExample);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int confirm(Long workOrderId) {
        WorkOrder workOrder = this.getById(workOrderId);
        if (workOrder == null) {
            return 0;
        }
        //只有状态为待确认，才需要设置已完成
        if (workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_UN_CONFIRMED.getCode()) {
            workOrder.setStatus(WorkOrderStatusEnum.WORK_ORDER_COMPLETE.getCode());
            return this.editById(workOrder);
        }
        return 0;

    }

    @Override
    public List<WorkOrder> getWorkOrderByUserId(Long userId, WorkOrderDTO workOrderDTO) {
        WorkOrderExample example = new WorkOrderExample();
        WorkOrderExample.Criteria criteria = example.createCriteria();
        if (Objects.nonNull(workOrderDTO.getUserId())) {
            criteria.andUserIdEqualTo(workOrderDTO.getUserId());
        }
        if (Objects.nonNull(workOrderDTO.getMenuId())) {
            criteria.andMenuIdEqualTo(workOrderDTO.getMenuId());
        }
        if (Objects.nonNull(workOrderDTO.getStatus())) {
            //用户只显示已解决和未解决状态的工单列表
            if (workOrderDTO.getStatus() == WorkOrderStatusEnum.WORK_ORDER_COMPLETE.getCode()) {
                criteria.andStatusEqualTo(workOrderDTO.getStatus());
            } else {
                criteria.andStatusNotEqualTo(WorkOrderStatusEnum.WORK_ORDER_COMPLETE.getCode());
            }
        }
        if (Objects.nonNull(workOrderDTO.getCreatedDate())) {
            criteria.andCreatedDateGreaterThanOrEqualTo(workOrderDTO.getCreatedDate());
        }
        return this.workOrderRepos.selectByExample(example);
    }
}