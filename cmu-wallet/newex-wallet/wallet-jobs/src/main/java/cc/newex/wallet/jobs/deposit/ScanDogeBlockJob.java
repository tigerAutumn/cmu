package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.wallet.impl.DogeWallet;
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
public class ScanDogeBlockJob extends AbstractScanUtxoBlockJob implements SimpleJob {

    @Autowired
    public ScanDogeBlockJob(final DogeWallet dogeWallet) {
        this.wallet = dogeWallet;
    }
}
