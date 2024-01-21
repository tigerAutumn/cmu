package cc.newex.dax.extra.rest.controller.outer.v1.common.customer;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.dto.customer.WorkOrderTemplateDTO;
import cc.newex.dax.extra.service.customer.WorkOrderTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 工单问题模版表 控制器类
 *
 * @author newex-team
 * @date 2018-06-08
 */
@RestController
@RequestMapping(value = "/v1/extra/customer/work-order-templates")
public class WorkOrderTemplateController {

    @Autowired
    private WorkOrderTemplateService workOrderTemplateService;

    @GetMapping("")
    public ResponseResult getWorkOrderTemplates(DataGridPager pager, WorkOrderTemplateDTO workOrderTemplateDTO){

        return ResultUtils.success();
    }


}