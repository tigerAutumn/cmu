package cc.newex.wallet.jobs.deposit;

import cc.newex.wallet.wallet.impl.ActWallet;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 下线
 *
 * @author newex-team
 * @date 2018/5/17
 */
//@Component
//@ElasticJobExtConfig(cron = "0/30 * * * * ?")
@Slf4j
public class ScanActBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {
    @Autowired
    public ScanActBlockJob(final ActWallet actWallet) {
        this.wallet = actWallet;
    }

//    @Override
//    protected void updateTXConfirmation(final CurrencyEnum currency) {
//        super.updateTXConfirmation(CurrencyEnum.ACT);
//        CurrencyEnum.ATP_SET.parallelStream().forEach(super::updateTXConfirmation);
//    }
}
