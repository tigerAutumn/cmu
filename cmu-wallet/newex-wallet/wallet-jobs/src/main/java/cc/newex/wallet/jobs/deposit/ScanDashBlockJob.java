package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.wallet.impl.DashWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 下线
 * 扫描打包在区块中的交易
 *
 * @author newex-team
 * @data 29/03/2018
 */
@Slf4j
// @Component
// @ElasticJobExtConfig(cron = "1 1/1 * * * ?")
public class ScanDashBlockJob extends AbstractScanUtxoBlockJob implements SimpleJob {

    @Autowired
    public ScanDashBlockJob(final DashWallet dashWallet) {
        this.wallet = dashWallet;
    }
}
