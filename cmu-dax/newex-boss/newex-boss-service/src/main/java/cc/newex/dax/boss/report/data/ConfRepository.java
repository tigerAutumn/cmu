package cc.newex.dax.boss.report.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.report.criteria.ConfExample;
import cc.newex.dax.boss.report.domain.Conf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 报表元数据配置字典表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface ConfRepository
        extends CrudRepository<Conf, ConfExample, Integer> {
    /**
     * @param parentId
     * @return
     */
    List<Conf> selectByParentId(Integer parentId);

    /**
     * @param code
     * @return
     */
    List<Conf> selectByParentKey(@Param(value = "code") String code);
}