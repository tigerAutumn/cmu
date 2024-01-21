package cc.newex.wallet.jobs.transfer;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.EthWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 30 1/1 * * ?")
@Slf4j
public class EthTransferJob extends AbstractTransferJob {
    @Autowired
    EthWallet ethWallet;

    @PostConstruct
    public void init() {
        super.setWallet(this.ethWallet);
    }
}
