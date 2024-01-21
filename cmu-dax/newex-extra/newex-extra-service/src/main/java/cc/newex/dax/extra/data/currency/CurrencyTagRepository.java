package cc.newex.dax.extra.data.currency;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.currency.CurrencyTagExample;
import cc.newex.dax.extra.domain.currency.CurrencyTag;
import org.springframework.stereotype.Repository;

/**
 * 币种标签表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-26 12:07:41
 */
@Repository
public interface CurrencyTagRepository extends CrudRepository<CurrencyTag, CurrencyTagExample, Long> {
}