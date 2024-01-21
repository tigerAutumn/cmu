package cc.newex.wallet.dao;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.wallet.criteria.CurrencyBalanceExample;
import cc.newex.wallet.pojo.CurrencyBalance;
import org.springframework.stereotype.Repository;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018-04-26
 */
@Repository
public interface CurrencyBalanceRepository
        extends CrudRepository<CurrencyBalance, CurrencyBalanceExample, Integer> {
}