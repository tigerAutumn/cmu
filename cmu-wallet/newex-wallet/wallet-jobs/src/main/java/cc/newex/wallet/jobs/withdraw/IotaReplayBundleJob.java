package cc.newex.wallet.jobs.withdraw;

import cc.newex.commons.redis.REDIS;
import cc.newex.wallet.utils.Constants;
import cc.newex.wallet.wallet.impl.IOTAWallet;
import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * 下线
 * @author newex-team
 * @data 2018/6/21
 */

// @Component
// @ElasticJobExtConfig(cron = "10 1/10 * * * ?")
@Slf4j
public class IotaReplayBundleJob implements SimpleJob {
    @Autowired
    private IOTAWallet iotaWallet;

    @Override
    public void execute(final ShardingContext shardingContext) {
        log.info("iota replay bundle begin");
        final Map<String, String> bundles = REDIS.hGetAll(Constants.WALLET_IOTA_UNCONFIRMED_TX_KEY);
        bundles.forEach((k,v)->{
            if (this.iotaWallet.isReattachable(k)) {
                this.iotaWallet.replayBundle(v);
            }else {
                REDIS.hDel(Constants.WALLET_IOTA_UNCONFIRMED_TX_KEY,k);
            }

        });

        log.info("iota replay bundle end");
    }
}
