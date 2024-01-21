package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.pojo.BestBlockHeight;
import cc.newex.wallet.wallet.impl.XlmWallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 下线
 *
 * @author newex-team
 * @data 2018/8/27
 */
// @Component
// @ElasticJobExtConfig(cron = "1 1/2 * * * ?")
@Slf4j
public class ScanXlmBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanXlmBlockJob(final XlmWallet xlmWallet) {
        this.wallet = xlmWallet;
    }

    @Value("${newex.app.env.name}")
    private String env;

    private final CurrencyEnum run = CurrencyEnum.XLM;

    @Override
    public void execute(final ShardingContext shardingContext) {
        log.info("scan {} tx begin", this.wallet.getCurrency().getName());

        if ("dev".equals(this.env)) {
            if (this.wallet.getCurrency() != this.run) {
                return;
            }
        }

        Long bestHeight = null;
        try {
            //先更新数据库中已有的交易的确认数
            this.wallet.updateTXConfirmation(this.wallet.getCurrency());

            //查询链上的最新区块高度
            bestHeight = this.wallet.getBestHeight();

            //查询数据库中已经同步的区块高度
            BestBlockHeight storedHeight = this.getDbBestBlockHeight();

            //初始化best block height 表
            if (ObjectUtils.isEmpty(storedHeight)) {
                storedHeight = new BestBlockHeight();
                final boolean insertFlag = this.initCurrencyBestHeight(storedHeight, bestHeight);
                if (!insertFlag) {
                    return;
                }
            }

            //判断数据库中的区块高度高于链上的高度
            if (storedHeight.getHeight() >= bestHeight) {
                log.error("database height is bigger than best height");
                return;
            }
            final long begin = storedHeight.getHeight();

            //具体扫描区块处理逻辑
            final List<TransactionDTO> transactions = this.wallet.findRelatedTxs(begin);

            //当前区块没有交易数据 记录最高pageingtoken
            if (!CollectionUtils.isEmpty(transactions)) {
                //推送查询到的交易数据
                this.txService.saveTransaction(transactions);
                for (final TransactionDTO tx : transactions) {
                    //如果未达到确认数，直接将tx的paging_token设置为bestHeight
                    if (tx.getConfirmNum() < CurrencyEnum.XLM.getConfirmNum()) {
                        bestHeight = tx.getBlockHeight();
                    }
                }
            }

            //更新数据库最新扫描完的区块
            this.updateStoreHeight(bestHeight, storedHeight);

            //更新币种余额
            this.wallet.updateTotalCurrencyBalance();

        } catch (final Throwable e) {
            log.info("scan {} tx current height:{} error", this.wallet.getCurrency().getName(), bestHeight, e);
        }
        log.info("scan {} tx end, current height:{}", this.wallet.getCurrency().getName(), bestHeight);

    }
}
