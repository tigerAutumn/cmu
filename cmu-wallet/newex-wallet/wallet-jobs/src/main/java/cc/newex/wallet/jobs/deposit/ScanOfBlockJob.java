package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.OfWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 12/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "0/30 * * * * ?")
@Slf4j
public class ScanOfBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanOfBlockJob(final OfWallet ofWallet) {
        this.wallet = ofWallet;
    }

//    @Override
//    protected void updateTXConfirmation(final CurrencyEnum currency) {
//        super.updateTXConfirmation(CurrencyEnum.OF);
//        CurrencyEnum.ORC_TOKEN_SET.parallelStream().forEach(super::updateTXConfirmation);
//    }

}
