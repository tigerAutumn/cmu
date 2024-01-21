package cc.newex.dax.asset.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.asset.criteria.AccountExample;
import cc.newex.dax.asset.dao.AccountRepository;
import cc.newex.dax.asset.domain.Account;
import cc.newex.dax.asset.dto.BizCurrencyBalance;
import cc.newex.dax.asset.service.AccountService;
import cc.newex.wallet.currency.BizEnum;
import cc.newex.wallet.currency.CurrencyEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户账户表 服务实现
 *
 * @author newex-team
 * @date 2018-07-12
 */
@Slf4j
@Service
public class AccountServiceImpl
        extends AbstractCrudService<AccountRepository, Account, AccountExample, Long>
        implements AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    protected AccountExample getPageExample(final String fieldName, final String keyword) {
        final AccountExample example = new AccountExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void accountAdd(Long userId, Integer currencyId, BigDecimal amount, String tradeNo, Integer brokerId) {
        log.info("accountAdd userId={}", userId);
//        AccountExample accountExample = new AccountExample();
//        AccountExample.Criteria criteria = accountExample.createCriteria();
//        criteria.andUserIdEqualTo(userId);
//        criteria.andCurrencyEqualTo(currencyId);
//        criteria.andStatusEqualTo(0);

        Account account4Insert = new Account();
        account4Insert.setUserId(userId);
        account4Insert.setCurrency(currencyId);
        account4Insert.setAccLockPosition(amount);
        account4Insert.setBrokerId(brokerId);
        account4Insert.setStatus(0);
        int insert = this.accountRepository.insertOnDuplicateKey(account4Insert);
        Assert.isTrue(insert > 0, "锁仓创建账户失败userId=" + userId + ",tradeNo=" + tradeNo);
    }

    @Override
    public List<BizCurrencyBalance> getLockBalanceSum() {
        List<BizCurrencyBalance> lockPositionSum = this.accountRepository.getLockPositionSum();
        lockPositionSum.parallelStream().forEach(a -> a.setBiz(BizEnum.ASSET.getIndex()));
        return lockPositionSum;
    }


    @Override
    public Map<CurrencyEnum, BizCurrencyBalance> getLockBalanceSumMap() {
        List<BizCurrencyBalance> balances = this.getLockBalanceSum();
        return balances.stream().collect(Collectors.toMap(balance -> CurrencyEnum.parseValue(balance.getCurrency()),
                BizCurrencyBalance::self));
    }


}