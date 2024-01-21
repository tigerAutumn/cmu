package cc.newex.dax.boss.admin.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.admin.criteria.ModuleExample;
import cc.newex.dax.boss.admin.domain.Module;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 后台系统模块表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface ModuleRepository
        extends CrudRepository<Module, ModuleExample, Integer> {
    int updatePath(@Param("oldPath") String oldPath, @Param("newPath") String newPath);
}