package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.UsdtWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 08/04/2018
 */

/**
 * 扫描打包在区块中的 USDT 交易
 *
 * @author newex-team
 * @data 29/03/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 1/5 * * * ?")
@Slf4j
public class ScanUsdtBlockJob extends AbstractScanOmniBlockJob implements SimpleJob {
    @Autowired
    public ScanUsdtBlockJob(final UsdtWallet usdtWallet) {
        this.wallet = usdtWallet;
    }
}
