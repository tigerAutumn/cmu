package cc.newex.dax.market.job.service.impl;

import cc.newex.commons.lang.util.DoubleUtil;
import cc.newex.dax.market.common.consts.IndexConst;
import cc.newex.dax.market.common.enums.CoinConfigTypeEnum;
import cc.newex.dax.market.common.enums.PublishTypeEnum;
import cc.newex.dax.market.common.util.DateUtils;
import cc.newex.dax.market.common.util.StringUtil;
import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.MarketIndex;
import cc.newex.dax.market.job.service.PublishService;
import cc.newex.dax.market.job.service.MarketIndexCacheBuildAndPushService;
import cc.newex.dax.market.service.MarketIndexService;
import cc.newex.dax.market.service.MonitorService;
import cc.newex.dax.market.service.RedisService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 指数数据更新缓存并推送
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Service("marketIndexCacheBuildAndPushService")
public class MarketIndexCacheBuildAndPushServiceImpl implements MarketIndexCacheBuildAndPushService {
    @Autowired
    MonitorService monitorService;
    @Autowired
    PublishService publishService;
    @Autowired
    MarketIndexService marketIndexService;
    @Autowired
    RedisService redisService;

    @Override
    public void marketIndexCacheBuildAndPush(final CoinConfig coinConfig) {
        final Date before24HourDate = DateUtils.getSubtractedDateByElapsedHourValue(new Date(), 24);

        final double high = this.marketIndexService.getIndexBefore24HourHigh(coinConfig.getSymbol(), before24HourDate);
        final double low = this.marketIndexService.getIndexBefore24HourLow(coinConfig.getSymbol(), before24HourDate);
        final double open = this.getOpenPriceBefore24Hour(coinConfig.getIndexMarketFrom());
        double last = 0D;
        final MarketIndex futureIndex = this.getIndexBySymbolFromCache(coinConfig);
        if (futureIndex != null) {
            last = futureIndex.getPrice();
        }
        //涨跌幅
        double changePercent = 0D;
        if (open != 0D) {
            changePercent = DoubleUtil.divide(DoubleUtil.subtract(last, open), open, 6);
        }

        final JSONObject jsonObject = new JSONObject();
        jsonObject.put("last", String.valueOf(last));
        jsonObject.put("open", String.valueOf(open));
        jsonObject.put("high", String.valueOf(high));
        jsonObject.put("low", String.valueOf(low));
        jsonObject.put("changeRate", String.valueOf(changePercent));
        jsonObject.put("currencyCode", coinConfig.getSymbolName());
        
        jsonObject.put("type", coinConfig.getType().equals(0) ?
                CoinConfigTypeEnum.MARKET.name() : CoinConfigTypeEnum.PORTFOLIO.name());

        //redis key
        this.publishService.setInfo(String.format(IndexConst.KEY_INDEX_TICKER, coinConfig.getSymbol()),
                JSON.toJSONString(jsonObject));

        //指数ticker 推送
        this.publishService.publish(IndexConst.INDEX_CHANNEL, PublishTypeEnum.TICKER, coinConfig.getSymbolName(), 1,
                jsonObject, null, null);
    }

    /**
     * 取得过去24小时开盘价
     *
     * @param marketFrom
     * @return
     */
    private double getOpenPriceBefore24Hour(final int marketFrom) {
        final String cacheKey = String.format(IndexConst.KEY_OPEN_PRICE_BEFORE_24HOUR_CACHE, marketFrom);
        final String o = this.redisService.getInfo(cacheKey);
        if (o == null) {
            return 0D;
        }
        return Double.parseDouble(o);
    }

    public MarketIndex getIndexBySymbolFromCache(final CoinConfig coinConfig) {
        final String format = String.format(IndexConst.FUTURE_KEY_LAST_INDEX,
                coinConfig.getSymbolName());
        final String futureIndexStr = this.redisService.getRandom(format);
        if (!StringUtil.isEmpty(futureIndexStr)) {
            return JSON.parseObject(futureIndexStr, MarketIndex.class);
        } else {
            return this.getIndexBySymbol(coinConfig.getSymbol());
        }
    }

    public MarketIndex getIndexBySymbol(final int symbol) {
        final List<MarketIndex> list = this.marketIndexService.getMarketIndexAndLimit(symbol, 1);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
