package cc.newex.dax.extra.rest.controller.admin.v1.customer;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.lang.util.NumberUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.extra.domain.customer.WorkOrderMenu;
import cc.newex.dax.extra.dto.customer.WorkOrderMenuDTO;
import cc.newex.dax.extra.service.admin.customer.WorkOrderMenuAdminService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工单菜单表 控制器类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@RestController
@RequestMapping(value = "/admin/v1/extra/customer/work-order/menus")
public class WorkOrderMenuAdminController {

    @Autowired
    private WorkOrderMenuAdminService workOrderMenuAdminService;


    @PostMapping(value = "")
    public ResponseResult add(@RequestBody final WorkOrderMenuDTO workOrderMenuDTO) {
        workOrderMenuDTO.setAdminUserId(1);
        final ModelMapper mapper = new ModelMapper();
        final WorkOrderMenu po = mapper.map(workOrderMenuDTO, WorkOrderMenu.class);
        final int count = this.workOrderMenuAdminService.add(po);
        return ResultUtils.success(count);
    }

    @GetMapping(value = "")
    public ResponseResult get(final DataGridPager pager, final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final List<WorkOrderMenu> list = this.workOrderMenuAdminService.getByPage(pageInfo, fieldName, "%" + keyword + "%");
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @GetMapping(value = "/{id}")
    public ResponseResult get(@PathVariable(value = "id") final Integer id) {
        return ResultUtils.success(this.workOrderMenuAdminService.getById(id));
    }


    @PutMapping(value = "/edit")
    public ResponseResult edit(@RequestBody final WorkOrderMenuDTO workOrderMenuDTO) {
        final ModelMapper mapper = new ModelMapper();
        final WorkOrderMenu po = mapper.map(workOrderMenuDTO, WorkOrderMenu.class);
        return ResultUtils.success(this.workOrderMenuAdminService.editById(po));
    }


    @DeleteMapping(value = "/{id}")
    public ResponseResult remove(@PathVariable(value = "id") final Integer id) {
        return ResultUtils.success(this.workOrderMenuAdminService.removeById(id));
    }

    @PostMapping(value = "/list")
    public ResponseResult listMenu(@RequestBody final DataGridPager<WorkOrderMenuDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();
        final ModelMapper mapper = new ModelMapper();
        final List<WorkOrderMenu> list = this.workOrderMenuAdminService.list(pageInfo, pager.getQueryParameter());
        final List<WorkOrderMenuDTO> workOrderMenuDTOS = mapper.map(
                list, new TypeToken<List<WorkOrderMenuDTO>>() {
                }.getType()
        );
        return ResultUtils.success(new DataGridPagerResult<>(pageInfo.getTotals(), workOrderMenuDTOS));
    }

    @GetMapping(value = "/tree")
    public ResponseResult menuTree() {
        final List<WorkOrderMenu> list = this.workOrderMenuAdminService.getAll();
        final ModelMapper mapper = new ModelMapper();
        final List<WorkOrderMenuDTO> workOrderMenuDTOS = mapper.map(
                list, new TypeToken<List<WorkOrderMenuDTO>>() {
                }.getType()
        );
        final List<TreeNode<WorkOrderMenuDTO>> roots = this.workOrderMenuAdminService.getMenusTree(workOrderMenuDTOS, x -> NumberUtil.eq(x.getStatus(), 0));
        return ResultUtils.success(roots);
    }

    @GetMapping(value = "/menuDesc")
    public ResponseResult menuDesc(@RequestParam(value = "id") final Integer menuId) {
        final String desc = this.workOrderMenuAdminService.getMenuDesc(menuId);
        return ResultUtils.success(desc);
    }
}