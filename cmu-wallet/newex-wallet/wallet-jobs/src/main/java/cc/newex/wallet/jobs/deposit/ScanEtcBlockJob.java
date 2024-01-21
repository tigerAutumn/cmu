package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.EtcWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "0/50 * * * * ?")
@Slf4j
public class ScanEtcBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanEtcBlockJob(final EtcWallet etcWallet) {
        this.wallet = etcWallet;
    }
}
