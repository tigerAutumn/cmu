package cc.newex.wallet.service;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.wallet.criteria.OmniTransactionExample;
import cc.newex.wallet.pojo.OmniTransaction;

import java.math.BigDecimal;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018-04-08
 */
public interface OmniTransactionService
        extends CrudService<OmniTransaction, OmniTransactionExample, Long> {
    int addOnDuplicateKey(OmniTransaction omni, ShardTable table);

    /**
     * 获取utxo的balance总和
     *
     * @return
     */
    BigDecimal getTotalBalance(OmniTransactionExample example, ShardTable table);
}
