package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.dto.TransactionDTO;
import cc.newex.wallet.wallet.impl.IOTAWallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 下线
 * @author newex-team
 * @data 13/04/2018
 */
@Slf4j
// @Component
// @ElasticJobExtConfig(cron = "10 0/5 * * * ?")
public class ScanIotaBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanIotaBlockJob(final IOTAWallet wallet) {
        this.wallet = wallet;
    }

    @Value("${newex.app.env.name}")
    private String env;
    @Override
    public void execute(final ShardingContext shardingContext) {


        log.info("scan {} tx begin", this.wallet.getCurrency().getName());

        try {
            if ("dev".equals(this.env)) {
                return;
            }
            final List<TransactionDTO> transactions;
            transactions = this.wallet.findRelatedTxs(0L);
            if(!CollectionUtils.isEmpty(transactions)){
                this.txService.saveTransaction(transactions);
            }
            this.wallet.updateTotalCurrencyBalance();
        } catch (final Throwable e) {
            log.info("scan {} tx error", this.wallet.getCurrency().getName(), e);
        }
        log.info("scan {} tx end", this.wallet.getCurrency().getName());
    }

}
