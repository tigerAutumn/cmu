package cc.newex.dax.market.job.service.impl;

import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.util.DateUtils;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.MarketData;
import cc.newex.dax.market.job.service.MarketDataOuterOpenPrice24HService;
import cc.newex.dax.market.service.MarketDataService;
import cc.newex.dax.market.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 生成外部指数K线的24小时内开盘价
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Service("marketDataOuterOpenPrice24HService")
public class MarketDataOuterOuterOpenPrice24HServiceImpl implements MarketDataOuterOpenPrice24HService {

    @Autowired
    MarketDataService marketDataService;
    @Autowired
    RedisService redisService;

    @Override
    public void openPriceBefore24H(final CoinConfig coinConfig) {
        final String cacheKey = String.format(IndexConst.KEY_OPEN_PRICE_BEFORE_24HOUR_CACHE,
                coinConfig.getIndexMarketFrom());
        final Date dateBefore24Hour = DateUtils.getDateInDayAgo(new Date(), -1);
        final MarketData open = this.marketDataService.getTodayOpenPrice(coinConfig, dateBefore24Hour);
        if (open != null) {
            this.redisService.setInfo(cacheKey, String.valueOf(open.getOpen()));
        }
    }
}
