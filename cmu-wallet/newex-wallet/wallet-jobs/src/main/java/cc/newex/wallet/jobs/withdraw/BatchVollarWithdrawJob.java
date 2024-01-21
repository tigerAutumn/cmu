package cc.newex.wallet.jobs.withdraw;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.command.VollarCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author newex-team
 * @data 01/04/2018
 */
@Component
//@ElasticJobExtConfig(cron = "1 1/1 * * * ?")
@ElasticJobExtConfig(cron = "0/30 * * * * ?")
@Slf4j
public class BatchVollarWithdrawJob extends AbstractBatchOnlineWithdrawJob implements SimpleJob {

    @Autowired
    VollarCommand command;

    @PostConstruct
    public void init() {
        this.currency = CurrencyEnum.VOLLAR;
        super.setCommand(this.command);
    }

}
















