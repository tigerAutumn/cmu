package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.BchWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 09/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 1/5 * * * ?")
@Slf4j
public class ScanBchBlockJob extends AbstractScanUtxoBlockJob implements SimpleJob {

    @Autowired
    public ScanBchBlockJob(final BchWallet bchWallet) {
        this.wallet = bchWallet;
    }
}
