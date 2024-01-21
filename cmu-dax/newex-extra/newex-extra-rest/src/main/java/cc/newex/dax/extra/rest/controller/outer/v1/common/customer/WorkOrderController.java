package cc.newex.dax.extra.rest.controller.outer.v1.common.customer;

import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.WorkOrderStatusEnum;
import cc.newex.dax.extra.common.enums.WorkOrderTypeEnum;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import cc.newex.dax.extra.dto.customer.WorkOrderDTO;
import cc.newex.dax.extra.service.customer.WorkOrderService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * 工单表 控制器类
 *
 * @author newex-team
 * @date 2018-06-08
 */
@Slf4j
@RestController
@RequestMapping("/v1/extra/customer/work-orders")
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping(value = "/getWorkOrderByUserId")
    @OpLog(name = "用户查看工单列表")
    public ResponseResult getWorkOrderByUserId(@RequestParam(value = "userId") Long userId, @RequestBody WorkOrderDTO workOrderDTO) {
        List<WorkOrder> source = this.workOrderService.getWorkOrderByUserId(userId, workOrderDTO);
        ModelMapper mapper = new ModelMapper();
        List<WorkOrderDTO> list = mapper.map(source, new TypeToken<List<WorkOrderDTO>>() {
        }.getType());
        return ResultUtils.success(list);
    }

    @PutMapping(value = "/save")
    @OpLog(name = "用户发布工单")
    public ResponseResult save(@RequestBody WorkOrderDTO workOrderDTO) {
        String locale = LocaleUtils.getLocale();
        if (Objects.nonNull(workOrderDTO)) {
            workOrderDTO.setStatus(WorkOrderStatusEnum.WORK_ORDER_UN_ALLOT.getCode());
            workOrderDTO.setFromType(WorkOrderTypeEnum.WORK_ORDER_BY_USER.getCode());
            workOrderDTO.setLocale(locale);
            ModelMapper map = new ModelMapper();
            WorkOrder workOrder = map.map(workOrderDTO, WorkOrder.class);
            return ResultUtils.success(this.workOrderService.add(workOrder));
        }
        return ResultUtils.success();
    }

    @PostMapping(value = "/confirm")
    @OpLog(name = "用户确认订单已解决")
    public ResponseResult confirm(@RequestParam(value = "workOrderId") Long workOrderId) {
        this.workOrderService.confirm(workOrderId);
        return ResultUtils.success();
    }

}