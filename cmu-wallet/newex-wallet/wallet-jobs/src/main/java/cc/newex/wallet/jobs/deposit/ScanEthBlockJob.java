package cc.newex.wallet.jobs.deposit;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.EthWallet;
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
public class ScanEthBlockJob extends AbstractScanAccountBlockJob implements SimpleJob {

    @Autowired
    public ScanEthBlockJob(final EthWallet ethWallet) {
        this.wallet = ethWallet;
    }

//    @Override
//    protected void updateTXConfirmation(final CurrencyEnum currency) {
//        super.updateTXConfirmation(CurrencyEnum.ETH);
//        CurrencyEnum.ERC20_SET.parallelStream().forEach(super::updateTXConfirmation);
//    }
}
