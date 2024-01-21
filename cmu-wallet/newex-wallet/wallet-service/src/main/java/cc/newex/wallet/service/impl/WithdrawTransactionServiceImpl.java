package cc.newex.wallet.service.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.wallet.criteria.WithdrawTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dao.WithdrawTransactionRepository;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.service.WithdrawTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-04-01
 */
@Slf4j
@Service
public class WithdrawTransactionServiceImpl
        extends AbstractCrudService<WithdrawTransactionRepository, WithdrawTransaction, WithdrawTransactionExample, Integer>
        implements WithdrawTransactionService {

    @Autowired
    private WithdrawTransactionRepository withdrawTransactionRepos;

    @Override
    protected WithdrawTransactionExample getPageExample(final String fieldName, final String keyword) {
        final WithdrawTransactionExample example = new WithdrawTransactionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public WithdrawTransaction getByTxId(String txid, CurrencyEnum currencyEnum) {
        final ShardTable table = ShardTable.builder().prefix(currencyEnum.getName()).build();
        final WithdrawTransactionExample withExam = new WithdrawTransactionExample();
        withExam.createCriteria().andTxIdEqualTo(txid);
        final WithdrawTransaction withdrawTransaction = this.getOneByExample(withExam, table);
        return withdrawTransaction;
    }


}