package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.wallet.impl.RbtcWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 下线
 *
 * @author newex-team
 * @data 12/04/2018
 */
// @Component
// @ElasticJobExtConfig(cron = "0/30 * * * * ?")
@Slf4j
public class ScanRbtcBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanRbtcBlockJob(final RbtcWallet rbtcWallet) {
        this.wallet = rbtcWallet;
    }

}
