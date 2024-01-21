package cc.newex.wallet.jobs.withdraw;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.currency.CurrencyEnum;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author newex-team
 * @date 2018/11/30
 */
@Component
@ElasticJobExtConfig(cron = "1 1/2 * * * ?")
@Slf4j
public class BatchZcashWithdrawJob extends AbstractBatchWithdrawJob implements SimpleJob {
    @PostConstruct
    public void init() {
        this.currency = CurrencyEnum.ZEC;
    }
}
