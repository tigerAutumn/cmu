package cc.newex.dax.boss.web.controller.outer.v1.admin;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.criteria.PermissionExample;
import cc.newex.dax.boss.admin.domain.Permission;
import cc.newex.dax.boss.admin.service.PermissionService;
import cc.newex.dax.boss.web.controller.common.BaseController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@RestController
@RequestMapping(value = "/v1/boss/admin/permissions")
public class PermissionController
        extends BaseController<PermissionService, Permission, PermissionExample, Integer> {

    @GetMapping(value = "/listByPage")
    @OpLog(name = "获取权限列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_PERMISSION_VIEW"})
    public ResponseResult listByPage(final DataGridPager pager, final Integer id) {
        final int moduleId = (id == null ? 0 : id);
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Permission> list = this.service.getByPage(pageInfo, moduleId);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", list.size());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "增加权限")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_PERMISSION_ADD"})
    public ResponseResult add(final Permission po) {
        po.setCreatedDate(new Date());
        po.setUpdatedDate(new Date());
        this.service.add(po);
        this.service.reloadCache();
        return ResultUtils.success();
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改权限")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_PERMISSION_EDIT"})
    public ResponseResult edit(final Permission po) {
        this.service.editById(po);
        this.service.reloadCache();
        return ResultUtils.success();
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除权限")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_PERMISSION_REMOVE"})
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        this.service.reloadCache();
        return ResultUtils.success();
    }
}
