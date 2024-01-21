package cc.newex.dax.extra.rest.controller.admin.v1.customer;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.ExtraBizErrorCodeEnum;
import cc.newex.dax.extra.common.enums.WorkOrderSourceEnum;
import cc.newex.dax.extra.common.enums.WorkOrderStatusEnum;
import cc.newex.dax.extra.domain.customer.WorkOrder;
import cc.newex.dax.extra.dto.customer.WorkOrderCustomerDTO;
import cc.newex.dax.extra.dto.customer.WorkOrderDTO;
import cc.newex.dax.extra.service.admin.customer.WorkOrderAdminService;
import cc.newex.dax.extra.service.admin.customer.WorkOrderMenuAdminService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 工单表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@RestController
@RequestMapping(value = "/admin/v1/extra/customer/work-orders")
public class WorkOrderAdminController {

    @Autowired
    private WorkOrderAdminService workOrderAdminService;

    @Autowired
    private WorkOrderMenuAdminService workOrderMenuAdminService;

    @PostMapping(value = "")
    public ResponseResult add(@RequestBody final WorkOrderDTO workOrderDTO) {
        final ModelMapper modelMapper = new ModelMapper();
        final WorkOrder po = modelMapper.map(workOrderDTO, WorkOrder.class);
        this.workOrderAdminService.add(po);
        return ResultUtils.success(po);
    }


