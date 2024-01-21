package cc.newex.dax.boss.web.controller.outer.v1.admin;

import cc.newex.commons.lang.pair.IdNamePair;
import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.support.annotation.CurrentUser;
import cc.newex.commons.support.annotation.OpLog;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.boss.admin.criteria.RoleExample;
import cc.newex.dax.boss.admin.domain.Module;
import cc.newex.dax.boss.admin.domain.Permission;
import cc.newex.dax.boss.admin.domain.Role;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.ModuleService;
import cc.newex.dax.boss.admin.service.PermissionService;
import cc.newex.dax.boss.admin.service.RoleService;
import cc.newex.dax.boss.web.controller.common.BaseController;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/boss/admin/roles")
public class RoleController
        extends BaseController<RoleService, Role, RoleExample, Integer> {

    @Resource
    private ModuleService moduleService;
    @Resource
    private PermissionService permissionService;

    @GetMapping(value = "/isSuperAdmin")
    @OpLog(name = "是否为超级管理角色")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_ROLE_VIEW"})
    public ResponseResult isSuperAdmin(@CurrentUser final User loginUser) {
        return ResultUtils.success(this.service.isSuperAdminRole(loginUser.getRoles()));
    }

    @GetMapping(value = "/listByPage")
    @OpLog(name = "分页获取角色列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_ROLE_VIEW"})
    public ResponseResult listByPage(@CurrentUser final User loginUser, final DataGridPager pager,
                                     final String fieldName, final String keyword) {
        final PageInfo pageInfo = pager.toPageInfo();
        final RoleExample example = new RoleExample();
        final RoleExample.Criteria criteria = example.createCriteria();
        if (loginUser.getBrokerId() != 0) {
            criteria.andBrokerIdEqualTo(loginUser.getBrokerId());
        }
        if (StringUtils.isNotEmpty(fieldName) && Objects.nonNull(keyword)) {
            criteria.andFieldLike(fieldName, "%" + keyword + "%");
        }
        final List<Role> list = this.service.getByPage(pageInfo, example);
        final Map<String, Object> modelMap = new HashMap<>(2);
        modelMap.put("total", pageInfo.getTotals());
        modelMap.put("rows", list);
        return ResultUtils.success(modelMap);
    }

    @PostMapping(value = "/add")
    @OpLog(name = "增加角色")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_ROLE_ADD"})
    public ResponseResult add(@CurrentUser final User loginUser, final Role po) {
        po.setModules("");
        po.setPermissions("");
        po.setAdminUserId(loginUser.getId());
        po.setAdminUsername(loginUser.getAccount());
        po.setCreatedDate(new Date());
        po.setUpdatedDate(new Date());
        po.setBrokerId(loginUser.getLoginBrokerId());

        this.service.add(po);
        return ResultUtils.success();
    }

    @PostMapping(value = "/edit")
    @OpLog(name = "修改角色")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_ROLE_EDIT"})
    public ResponseResult edit(final Role po) {
        this.service.editById(po);
        return ResultUtils.success();
    }

    @PostMapping(value = "/remove")
    @OpLog(name = "删除角色")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_ROLE_REMOVE"})
    public ResponseResult remove(final Integer id) {
        this.service.removeById(id);
        return ResultUtils.success();
    }

    @GetMapping(value = "/getRoleList")
    @OpLog(name = "获取当前的角色列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_ROLE_VIEW"})
    public ResponseResult getRoleList(@CurrentUser final User loginUser) {
        final List<IdNamePair> list = new ArrayList<>(10);
        final List<Role> roleList = this.service.getRolesList(loginUser);
        if (CollectionUtils.isEmpty(roleList)) {
            return ResultUtils.success(list);
        }
        list.addAll(roleList.stream()
                .map(role -> new IdNamePair(String.valueOf(role.getId()), role.getName()))
                .collect(Collectors.toList()));
        return ResultUtils.success(list);
    }

    @PostMapping(value = "/authorize")
    @OpLog(name = "给角色授权")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_ROLE_AUTHORIZE"})
    public ResponseResult authorize(final Role role) {
        final String permissions = StringUtils.stripEnd(role.getPermissions(), ",");
        final String modules = this.permissionService.getModuleIds(permissions);

        role.setPermissions(permissions);
        role.setModules(modules);

        log.info("给角色授权: {}", role);

        this.service.editById(role);

        return ResultUtils.success();
    }

    @GetMapping(value = "/getRoleById")
    @OpLog(name = "获取指定id的角色信息")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_ROLE_VIEW"})
    public ResponseResult getRoleById(final Integer id) {
        return ResultUtils.success(this.service.getById(id));
    }

    @GetMapping(value = "/listPermissionTree")
    @OpLog(name = "获取当前用户所拥有的权限列表")
    @Secured({"ROLE_SUPER_ADMIN", "ROLE_ADMIN_ROLE_VIEW"})
    public ResponseResult listPermissionTree(@CurrentUser final User loginUser,
                                             final Integer roleId) {
        final Map<String, String[]> roleModuleAndPermissionMap
                = this.service.getRoleModulesAndPermissions(roleId);
        if (roleModuleAndPermissionMap == null) {
            return ResultUtils.success(new ArrayList<>(0));
        }
        return ResultUtils.success(this.buildTree(this.getModulePermissions(
                loginUser.getRoles(),
                roleModuleAndPermissionMap.get("modules"),
                roleModuleAndPermissionMap.get("permissions")))
        );
    }

    private List<TreeNode<String>> getModulePermissions(final String userRoles,
                                                        final String[] moduleSplit,
                                                        final String[] operationSplit) {
        final boolean isSuperAdminRole = this.service.isSuperAdminRole(userRoles);
        final List<Module> modules = isSuperAdminRole
                ? this.moduleService.getAll()
                : this.moduleService.getModules(this.service.getModuleIds(userRoles));

        final List<TreeNode<String>> moduleNodes = new ArrayList<>(modules.size());
        moduleNodes.addAll(modules.stream()
                .map(module -> new TreeNode<>(
                        String.valueOf(-module.getId()),
                        String.valueOf(-module.getParentId()),
                        module.getName(), "open", "", false,
                        String.valueOf(module.getId())
                )).collect(Collectors.toList()));

        final List<Permission> permissions = this.permissionService.getAll();
        final List<TreeNode<String>> permNodes = new ArrayList<>(permissions.size());
        permNodes.addAll(permissions.stream()
                .map(perm -> new TreeNode<>(
                        String.valueOf(perm.getId()),
                        String.valueOf(-perm.getModuleId()),
                        perm.getName(), "open", "",
                        ArrayUtils.contains(operationSplit, perm.getId().toString()),
                        String.valueOf(perm.getId())
                )).collect(Collectors.toList()));
        moduleNodes.addAll(permNodes);
        return moduleNodes;
    }

    private List<TreeNode<String>> buildTree(final Collection<TreeNode<String>> nodes) {
        if (CollectionUtils.isEmpty(nodes)) {
            return new ArrayList<>(0);
        }

        final List<TreeNode<String>> rootNodes = new ArrayList<>(20);
        rootNodes.addAll(nodes.stream()
                .filter(treeNode -> "0".equals(treeNode.getPId()))
                .collect(Collectors.toList()));
        for (final TreeNode<String> rootNode : rootNodes) {
            this.getChildNodes(nodes, rootNode);
        }
        return rootNodes;
    }

    private void getChildNodes(final Collection<TreeNode<String>> nodes, final TreeNode<String> node) {
        final List<TreeNode<String>> childNodes = new ArrayList<>(nodes.size());
        childNodes.addAll(nodes.stream()
                .filter(treeNode -> treeNode.getPId().equals(node.getId()))
                .collect(Collectors.toList()));
        for (final TreeNode<String> childNode : childNodes) {
            node.setState("closed");
            node.getChildren().add(childNode);
            this.getChildNodes(nodes, childNode);
        }
    }

}
