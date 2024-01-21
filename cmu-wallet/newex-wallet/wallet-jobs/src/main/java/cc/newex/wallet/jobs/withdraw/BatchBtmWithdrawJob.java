package cc.newex.wallet.jobs.withdraw;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.currency.CurrencyEnum;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author newex-team
 * @data 01/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 1/2 * * * ?")
@Slf4j
public class BatchBtmWithdrawJob extends AbstractBatchWithdrawJob implements SimpleJob {


    @PostConstruct
    public void init() {
        this.currency = CurrencyEnum.BTM;
    }
}
















