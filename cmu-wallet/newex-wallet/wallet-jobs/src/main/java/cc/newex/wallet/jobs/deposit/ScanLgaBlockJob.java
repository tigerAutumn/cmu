package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.LgaWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 扫描打包在区块中的交易
 *
 * @author newex-team
 * @data 29/03/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 1/1 * * * ?")
@Slf4j
public class ScanLgaBlockJob extends AbstractScanUtxoBlockJob implements SimpleJob {

    @Autowired
    public ScanLgaBlockJob(final LgaWallet lgaWallet) {
        this.wallet = lgaWallet;
    }
}
