package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.ZcashWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @date 2018/11/30
 */
@Component
@ElasticJobExtConfig(cron = "1 1/1 * * * ?")
@Slf4j
public class ScanZcashBlockJob extends AbstractScanUtxoBlockJob implements SimpleJob {
    @Autowired
    public ScanZcashBlockJob(final ZcashWallet zcashWallet) {
        this.wallet = zcashWallet;
    }
}
