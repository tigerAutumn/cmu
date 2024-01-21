package cc.newex.wallet.service.impl;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.wallet.criteria.OmniTransactionExample;
import cc.newex.wallet.dao.OmniTransactionRepository;
import cc.newex.wallet.pojo.OmniTransaction;
import cc.newex.wallet.service.OmniTransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Date;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-04-08
 */
@Slf4j
@Service
public class OmniTransactionServiceImpl
        extends AbstractCrudService<OmniTransactionRepository, OmniTransaction, OmniTransactionExample, Long>
        implements OmniTransactionService {

    @Autowired
    private OmniTransactionRepository omniTransactionRepos;

    @Override
    protected OmniTransactionExample getPageExample(final String fieldName, final String keyword) {
        final OmniTransactionExample example = new OmniTransactionExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public int addOnDuplicateKey(final OmniTransaction omni, final ShardTable table) {
        final OmniTransactionExample example = new OmniTransactionExample();
        example.createCriteria().andTxIdEqualTo(omni.getTxId()).andSeqEqualTo(omni.getSeq());
        final OmniTransaction omniExist = this.getOneByExample(example, table);
        if (ObjectUtils.isEmpty(omniExist)) {
            return this.omniTransactionRepos.insertOnDuplicateKey(omni, table);
        } else {
            if (omniExist.getBlockHeight() <= 0 && omni.getBlockHeight() > 0) {
                omniExist.setBlockHeight(omni.getBlockHeight());
                omniExist.setUpdateDate(Date.from(Instant.now()));
                return this.editById(omniExist, table);
            } else {
                return 1;
            }
        }
    }

    @Override
    public BigDecimal getTotalBalance(final OmniTransactionExample example, final ShardTable table) {
        final BigDecimal totalBalance = this.omniTransactionRepos.getTotalBalance(example, table);
        return totalBalance;
    }
}