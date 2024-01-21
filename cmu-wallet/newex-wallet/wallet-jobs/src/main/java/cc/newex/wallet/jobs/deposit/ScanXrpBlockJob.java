package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.XrpWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "10 0/1 * * * ?")
@Slf4j
public class ScanXrpBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanXrpBlockJob(final XrpWallet xrpWallet) {
        this.wallet = xrpWallet;
    }
}
