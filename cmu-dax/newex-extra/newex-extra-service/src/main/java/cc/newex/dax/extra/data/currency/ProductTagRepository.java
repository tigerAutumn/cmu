package cc.newex.dax.extra.data.currency;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.currency.ProductTagExample;
import cc.newex.dax.extra.domain.currency.ProductTag;
import org.springframework.stereotype.Repository;

/**
 * 币对标签表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-26 12:07:36
 */
@Repository
public interface ProductTagRepository extends CrudRepository<ProductTag, ProductTagExample, Long> {
}