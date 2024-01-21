package cc.newex.wallet.jobs.transfer;

import cc.newex.wallet.wallet.impl.RbtcWallet;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

/**
 * 下线
 * @author newex-team
 * @data 13/04/2018
 */
// @Component
// @ElasticJobExtConfig(cron = "1 30 1/1 * * ?")
//@ElasticJobExtConfig(cron = "0/30 * * * * ?")
@Slf4j
public class RbtcTransferJob extends AbstractTransferJob {
    @Autowired
    RbtcWallet rbtcWallet;

    @PostConstruct
    public void init() {
        super.setWallet(this.rbtcWallet);
    }
}
