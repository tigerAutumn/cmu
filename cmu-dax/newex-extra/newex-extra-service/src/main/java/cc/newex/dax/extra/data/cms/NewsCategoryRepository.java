package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.NewsCategoryExample;
import cc.newex.dax.extra.domain.cms.NewsCategory;
import org.springframework.stereotype.Repository;

/**
 * 新闻或文章类别表 数据访问类
 *
 * @author newex-team
 * @date 2018-05-30
 */
@Repository
public interface NewsCategoryRepository
        extends CrudRepository<NewsCategory, NewsCategoryExample, Integer> {
}