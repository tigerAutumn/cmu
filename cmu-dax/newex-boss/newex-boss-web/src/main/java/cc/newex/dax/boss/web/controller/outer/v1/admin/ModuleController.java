package cc.newex.dax.boss.web.controller.outer.v1.admin;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.criteria.ModuleExample;
import cc.newex.dax.boss.admin.domain.Module;
import cc.newex.dax.boss.admin.service.ModuleService;
import cc.newex.dax.boss.web.controller.common.BaseController;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@RestController
@RequestMapping(value = "/v1/boss/admin/modules")
public class ModuleController
        extends BaseController<ModuleService, Module, ModuleExample, Integer> {

    @GetMapping(value = "/getModuleTree")
    @OpLog(name = "获取系统模块树型列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_MODULE_VIEW"})
    public ResponseResult getModuleTree() {
        final List<Module> modules = this.service.getAll();
        final List<TreeNode<Module>> roots = this.service.getModuleTree(modules, x -> x.getStatus() < 2);
        return ResultUtils.success(roots);
    }

    @GetMapping(value = "/listByPage")
    @OpLog(name = "获取系统模块树型列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_MODULE_VIEW"})
    public ResponseResult listByPage(final DataGridPager pager, final Integer id) {
        final int pid = (id == null ? 0 : id);
        final PageInfo pageInfo = pager.toPageInfo();
        final List<Module> list = this.service.getByPage(pageInfo, pid);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "新增系统模块")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_MODULE_ADD"})
    public ResponseResult add(final Module po) {
        po.setHasChild(0);
        po.setPath("");
        po.setCreatedDate(new Date());
        po.setUpdatedDate(new Date());
        this.service.add(po);
        return ResultUtils.success();
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "编辑指定ID的系统模块")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_MODULE_EDIT"})
    public ResponseResult edit(final Module module) {
        this.service.editById(module);

        final String path = this.service.getPath(module.getParentId(), module.getId());
        this.service.updatePath(module.getId(), path);

        return ResultUtils.success();
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "邮件指定ID的系统模块")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_MODULE_REMOVE"})
    public ResponseResult remove(final Integer id, final Integer pid) {
        this.service.remove(id, pid);
        return ResultUtils.success();
    }

    @GetMapping(value = "/getModule")
    @OpLog(name = "获取指定ID系统模块信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_MODULE_VIEW"})
    public ResponseResult getModule(final Integer id) {
        return ResultUtils.success(this.service.getById(id));
    }

    @GetMapping(value = "/getChildModules")
    @OpLog(name = "获取子模块树型列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_MODULE_VIEW"})
    public ResponseResult getChildModules(final Integer id) {
        final int parentId = (id == null ? 0 : id);
        final List<Module> modules = this.service.getChildren(parentId);
        final List<TreeNode<Module>> treeNodes = new ArrayList<>(modules.size());
        for (final Module po : modules) {
            final String mid = Integer.toString(po.getId());
            final String pid = Integer.toString(po.getParentId());
            final String text = po.getName();
            final TreeNode<Module> node = new TreeNode<>(mid, pid, text, "closed", po.getIcon(), false, po);
            treeNodes.add(node);
        }
        return ResultUtils.success(treeNodes);
    }

    @PostMapping(value = "/move")
    @OpLog(name = "移动模块树型关系")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_MODULE_VIEW"})
    public ResponseResult move(final Integer sourceId, final Integer targetId, final Integer sourcePid,
                               final String sourcePath) {
        this.service.move(sourceId, targetId, sourcePid, sourcePath);
        return ResultUtils.success(new HashMap<String, Integer>(3) {
            {
                this.put("sourceId", sourceId);
                this.put("targetId", targetId);
                this.put("sourcePid", sourcePid);
            }
        });
    }

    @GetMapping(value = "/rebuildPath")
    @OpLog(name = "重新模块树路径")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_MODULE_VIEW"})
    public ResponseResult rebuildPath() {
        this.service.rebuildAllPath();
        return ResultUtils.success();
    }
}
