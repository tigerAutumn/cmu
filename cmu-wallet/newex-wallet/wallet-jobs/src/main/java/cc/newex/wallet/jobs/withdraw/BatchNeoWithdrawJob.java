package cc.newex.wallet.jobs.withdraw;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.criteria.UtxoTransactionExample;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.Address;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import cc.newex.wallet.pojo.WithdrawTransaction;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.impl.Nep5Wallet;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.List;

import static cc.newex.wallet.currency.CurrencyEnum.GAS;

/**
 * @author newex-team
 * @data 01/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 1/2 * * * ?")
@Slf4j
public class BatchNeoWithdrawJob extends AbstractBatchWithdrawJob implements SimpleJob {

    @Autowired
    Nep5Wallet nep5Wallet;

    @PostConstruct
    public void init() {
        this.currency = CurrencyEnum.NEO;
    }

    @Override
    protected WithdrawTransaction buildTransaction(final List<WithdrawRecord> records) {
        WithdrawTransaction transaction = super.buildTransaction(records);
        JSONObject sigJson = JSONObject.parseObject(transaction.getSignature());
        final UtxoTransactionExample example = new UtxoTransactionExample();
        BigDecimal minGasFee = new BigDecimal("0.00000001");

        example.createCriteria().andStatusEqualTo((byte) Constants.WAITING).andBalanceGreaterThan(minGasFee);
        UtxoTransaction gasUtxo = this.utxoService.markAsSpent(example, GAS);

        if (ObjectUtils.isEmpty(gasUtxo)) {
            log.error("buildTransaction failed , neo gas is not enough");
            return null;
        } else {
            final String transactionId = transaction.getId().toString() + this.currency.getName();

            //更新utxo和WithdrawRecord的status
            gasUtxo.setSpentTxId(transactionId);
            ShardTable table = ShardTable.builder().prefix(GAS.getName()).build();
            this.utxoService.editById(gasUtxo, table);

            ShardTable addressTable = ShardTable.builder().prefix(CurrencyEnum.NEO.getName()).build();
            final Address gasAddress = this.addressService.getAddress(gasUtxo.getAddress(), addressTable);
            sigJson.put("gasInput", gasUtxo);
            sigJson.put("gasAddress", gasAddress);
            sigJson.put("gasChange", this.nep5Wallet.getWithdrawAddress());
            transaction.setSignature(sigJson.toJSONString());
        }

        return transaction;

    }
}
















