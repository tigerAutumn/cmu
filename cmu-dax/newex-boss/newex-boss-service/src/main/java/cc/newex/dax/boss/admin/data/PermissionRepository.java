package cc.newex.dax.boss.admin.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.admin.criteria.PermissionExample;
import cc.newex.dax.boss.admin.domain.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台系统权限表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface PermissionRepository
        extends CrudRepository<Permission, PermissionExample, Integer> {
    List<Permission> selectAllWithModulePath();
}