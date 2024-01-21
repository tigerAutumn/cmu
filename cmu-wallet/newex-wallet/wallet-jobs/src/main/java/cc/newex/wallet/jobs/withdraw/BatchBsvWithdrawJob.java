package cc.newex.wallet.jobs.withdraw;

import cc.newex.wallet.currency.CurrencyEnum;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.PostConstruct;

/**
 * 下线
 *
 * @author newex-team
 * @data 10/04/2018
 */
// @Component
// @ElasticJobExtConfig(cron = "1 1/1 * * * ?")
@Slf4j
public class BatchBsvWithdrawJob extends AbstractBatchWithdrawJob implements SimpleJob {
    @PostConstruct
    public void init() {
        this.currency = CurrencyEnum.BSV;
    }
}
