package cc.newex.wallet.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.wallet.criteria.CurrencyBalanceExample;
import cc.newex.wallet.pojo.CurrencyBalance;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-04-26
 */
public interface CurrencyBalanceService
        extends CrudService<CurrencyBalance, CurrencyBalanceExample, Integer> {
}
