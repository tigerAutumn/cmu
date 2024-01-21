package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.XemWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 2018/8/27
 */
@Component
@ElasticJobExtConfig(cron = "0/30 * * * * ?")
@Slf4j
public class ScanXemBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanXemBlockJob(final XemWallet xemWallet) {
        this.wallet = xemWallet;
    }

}
