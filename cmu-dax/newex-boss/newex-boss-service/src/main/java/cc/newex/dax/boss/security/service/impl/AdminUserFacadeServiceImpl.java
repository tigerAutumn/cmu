package cc.newex.dax.boss.security.service.impl;

import cc.newex.commons.lang.tree.TreeNode;
import cc.newex.dax.boss.admin.domain.Module;
import cc.newex.dax.boss.admin.domain.User;
import cc.newex.dax.boss.admin.service.ModuleService;
import cc.newex.dax.boss.admin.service.PermissionService;
import cc.newex.dax.boss.admin.service.RoleService;
import cc.newex.dax.boss.admin.service.UserService;
import cc.newex.dax.boss.security.service.AdminUserFacadeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Predicate;

/**
 * 用户权限服务外观类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Slf4j
@Service
public class AdminUserFacadeServiceImpl implements AdminUserFacadeService {
    @Resource
    private UserService userService;
    @Resource
    private RoleService roleService;
    @Resource
    private ModuleService moduleService;
    @Resource
    private PermissionService permissionService;

    public AdminUserFacadeServiceImpl() {
    }

    @Override
    public void loadCache() {
        this.permissionService.reloadCache();
    }

    @Override
    public List<TreeNode<Module>> getModuleTree(final List<Module> modules, final Predicate<Module> predicate) {
        return this.moduleService.getModuleTree(modules, predicate);
    }

    @Override
    public List<Module> getModules(final String roleIds) {
        if (this.isAdministrator(roleIds)) {
            return this.moduleService.getAll();
        }
        final String moduleIds = this.roleService.getModuleIds(roleIds);
        return this.moduleService.getModules(moduleIds);
    }

    @Override
    public User getUserNonSensitiveInfo(final String account) {
        return this.userService.getUserWithoutSensitiveInfo(account);
    }

    @Override
    public User getUser(final String account) {
        return this.userService.getUserByAccount(account);
    }

    @Override
    public String getRoleNames(final String roleIds) {
        return this.roleService.getNames(roleIds);
    }

    @Override
    public Set<String> getRoleSet(final String roleIds) {
        final String[] roleIdSplit = StringUtils.split(roleIds, ',');
        if (roleIdSplit == null || roleIdSplit.length == 0) {
            return Collections.emptySet();
        }

        final List<String> codes = this.roleService.getCodes(roleIds);
        final Set<String> roleSet = new HashSet<>(codes.size());
        for (final String code : codes) {
            if (!roleSet.contains(code.trim())) {
                roleSet.add(code);
            }
        }
        return roleSet;
    }

    @Override
    public Set<String> getPermissionSet(final String roleIds) {
        final Set<String> permSet = new HashSet<>(128);

        //先把角色加入到权限列表
        final Set<String> roleSet = this.getRoleSet(roleIds);
        if (CollectionUtils.isNotEmpty(roleSet)) {
            permSet.addAll(roleSet);
        }

        //加入权限列表
        final String permissionIds = this.roleService.getPermissionIds(roleIds);
        if (StringUtils.isBlank(permissionIds)) {
            return permSet;
        }

        final Map<String, String> permissionMap = this.permissionService.getIdCodeMap();
        final String[] permissionIdSplit = StringUtils.split(permissionIds, ',');
        for (final String permId : permissionIdSplit) {
            final String perm = permissionMap.get(StringUtils.trim(permId));
            if (StringUtils.isNotBlank(perm)) {
                permSet.add(perm);
            }
        }
        return permSet;
    }

    @Override
    public boolean hasPermission(final String roleIds, final String... codes) {
        if (this.isAdministrator(roleIds)) {
            return true;
        }

        if (StringUtils.isBlank(roleIds) || ArrayUtils.isEmpty(codes)) {
            return false;
        }

        final String permissionIds = this.roleService.getPermissionIds(roleIds);
        if (StringUtils.isBlank(permissionIds)) {
            return false;
        }

        final String[] permissionIdSplit = StringUtils.split(permissionIds, ',');
        final String codePermissionIds = this.permissionService.getPermissionIds(codes);
        final String[] codePermissionIdSplit = StringUtils.split(codePermissionIds, ',');

        return this.hasPermission(codePermissionIdSplit, permissionIdSplit);
    }

    private boolean hasPermission(final String[] codePermissionIdSplit, final String[] permissionIdSplit) {
        if (codePermissionIdSplit == null || permissionIdSplit == null) {
            return false;
        }

        for (final String permId : codePermissionIdSplit) {
            if (!ArrayUtils.contains(permissionIdSplit, permId)) {
                return false;
            }
        }

        return true;
    }

    @Override
    public boolean isAdministrator(final String roleIds) {
        if (StringUtils.isBlank(roleIds)) {
            return false;
        }
        return this.roleService.isSuperAdminRole(roleIds);
    }
}
