package cc.newex.dax.extra.service.admin.customer.impl;


import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.common.enums.WorkOrderTypeEnum;
import cc.newex.dax.extra.criteria.customer.WorkOrderReplyExample;
import cc.newex.dax.extra.data.customer.WorkOrderReplyRepository;
import cc.newex.dax.extra.data.customer.WorkOrderRepository;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import cc.newex.dax.extra.domain.customer.WorkOrderReply;
import cc.newex.dax.extra.dto.customer.WorkOrderReplyDTO;
import cc.newex.dax.extra.service.admin.customer.WorkOrderReplyAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

/**
 * 工单回复表 服务实现
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@Service
public class WorkOrderReplyAdminServiceImpl
        extends AbstractCrudService<WorkOrderReplyRepository, WorkOrderReply, WorkOrderReplyExample, Long>
        implements WorkOrderReplyAdminService {

    @Autowired
    private WorkOrderReplyRepository workOrderReplyRepos;
    @Autowired
    private WorkOrderRepository workOrderRepos;

    @Override
    protected WorkOrderReplyExample getPageExample(String fieldName, String keyword) {
        WorkOrderReplyExample example = new WorkOrderReplyExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    /**
     * 分页查询
     *
     * @param pageInfo
     * @param workOrderReplyDTO
     * @return
     */
    @Override
    public List<WorkOrderReply> listByPage(PageInfo pageInfo, WorkOrderReplyDTO workOrderReplyDTO) {
        WorkOrderReplyExample workOrderReplyExample = new WorkOrderReplyExample();
        WorkOrderReplyExample.Criteria criteria = workOrderReplyExample.createCriteria();
        if (Objects.nonNull(workOrderReplyDTO.getWorkOrderId()) && StringUtils.isNumeric(String.valueOf(workOrderReplyDTO.getWorkOrderId()))) {
            criteria.andWorkOrderIdEqualTo(workOrderReplyDTO.getWorkOrderId());
        }
        if (Objects.nonNull(workOrderReplyDTO.getAdminUserId()) && StringUtils.isNumeric(String.valueOf(workOrderReplyDTO.getAdminUserId()))) {
            criteria.andAdminUserIdEqualTo(workOrderReplyDTO.getAdminUserId());
        }
        return this.getByPage(pageInfo, workOrderReplyExample);
    }

    @Override
    public List<WorkOrderReply> getByWorkOrderId(Long workOrderId) {
        WorkOrderReplyExample workOrderReplyExample = new WorkOrderReplyExample();
        WorkOrderReplyExample.Criteria criteria = workOrderReplyExample.createCriteria();
        if (Objects.nonNull(workOrderId)) {
            criteria.andWorkOrderIdEqualTo(workOrderId);
        }
        return this.getByExample(workOrderReplyExample);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveReplay(WorkOrder workOrder, WorkOrderReply workOrderReply) {
        workOrderReply.setType(WorkOrderTypeEnum.WORK_ORDER_BY_CUSTOMER.getCode());
        this.workOrderRepos.updateById(workOrder);
        this.workOrderReplyRepos.insert(workOrderReply);
    }
}