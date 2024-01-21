package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.market.criteria.CurrencyExample;
import cc.newex.dax.market.domain.Currency;
import org.springframework.stereotype.Repository;

/**
 * 币种表 数据访问类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface CurrencyRepository
    extends CrudRepository<Currency, CurrencyExample, Integer> {
}