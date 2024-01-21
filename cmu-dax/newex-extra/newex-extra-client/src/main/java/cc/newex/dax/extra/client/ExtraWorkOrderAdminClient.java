package cc.newex.dax.extra.client;

import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.extra.dto.customer.WorkOrderMenuDTO;
import cc.newex.dax.extra.dto.customer.WorkOrderTemplateDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author newex-team
 */
@FeignClient(value = "${newex.feignClient.dax.extra}", path = "/admin/v1/extra/customer/work-order")
public interface ExtraWorkOrderAdminClient {
    //template begin

    /**
     * 按条件查询模版
     * @param workOrderTemplateDTO
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @PostMapping("/templates/list")
    ResponseResult listTemplate(@RequestBody final WorkOrderTemplateDTO workOrderTemplateDTO,
                           @RequestParam("pageIndex") final Integer pageIndex,
                           @RequestParam("pageSize") final Integer pageSize);

    /**
     * 添加模版列表
     * @param workOrderTemplateDTO
     * @return
     */
    @PostMapping("/templates")
    ResponseResult add(@RequestBody final WorkOrderTemplateDTO workOrderTemplateDTO);

    /**
     * 更新模版
     * @param workOrderTemplateDTO
     * @return
     */
    @PutMapping("/templates/edit")
    ResponseResult edit(@RequestBody final WorkOrderTemplateDTO workOrderTemplateDTO);

    /**
     * 删除模版
     * @param id
     * @return
     */
    @DeleteMapping("/templates/{id}")
    ResponseResult removeTemplates(@PathVariable(value = "id") final Integer id);

    //menu begin
    /**
     * 添加工单菜单
     * @param workOrderMenuDTO
     * @return
     */
    @PostMapping("/menus")
    ResponseResult add(@RequestBody final WorkOrderMenuDTO workOrderMenuDTO);

    /**
     * 修改菜单
     * @param workOrderMenuDTO
     * @return
     */
    @PutMapping("/menus/edit")
    ResponseResult edit(@RequestBody final  WorkOrderMenuDTO workOrderMenuDTO);

    /**
     * 删除工单菜单
     * @param id
     * @return
     */
    @DeleteMapping("/menus/{id}")
    ResponseResult removeMenus(@PathVariable(value = "id")final Integer id);

    /**
     * 添加菜单模版
     */
    @PostMapping("/menus/list")
    ResponseResult listMenu(@RequestBody final DataGridPager<WorkOrderMenuDTO> pager);

    /**
     * 工单菜单的树形结构
     * @return
     */
    @GetMapping("/menus/tree")
    ResponseResult menuTree();

}
