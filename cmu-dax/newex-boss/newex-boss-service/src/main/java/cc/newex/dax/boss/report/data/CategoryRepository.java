package cc.newex.dax.boss.report.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.boss.report.criteria.CategoryExample;
import cc.newex.dax.boss.report.domain.Category;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 报表类别表 数据访问类
 *
 * @author newex-team
 * @date 2017-03-18
 */
@Repository
public interface CategoryRepository
        extends CrudRepository<Category, CategoryExample, Integer> {
    /**
     * @param oldPath
     * @param newPath
     * @return
     */
    int updatePath(@Param("oldPath") String oldPath, @Param("newPath") String newPath);
}