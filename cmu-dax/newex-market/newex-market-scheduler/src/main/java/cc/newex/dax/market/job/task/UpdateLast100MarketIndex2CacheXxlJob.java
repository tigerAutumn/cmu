package cc.newex.dax.market.job.task;

import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.MarketIndex;
import cc.newex.dax.market.job.service.CoinService;
import cc.newex.dax.market.service.MarketIndexService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSONArray;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author newex-team
 * @date 2018/7/23
 */
@Slf4j
@JobHandler(value = "UpdateLast100MarketIndex2CacheXxlJob")
@Component
public class UpdateLast100MarketIndex2CacheXxlJob extends IJobHandler {
    @Autowired
    CoinService coinService;
    @Autowired
    MarketIndexService marketIndexService;
    @Autowired
    RedisService redisService;

    @Override
    public ReturnT<String> execute(final String s) throws Exception {
        this.doExecute();
        return IJobHandler.SUCCESS;
    }

    /**
     * 更新最新100条指数数据至缓存
     */
    private void doExecute() {
        final List<CoinConfig> coinConfigList = this.coinService.getCoinConfigList();
        if (CollectionUtils.isEmpty(coinConfigList)) {
            UpdateLast100MarketIndex2CacheXxlJob.log.info("updateLast100MarketIndex2Cache coinsArray is null");
            return;
        }
        for (final CoinConfig coinConfig : coinConfigList) {
            final List<MarketIndex> marketIndexList = this.marketIndexService.getMarketIndexAndLimit(coinConfig.getSymbol(), 100);
            this.redisService.setInfo(this.coinService.getIndexCacheKey(coinConfig),
                    JSONArray.toJSONString(marketIndexList));
        }
    }
}