package cc.newex.dax.boss.admin.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.admin.criteria.RoleExample;
import cc.newex.dax.boss.admin.domain.Role;
import cc.newex.dax.boss.admin.domain.User;

import java.util.List;
import java.util.Map;

/**
 * 后台系统角色表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface RoleService
        extends CrudService<Role, RoleExample, Integer> {
    /**
     * @param roleIds
     * @return
     */
    boolean isSuperAdminRole(String roleIds);

    /**
     * @param roleIds
     * @return
     */
    String getNames(String roleIds);

    /**
     * @param roleIds
     * @return
     */
    List<String> getCodes(final String roleIds);

    /**
     * @param roleIds
     * @return
     */
    String getModuleIds(String roleIds);

    /**
     * @param roleIds
     * @return
     */
    String getPermissionIds(String roleIds);

    /**
     * @param page
     * @param currentUser
     * @param fieldName
     * @param keyword
     * @return
     */
    List<Role> getByPage(PageInfo page, User currentUser, String fieldName, String keyword);

    /**
     * @param createUser
     * @return
     */
    String getRoleIdsBy(String createUser);

    /**
     * @param currentUser
     * @return
     */
    List<Role> getRolesList(User currentUser);

    /**
     * @param roleId
     * @return
     */
    Map<String, String[]> getRoleModulesAndPermissions(Integer roleId);
}
