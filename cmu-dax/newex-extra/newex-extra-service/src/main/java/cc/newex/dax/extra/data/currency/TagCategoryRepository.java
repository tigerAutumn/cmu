package cc.newex.dax.extra.data.currency;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.currency.TagCategoryExample;
import cc.newex.dax.extra.domain.currency.TagCategory;
import org.springframework.stereotype.Repository;

/**
 * 标签分类表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-26 12:07:16
 */
@Repository
public interface TagCategoryRepository extends CrudRepository<TagCategory, TagCategoryExample, Long> {
}