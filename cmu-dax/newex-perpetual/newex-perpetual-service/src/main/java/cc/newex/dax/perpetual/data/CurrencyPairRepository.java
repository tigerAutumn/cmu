package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.CurrencyPairExample;
import cc.newex.dax.perpetual.domain.CurrencyPair;
import org.springframework.stereotype.Repository;

/**
 * 合约币对 数据访问类
 *
 * @author newex-team
 * @date 2018-11-22 19:22:25
 */
@Repository
public interface CurrencyPairRepository extends CrudRepository<CurrencyPair, CurrencyPairExample, Integer> {
}