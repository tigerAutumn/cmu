package cc.newex.dax.market.job.task;

import cc.newex.dax.market.common.consts.MarketAllRateConst;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.service.FetchingDataPathService;
import cc.newex.dax.market.service.LatestTickerService;
import cc.newex.dax.market.service.MarketAllRateService;
import cc.newex.dax.market.service.MinePoolShareDataService;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @date 2018/7/23
 */
@Slf4j
@JobHandler(value = "ReloadCacheXxlJob")
@Component
public class ReloadCacheXxlJob extends IJobHandler {

    @Autowired
    private CoinService coinService;
    @Autowired
    private FetchingDataPathService fetchingDataPathService;
    @Autowired
    private LatestTickerService latestTickerService;
    @Autowired
    private MarketAllRateService marketAllRateService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.coinService.reloadCache();
        this.fetchingDataPathService.putAllPathRedis();
        this.latestTickerService.putTickerRedis();
        MarketAllRateConst.RATE_LIST.stream().forEach(rate -> {
            this.marketAllRateService.putRateRedis(rate);
        });
        log.info("ReloadCacheXxlJob new");
        return IJobHandler.SUCCESS;
    }

}