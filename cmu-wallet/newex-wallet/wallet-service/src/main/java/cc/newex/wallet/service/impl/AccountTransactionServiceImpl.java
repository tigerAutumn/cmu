package cc.newex.wallet.service.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.wallet.criteria.AccountTransactionExample;
import cc.newex.wallet.dao.AccountTransactionRepository;
import cc.newex.wallet.pojo.AccountTransaction;
import cc.newex.wallet.service.AccountTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-04-12
 */
@Slf4j
@Service
public class AccountTransactionServiceImpl
        extends AbstractCrudService<AccountTransactionRepository, AccountTransaction, AccountTransactionExample, Long>
        implements AccountTransactionService {

    @Autowired
    private AccountTransactionRepository accountTransactionRepos;

    @Override
    protected AccountTransactionExample getPageExample(final String fieldName, final String keyword) {
        final AccountTransactionExample example = new AccountTransactionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public BigDecimal getTotalBalance(final AccountTransactionExample example, final ShardTable table) {
        final BigDecimal totalBalance = this.accountTransactionRepos.getTotalBalance(example, table);
        return totalBalance;
    }

    @Override
    public int addOnDuplicateKey(final AccountTransaction tx, final ShardTable table) {
        final AccountTransactionExample example = new AccountTransactionExample();
        example.createCriteria().andTxIdEqualTo(tx.getTxId());
        final AccountTransaction exist = this.getOneByExample(example, table);
        if (ObjectUtils.isEmpty(exist)) {
            return this.accountTransactionRepos.insertOnDuplicateKey(tx, table);
        } else {
            return 1;
        }
    }
}