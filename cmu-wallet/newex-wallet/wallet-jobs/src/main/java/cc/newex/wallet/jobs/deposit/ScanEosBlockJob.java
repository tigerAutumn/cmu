package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.EosWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "0 0/15 * * * ?")
@Slf4j
public class ScanEosBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanEosBlockJob(final EosWallet eosWallet) {
        this.wallet = eosWallet;
    }

}
