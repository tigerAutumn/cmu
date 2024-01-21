package cc.newex.wallet.jobs.transfer;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.wallet.impl.EtcWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author newex-team
 * @data 13/04/2018
 */
@Component
@ElasticJobExtConfig(cron = "1 20 1/1 * * ?")
@Slf4j
public class EtcTransferJob extends AbstractTransferJob {
    @Autowired
    EtcWallet etcWallet;

    @PostConstruct
    public void init() {
        super.setWallet(this.etcWallet);
    }
}
