package cc.newex.dax.boss.admin.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.admin.criteria.GroupExample;
import cc.newex.dax.boss.admin.domain.Group;
import cc.newex.dax.boss.admin.domain.GroupUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台系统企业组织机构表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface GroupRepository
        extends CrudRepository<Group, GroupExample, Integer> {

    List<GroupUser> selectAdminUsers(@Param("id") int id);
}