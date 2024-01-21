package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.TronWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Component
//@ElasticJobExtConfig(cron = "0/30 * * * * ?")
@ElasticJobExtConfig(cron = "1 1/2 * * * ?")
@Slf4j
public class ScanTronBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanTronBlockJob(final TronWallet tronWallet) {
        this.wallet = tronWallet;
    }

}
