package cc.newex.dax.extra.rest.controller.admin.v1.customer;


import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.common.enums.OpLogTypeEnum;
import cc.newex.dax.extra.domain.customer.WorkOrderOplog;
import cc.newex.dax.extra.dto.customer.WorkOrderOpLogDTO;
import cc.newex.dax.extra.service.customer.WorkOrderOplogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单操作记录表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@RestController
@RequestMapping(value = "/admin/v1/extra/customer/work-orders/oplogs")
public class WorkOrderOplogAdminController {

    @Autowired
    private WorkOrderOplogService workOrderOplogService;


    @PostMapping(value = "")
    public ResponseResult add(@RequestBody WorkOrderOplog po) {
        this.workOrderOplogService.add(po);
        return ResultUtils.success(po);
    }

    @GetMapping(value = "")
    public ResponseResult get(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<WorkOrderOplog> list = this.workOrderOplogService.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }


    @GetMapping(value = "/{id}")
    public ResponseResult get(Long id) {
        return ResultUtils.success(this.workOrderOplogService.getById(id));
    }


    @PutMapping(value = "")
    public ResponseResult edit(@RequestBody WorkOrderOplog po) {
        return ResultUtils.success(this.workOrderOplogService.editById(po));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(Long id) {
        return ResultUtils.success(this.workOrderOplogService.removeById(id));
    }

    @PostMapping(value = "/log")
    public ResponseResult log(@RequestParam(value = "opType") Integer opType, @RequestBody WorkOrderOpLogDTO po) {
        ModelMapper mapper = new ModelMapper();
        WorkOrderOplog workOrderOplog = mapper.map(po, WorkOrderOplog.class);
        workOrderOplog.setContent(OpLogTypeEnum.setOpEnum(opType).getMsg() + ":" + po.getContent());
        this.workOrderOplogService.add(workOrderOplog);
        return ResultUtils.success(po);
    }
}