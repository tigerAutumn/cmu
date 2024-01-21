package cc.newex.dax.boss.admin.service;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.boss.admin.criteria.PermissionExample;
import cc.newex.dax.boss.admin.domain.Permission;

import java.util.List;
import java.util.Map;

/**
 * 后台系统权限表 服务接口
 *
 * @author newex-team
 * @date 2017-03-18
 */
public interface PermissionService
        extends CrudService<Permission, PermissionExample, Integer> {
    /**
     *
     */
    void reloadCache();

    /**
     * @param pageInfo
     * @param moduleId
     * @return
     */
    List<Permission> getByPage(PageInfo pageInfo, Integer moduleId);

    /**
     * @param moduleId
     * @return
     */
    List<Permission> getByModuleId(Integer moduleId);

    /**
     * @return
     */
    Map<String, String> getIdCodeMap();

    /**
     * @param codes
     * @return
     */
    String getPermissionIds(String[] codes);

    /**
     * @param permissionIds
     * @return
     */
    String getModuleIds(String permissionIds);
}
