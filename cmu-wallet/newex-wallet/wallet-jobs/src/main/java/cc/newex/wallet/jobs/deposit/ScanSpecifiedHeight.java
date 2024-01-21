package cc.newex.wallet.jobs.deposit;

import cc.newex.commons.redis.REDIS;
import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.service.TransactionService;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.AbstractWallet;
import cc.newex.wallet.wallet.WalletContext;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 扫描指定的高度
 *
 * @author newex-team
 * @data 2018/5/3
 */
@Component
@ElasticJobExtConfig(cron = "1 1/2 * * * ?")
@Slf4j
public class ScanSpecifiedHeight implements SimpleJob {
    @Autowired
    protected TransactionService txService;
    @Autowired
    private WalletContext context;

    @Override
    public void execute(final ShardingContext shardingContext) {
        ScanSpecifiedHeight.log.info("ScanSpecifiedHeight start");
        final String key = Constants.WALLET_SCAN_SPECIFIED_HEIGHT_KEY;

        while (true) {
            String val = "";
            try {
                val = REDIS.lPop(key);
                if (ObjectUtils.isEmpty(val)) {
                    break;
                }
                final JSONObject json = JSONObject.parseObject(val);
                final int currencyIndex = json.getInteger("currency");
                CurrencyEnum currency = CurrencyEnum.parseValue(currencyIndex);
//                if (CurrencyEnum.isErc20(currency)) {
//                    currency = CurrencyEnum.ETH;
//                }
                currency = CurrencyEnum.toMainCurrency(currency);
                final AbstractWallet wallet = (AbstractWallet) this.context.getWallet(currency);
                final long height = json.getInteger("height");
                final List<TransactionDTO> transactions = wallet.findRelatedTxs(height);
                if (!CollectionUtils.isEmpty(transactions)) {
                    this.txService.saveTransaction(transactions);
                }

            } catch (final Throwable e) {
                ScanSpecifiedHeight.log.error("ScanSpecifiedHeight error", e);
                break;
            }

        }


        ScanSpecifiedHeight.log.info("ScanSpecifiedHeight end");
    }
}
