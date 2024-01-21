package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.wallet.impl.XmrWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 下线
 *
 * @author newex-team
 * @data 2018/12/25
 */
// @Component
// @ElasticJobExtConfig(cron = "1 1/5 * * * ?")
@Slf4j
public class ScanXmrBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {
    @Autowired
    public ScanXmrBlockJob(final XmrWallet xmrWallet) {
        this.wallet = xmrWallet;
    }
}
