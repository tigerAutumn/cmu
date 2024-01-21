package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.wallet.impl.EoscWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 下线
 * @author newex-team
 * @data 12/04/2018
 */
@Slf4j
// @Component
// @ElasticJobExtConfig(cron = "0/30 * * * * ?")
public class ScanEoscBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanEoscBlockJob(final EoscWallet eoscWallet) {
        this.wallet = eoscWallet;
    }

}
