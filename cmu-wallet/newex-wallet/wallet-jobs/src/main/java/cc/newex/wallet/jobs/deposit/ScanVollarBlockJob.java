package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.VollarWallet;
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
//@ElasticJobExtConfig(cron = "1 1/1 * * * ?")
@ElasticJobExtConfig(cron = "0/30 * * * * ?")
@Slf4j
public class ScanVollarBlockJob extends AbstractScanOnlineBtcBlockJob implements SimpleJob {

    @Autowired
    public ScanVollarBlockJob(final VollarWallet vollarWallet) {
        this.wallet = vollarWallet;
    }
}
