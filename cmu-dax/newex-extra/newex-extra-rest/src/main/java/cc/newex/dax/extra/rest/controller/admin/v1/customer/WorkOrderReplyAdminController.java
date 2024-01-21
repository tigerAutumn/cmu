package cc.newex.dax.extra.rest.controller.admin.v1.customer;


import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import cc.newex.dax.extra.common.enums.WorkOrderStatusEnum;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import cc.newex.dax.extra.domain.customer.WorkOrderReply;
import cc.newex.dax.extra.dto.customer.WorkOrderReplyDTO;
import cc.newex.dax.extra.service.admin.customer.WorkOrderAdminService;
import cc.newex.dax.extra.service.admin.customer.WorkOrderReplyAdminService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单回复表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Slf4j
@RestController
@RequestMapping(value = "/admin/v1/extra/customer/work-order/replies")
public class WorkOrderReplyAdminController {


    @Autowired
    private WorkOrderReplyAdminService workOrderReplyAdminService;
    @Autowired
    private WorkOrderAdminService workOrderAdminService;

    @PostMapping(value = "/{workOrderId}")
    public ResponseResult add(@PathVariable(value = "workOrderId") Long workOrderId, @RequestBody WorkOrderReplyDTO workOrderReplyDTO) {
        WorkOrder workOrder = this.workOrderAdminService.getById(workOrderId);
        ModelMapper modelMapper = new ModelMapper();
        WorkOrderReply po = modelMapper.map(workOrderReplyDTO, WorkOrderReply.class);
        //状态已经受理,回复之后更新状态
        if (workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_ALLOT.getCode()) {
            workOrder.setStatus(WorkOrderStatusEnum.WORK_ORDER_DEALING.getCode());
            workOrder.setResponseTime(new Date());
        }
        //状态为等待中,回复之后更新状态
        if (workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_PENDING.getCode()) {
            workOrder.setStatus(WorkOrderStatusEnum.WORK_ORDER_DEALING.getCode());
        }
        //状态为已完成，不允许回复
        if (workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_COMPLETE.getCode()) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.WRONG_OPERATION);
        }
        //todo 工单为待确认时，发出回复
        this.workOrderReplyAdminService.saveReplay(workOrder, po);
        return ResultUtils.success();
    }

    @GetMapping(value = "")
    public ResponseResult get(DataGridPager pager, WorkOrderReplyDTO workOrderReplyDTO) {
        PageInfo pageInfo = pager.toPageInfo();
        List<WorkOrderReply> list = this.workOrderReplyAdminService.listByPage(pageInfo, workOrderReplyDTO);
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }


    @PutMapping(value = "")
    public ResponseResult edit(@RequestBody WorkOrderReplyDTO workOrderReplyDTO) {
        ModelMapper modelMapper = new ModelMapper();
        WorkOrderReply po = modelMapper.map(workOrderReplyDTO, WorkOrderReply.class);
        return ResultUtils.success(this.workOrderReplyAdminService.editById(po));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(Long id) {
        return ResultUtils.success(this.workOrderReplyAdminService.removeById(id));
    }

    @GetMapping(value = "/{workOrderId}")
    public ResponseResult getByWorkOrderId(@PathVariable(value = "workOrderId") Long workOrderId) {

        List<WorkOrderReply> replies = this.workOrderReplyAdminService.getByWorkOrderId(workOrderId);
        return ResultUtils.success(replies);
    }

}