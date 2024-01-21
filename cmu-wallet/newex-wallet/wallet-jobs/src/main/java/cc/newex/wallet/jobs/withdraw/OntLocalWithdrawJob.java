package cc.newex.wallet.jobs.withdraw;

import cc.newex.wallet.wallet.impl.OntWallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 下线
 * @author newex-team
 * @data 31/07/2018
 */
// @Component
// @ElasticJobExtConfig(cron = "00 30 23 * * ?")
@Slf4j
public class OntLocalWithdrawJob implements SimpleJob {

    @Autowired
    OntWallet ontWallet;

    @Override
    public void execute(final ShardingContext shardingContext) {
        try {
            OntLocalWithdrawJob.log.info("localWithdrawOnt job begin");
            //由于前期Ont充值提现不活跃，为了能够领取ong，所以自己给自己转一笔(https://github.com/ontio/documentation/blob/master/exchangeDocs/ONT%2BExchange%2BDocking%2BFAQ.md#q-whats-the-ong-unbounding-rule)
            this.ontWallet.localWithdrawOnt();
            OntLocalWithdrawJob.log.info("localWithdrawOnt job end");
        } catch (final Exception e) {
            OntLocalWithdrawJob.log.error("localWithdrawOnt error", e);
        }
    }


}
