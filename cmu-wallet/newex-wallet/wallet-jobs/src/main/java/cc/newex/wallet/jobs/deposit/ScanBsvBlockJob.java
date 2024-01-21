package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.wallet.impl.BsvWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 下线
 * @author newex-team
 * @data 09/04/2018
 */
@Slf4j
// @Component
// @ElasticJobExtConfig(cron = "1 1/5 * * * ?")
public class ScanBsvBlockJob extends AbstractScanUtxoBlockJob implements SimpleJob {

    @Autowired
    public ScanBsvBlockJob(final BsvWallet bsvWallet) {
        this.wallet = bsvWallet;
    }
}
