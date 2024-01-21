package cc.newex.dax.boss.config.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.config.criteria.GlobalDictExample;
import cc.newex.dax.boss.config.domain.GlobalDict;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 后台系统全局配置字典表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface GlobalDictRepository
        extends CrudRepository<GlobalDict, GlobalDictExample, Integer> {

    /**
     * @param parentId
     * @return
     */
    List<GlobalDict> selectByParentId(Integer parentId);

    /**
     * @param code
     * @return
     */
    List<GlobalDict> selectByParentKey(@Param(value = "code") String code);
}