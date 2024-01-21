package cc.newex.dax.extra.rest.controller.admin.v1.customer;


import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.customer.WorkOrderTemplate;
import cc.newex.dax.extra.dto.customer.WorkOrderTemplateDTO;
import cc.newex.dax.extra.service.admin.customer.WorkOrderTemplateAdminService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单问题模版表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@RestController
@RequestMapping(value = "/admin/v1/extra/customer/work-order/templates")
public class WorkOrderTemplateAdminController {

    @Autowired
    private WorkOrderTemplateAdminService workOrderTemplateAdminService;


    @PostMapping(value = "")
    public ResponseResult add(@RequestBody WorkOrderTemplateDTO workOrderTemplateDTO) {
        ModelMapper mapper = new ModelMapper();
        WorkOrderTemplate po = mapper.map(workOrderTemplateDTO, WorkOrderTemplate.class);
        this.workOrderTemplateAdminService.add(po);
        return ResultUtils.success(po);
    }


    @GetMapping(value = "")
    public ResponseResult get(DataGridPager pager, String fieldName, String keyword) {
        PageInfo pageInfo = pager.toPageInfo();
        List<WorkOrderTemplate> list = this.workOrderTemplateAdminService.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @GetMapping(value = "/{id}")
    public ResponseResult get(@PathVariable(value = "id") Integer id) {
        return ResultUtils.success(this.workOrderTemplateAdminService.getById(id));
    }


    @PutMapping(value = "/edit")
    public ResponseResult edit(@RequestBody WorkOrderTemplateDTO workOrderTemplateDTO) {
        ModelMapper mapper = new ModelMapper();
        WorkOrderTemplate po = mapper.map(workOrderTemplateDTO, WorkOrderTemplate.class);
        return ResultUtils.success(this.workOrderTemplateAdminService.editById(po));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(@PathVariable("id") Integer id) {
        return ResultUtils.success(this.workOrderTemplateAdminService.removeById(id));
    }

    @PostMapping(value = "/list")
    public ResponseResult listTemplate(@RequestBody WorkOrderTemplateDTO workOrderTemplateDTO,
                                       @RequestParam(required = false, defaultValue = "1") Integer pageIndex,
                                       @RequestParam(required = false, defaultValue = "50") Integer pageSize) {
        DataGridPager pager = new DataGridPager();
        pager.setPage(pageIndex);
        pager.setRows(pageSize);
        PageInfo pageInfo = pager.toPageInfo();
        ModelMapper mapper = new ModelMapper();
        List<WorkOrderTemplate> list = this.workOrderTemplateAdminService.list(pageInfo, workOrderTemplateDTO);
        List<WorkOrderTemplateDTO> workOrderTemplateDTOS = mapper.map(
                list, new TypeToken<List<WorkOrderTemplateDTO>>() {
                }.getType()
        );
        Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", workOrderTemplateDTOS);
        return ResultUtils.success(modelMap);
    }
}
