package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.BaicWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 2019/1/21
 */
@Component
@ElasticJobExtConfig(cron = "1 1/25 * * * ?")
@Slf4j
public class ScanBaicBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {
    @Autowired
    public ScanBaicBlockJob(final BaicWallet baicWallet) {
        this.wallet = baicWallet;
    }
}
