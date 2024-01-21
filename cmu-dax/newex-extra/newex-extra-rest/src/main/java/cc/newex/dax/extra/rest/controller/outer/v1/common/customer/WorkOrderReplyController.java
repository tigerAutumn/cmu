package cc.newex.dax.extra.rest.controller.outer.v1.common.customer;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import cc.newex.dax.extra.common.enums.WorkOrderStatusEnum;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import cc.newex.dax.extra.domain.customer.WorkOrderReply;
import cc.newex.dax.extra.dto.customer.WorkOrderReplyDTO;
import cc.newex.dax.extra.service.customer.WorkOrderReplyService;
import cc.newex.dax.extra.service.customer.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

/**
 * 工单回复表 控制器类
 *
 * @author newex-team
 * @date 2018-06-08
 */
@RestController
@RequestMapping(value = "/v1/extra/customer/work-order-replies")
public class WorkOrderReplyController {
    @Autowired
    WorkOrderService workOrderService;
    @Autowired
    private WorkOrderReplyService workOrderReplyService;

    @PostMapping(value = "/reply")
    @OpLog(name = "用户发送回复")
    public ResponseResult reply(@RequestBody WorkOrderReplyDTO workOrderReplyDTO) {
        WorkOrder workOrder = this.workOrderService.getById(workOrderReplyDTO.getWorkOrderId());
        if (workOrder == null) {
            return ResultUtils.failure("null");
        }
        if (workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_UN_ALLOT.getCode() ||
                workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_COMPLETE.getCode() ||
                workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_UN_CONFIRMED.getCode()) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.WRONG_OPERATION);
        }
        WorkOrderReply workOrderReply = WorkOrderReply.builder().build();
        if (Objects.nonNull(workOrderReplyDTO.getReply()) && Objects.nonNull(workOrderReplyDTO.getWorkOrderId())) {
            workOrderReply.setReply(workOrderReplyDTO.getReply());
            workOrderReply.setWorkOrderId(workOrder.getId());
            workOrderReply.setType(0);
        } else {
            return ResultUtils.failure("");
        }
        return ResultUtils.success(this.workOrderReplyService.add(workOrderReply));
    }
}