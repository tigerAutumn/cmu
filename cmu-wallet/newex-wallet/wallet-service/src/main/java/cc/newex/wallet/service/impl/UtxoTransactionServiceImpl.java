package cc.newex.wallet.service.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.wallet.criteria.UtxoTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dao.UtxoTransactionRepository;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.service.UtxoTransactionService;
import cc.newex.wallet.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;
import java.util.List;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-03-31
 */
@Slf4j
@Service
public class UtxoTransactionServiceImpl
        extends AbstractCrudService<UtxoTransactionRepository, UtxoTransaction, UtxoTransactionExample, Long>
        implements UtxoTransactionService {

    @Autowired
    private UtxoTransactionRepository utxoTransactionRepos;

    @Override
    protected UtxoTransactionExample getPageExample(final String fieldName, final String keyword) {
        final UtxoTransactionExample example = new UtxoTransactionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }


    @Override
    public BigDecimal getTotalBalance(final UtxoTransactionExample example, final ShardTable table) {
        final BigDecimal totalBalance = this.utxoTransactionRepos.getTotalBalance(example, table);
        return totalBalance;
    }

    @Override
    public UtxoTransaction getByTxid(String txId, CurrencyEnum currencyEnum) {
        ShardTable table = ShardTable.builder().prefix(currencyEnum.getName()).build();
        final UtxoTransactionExample example = new UtxoTransactionExample();
        example.createCriteria().andTxIdEqualTo(txId);
        final UtxoTransaction utxoTransaction = this.getOneByExample(example, table);
        return utxoTransaction;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public UtxoTransaction markAsSpent(UtxoTransactionExample example, CurrencyEnum currencyEnum) {
        ShardTable table = ShardTable.builder().prefix(currencyEnum.getName()).build();

        final UtxoTransaction utxoTransaction = this.getOneByExample(example, table);
        utxoTransaction.setStatus((byte) Constants.SIGNING);
        this.editById(utxoTransaction, table);
        return utxoTransaction;
    }


    @Override
    public int batchAddOnDuplicateKey(final List<UtxoTransaction> records, final ShardTable shardTable) {
        records.parallelStream().forEach((record) -> {
            this.addOnDuplicateKey(record, shardTable);
        });
        return records.size();
    }

    @Override
    public int addOnDuplicateKey(final UtxoTransaction tx, final ShardTable table) {
        final UtxoTransactionExample example = new UtxoTransactionExample();
        example.createCriteria().andTxIdEqualTo(tx.getTxId()).andSeqEqualTo(tx.getSeq());
        final UtxoTransaction exist = this.getOneByExample(example, table);
        if (ObjectUtils.isEmpty(exist)) {
            return this.utxoTransactionRepos.insertOnDuplicateKey(tx, table);
        } else {
            if (tx.getBlockHeight() > 0 && exist.getBlockHeight() <= 0) {
                exist.setBlockHeight(tx.getBlockHeight());
                exist.setUpdateDate(Date.from(Instant.now()));
                return this.editById(exist, table);

            } else {
                return 1;
            }
        }
    }

    @Override
    public int setSpentTxId(UtxoTransaction utxo, String spentTxid, CurrencyEnum currencyEnum) {
        ShardTable table = ShardTable.builder().prefix(currencyEnum.getName()).build();
        utxo.setSpentTxId(spentTxid);
        utxo.setStatus((byte) Constants.SENT);
        utxo.setUpdateDate(Date.from(Instant.now()));
        return this.editById(utxo, table);
    }


}