    @PostMapping(value = "/list")
    public ResponseResult get(final DataGridPager pager, @RequestBody final WorkOrderDTO workOrderDTO) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<WorkOrder> list = this.workOrderAdminService.listByPage(pageInfo, workOrderDTO);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }


    @GetMapping(value = "/{id}")
    public ResponseResult getById(final Long id) {
        return ResultUtils.success(this.workOrderAdminService.getById(id));
    }


    @PutMapping(value = "")
    public ResponseResult edit(@RequestBody final WorkOrderDTO workOrderDTO) {
        final ModelMapper modelMapper = new ModelMapper();
        final WorkOrder po = modelMapper.map(workOrderDTO, WorkOrder.class);
        return ResultUtils.success(this.workOrderAdminService.editById(po));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(final Long id) {
        return ResultUtils.success(this.workOrderAdminService.removeById(id));
    }

    @PostMapping(value = "/transfer")
    public ResponseResult transfer(@RequestParam(value = "id") final Long id,
                                   @RequestBody final WorkOrderCustomerDTO workOrderCustomerDTO) {
        final WorkOrderDTO workOrderDTO = WorkOrderDTO.builder()
                .id(id)
                .source(WorkOrderSourceEnum.WORK_ORDER_SOURCE_TRANSPOND.getCode())
                .adminUserId(workOrderCustomerDTO.getAdminUserId())
                .adminAccount(workOrderCustomerDTO.getAdminAccount())
                .adminName(workOrderCustomerDTO.getAdminName()).build();
        final ModelMapper modelMapper = new ModelMapper();
        final WorkOrder po = modelMapper.map(workOrderDTO, WorkOrder.class);
        return ResultUtils.success(this.workOrderAdminService.editById(po));
    }

    @PostMapping(value = "/workOrderList")
    public ResponseResult getListByPage(@RequestBody final DataGridPager<WorkOrderDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<WorkOrder> list = this.workOrderAdminService.getListByPage(pageInfo, pager.getQueryParameter());
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), list));
    }

    @GetMapping(value = "/workOrderStatusList")
    public ResponseResult getListByStatus(@RequestBody final DataGridPager pager,
                                          @RequestParam(value = "filedName", required = false) final String filedName,
                                          @RequestParam(value = "keyword", required = false) final String keyword,
                                          @RequestParam(value = "adminUserId") final Integer adminUserId,
                                          @RequestParam(value = "status") final Integer status) {
        final PageInfo pageInfo = pager.toPageInfo();
        final WorkOrderDTO workOrderDTO = WorkOrderDTO.builder().adminUserId(adminUserId).status(status).build();
        if (filedName != null && filedName.equals("id")) {
            workOrderDTO.setId(Long.parseLong(keyword));
        }
        if (filedName != null && filedName.equals("userId")) {
            workOrderDTO.setUserId(Long.parseLong(keyword));
        }
        if (filedName != null && filedName.equals("userName")) {
            workOrderDTO.setUserName(keyword);
        }

        final List<WorkOrder> source = this.workOrderAdminService.getListByPage(pageInfo, workOrderDTO);
        final ModelMapper mapper = new ModelMapper();
        final List<WorkOrderDTO> list = mapper.map(source, new TypeToken<List<WorkOrderDTO>>() {
        }.getType());
        list.forEach(wd -> wd.setMenuDesc(this.workOrderMenuAdminService.getMenuDesc(wd.getMenuId())));

        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), list));
    }

    @GetMapping(value = "/status")
    public ResponseResult getStatus() {
        final JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < WorkOrderStatusEnum.values().length; i++) {
            final JSONObject jsonObject = new JSONObject();
            jsonObject.put("code", i);
            jsonObject.put("codeMsg", WorkOrderStatusEnum.setOpEnum(i));
            jsonObject.put("name", WorkOrderStatusEnum.setOpEnum(i).getMsg());
            jsonArray.add(jsonObject);
        }
        return ResultUtils.success(jsonArray);
    }

    @PostMapping(value = "/wait")
    public ResponseResult setWait(@RequestParam(value = "id") final Long id) {
        final WorkOrder workOrder = this.workOrderAdminService.getById(id);
        if (workOrder == null) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.WRONG_OPERATION);
        }
        //已受理 处理中工单可以置为等待
        if (workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_ALLOT.getCode() ||
                workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_DEALING.getCode()) {
            workOrder.setStatus(WorkOrderStatusEnum.WORK_ORDER_PENDING.getCode());
        } else {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.WRONG_OPERATION);
        }
        return ResultUtils.success(this.workOrderAdminService.editById(workOrder));
    }

    @PostMapping(value = "/complete")
    public ResponseResult setComplete(@RequestParam(value = "id") final Long id) {
        final WorkOrder workOrder = this.workOrderAdminService.getById(id);
        if (workOrder == null) {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.WRONG_OPERATION);
        }
        //状态为处理中的可以设置为待确认，等待用户确认,设置最后回复时间
        if (workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_DEALING.getCode()) {
            workOrder.setStatus(WorkOrderStatusEnum.WORK_ORDER_UN_CONFIRMED.getCode());
            workOrder.setSolveTime(new Date());
            workOrder.setLastReplyTime(new Date());
            //首次回复-受理时间
            workOrder.setHandleTime(workOrder.getResponseTime().getTime() - workOrder.getAcceptTime().getTime());
            //解决时间-受理时间
            workOrder.setDisposeTime(workOrder.getSolveTime().getTime() - workOrder.getAcceptTime().getTime());
            return ResultUtils.success(this.workOrderAdminService.editById(workOrder));
        } else {
            return ResultUtils.failure(ExtraBizErrorCodeEnum.WRONG_OPERATION);
        }
    }

    @GetMapping(value = "/data")
    ResponseResult evaluation(@RequestParam(value = "adminUserId") final Integer adminUserId) {
        final WorkOrderDTO workOrderDTO = WorkOrderDTO.builder().adminUserId(adminUserId).build();
        final HashMap<String, Double> result = new HashMap<>(6);
        final List<WorkOrder> list = this.workOrderAdminService.listByAdminUser(workOrderDTO);
        //处理总工单数
        result.put("total", (double) list.size());
        final AtomicReference<Double> averageResponseTime = new AtomicReference<>((double) 0);
        final AtomicReference<Double> averageDealingTime = new AtomicReference<>((double) 0);
        final AtomicInteger complete = new AtomicInteger();
        list.forEach(workOrder -> {
            if (workOrder.getStatus() == WorkOrderStatusEnum.WORK_ORDER_COMPLETE.getCode()) {
                averageResponseTime.updateAndGet(v -> v + (workOrder.getAcceptTime().getTime() - workOrder.getCreatedDate().getTime()) / (1000 * 60 * 60));
                averageDealingTime.updateAndGet(v -> v + (workOrder.getUpdatedDate().getTime() - workOrder.getResponseTime().getTime() / (1000 * 60 * 60)));
                complete.getAndIncrement();
            }
        });
        //已处理工单数
        result.put("complete", (double) complete.get());
        //未处理工单数
        result.put("uncomplete", (double) (list.size() - complete.get()));
        //平均响应时间
        result.put("averageResponseTime", Math.floor(averageResponseTime.get() / list.size()));
        //平均处理时间
        result.put("averageDealingTime", Math.floor(averageDealingTime.get() / list.size()));
        final JSONArray jsonArray = new JSONArray();
        for (final Map.Entry m : result.entrySet()) {
            final JSONObject json = new JSONObject();
            json.put("code", m.getKey());
            json.put("name", m.getValue());
            jsonArray.add(json);
        }
        return ResultUtils.success(jsonArray);
    }


}