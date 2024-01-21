package cc.newex.dax.asset.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.asset.criteria.AccountExample;
import cc.newex.dax.asset.domain.Account;
import cc.newex.dax.asset.dto.BizCurrencyBalance;
import cc.newex.wallet.currency.CurrencyEnum;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 用户账户表 服务接口
 *
 * @author newex-team
 * @date 2018-07-12
 */
public interface AccountService
        extends CrudService<Account, AccountExample, Long> {

    void accountAdd(Long userId, Integer currencyId, BigDecimal amount, String tradeNo, Integer brokerId);

    List<BizCurrencyBalance> getLockBalanceSum();

    Map<CurrencyEnum, BizCurrencyBalance> getLockBalanceSumMap();
}
