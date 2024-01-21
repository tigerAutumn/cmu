package cc.newex.wallet.jobs.claim;

import cc.newex.extension.annotation.ElasticJobExtConfig;
import cc.newex.wallet.currency.CurrencyEnum;
import cc.newex.wallet.wallet.impl.OntWallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @data 31/07/2018
 */
@Component
@ElasticJobExtConfig(cron = "00 59 23 * * ?")
@Slf4j
public class OngClaimJob implements SimpleJob {

    @Autowired
    OntWallet ontWallet;

    @Override
    public void execute(final ShardingContext shardingContext) {
        try {
            OngClaimJob.log.info("Claim ong job begin");
            this.ontWallet.claimOng(CurrencyEnum.ONG);
            OngClaimJob.log.info("Claim ong job end");
        } catch (Exception e) {
            OngClaimJob.log.error("OngClaimJob error", e);
        }
    }


}
