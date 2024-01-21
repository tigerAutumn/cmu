package cc.newex.dax.boss.admin.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.admin.criteria.RoleExample;
import cc.newex.dax.boss.admin.domain.Role;
import org.springframework.stereotype.Repository;

/**
 * 后台系统角色表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface RoleRepository
        extends CrudRepository<Role, RoleExample, Integer> {
}