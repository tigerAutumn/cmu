package cc.newex.wallet.jobs.withdraw;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.command.RcCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author newex-team
 * @data 03/28/2019
 */
@Component
@ElasticJobExtConfig(cron = "0/30 * * * * ?")
@Slf4j
public class BatchRcWithdrawJob extends AbstractBatchOnlineWithdrawJob implements SimpleJob {

    @Autowired
    RcCommand command;

    @PostConstruct
    public void init() {
        this.currency = CurrencyEnum.RC;
        super.setCommand(this.command);

    }
}