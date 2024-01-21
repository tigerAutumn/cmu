package cc.newex.wallet.jobs.withdraw;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.command.GodCommand;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.pojo.UtxoTransaction;
import cc.newex.wallet.pojo.WithdrawRecord;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author newex-team
 * @data 03/28/2019
 */
@Component
@ElasticJobExtConfig(cron = "0/30 * * * * ?")
@Slf4j
public class BatchGodWithdrawJob extends AbstractBatchOnlineWithdrawJob implements SimpleJob {

    @Autowired
    GodCommand command;

    @PostConstruct
    public void init() {
        this.currency = CurrencyEnum.GOD;
        super.setCommand(this.command);

    }
}