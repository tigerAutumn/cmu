package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.LtcWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "2 1/2 * * * ?")
@Slf4j
public class ScanLtcBlockJob extends AbstractScanUtxoBlockJob implements SimpleJob {

    @Autowired
    public ScanLtcBlockJob(final LtcWallet ltcWallet) {
        this.wallet = ltcWallet;
    }
}
