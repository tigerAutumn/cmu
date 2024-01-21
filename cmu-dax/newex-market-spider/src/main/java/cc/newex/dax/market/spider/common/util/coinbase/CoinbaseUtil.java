package cc.newex.dax.market.spider.common.util.coinbase;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author newex-team
 */
@Slf4j
public class CoinbaseUtil {

    /**
     * coinBase抓取数据
     *
     * @param tickerStr
     * @param tickerStrBid
     * @param tickerStrVom
     * @return
     */
    public static LatestTicker coinBaseBuildTicker(final String tickerStr, final String tickerStrBid, final String tickerStrVom) {

        try {
            final JSONObject json = JSONObject.parseObject(tickerStr);
            final JSONObject json_bid = JSONObject.parseObject(tickerStrBid);
            final JSONObject json_vom = JSONObject.parseObject(tickerStrVom);
            final JSONArray asks = JSON.parseArray(json_bid.getString("asks")).getJSONArray(0);
            final JSONArray bids = JSON.parseArray(json_bid.getString("bids")).getJSONArray(0);

            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(json_vom.getBigDecimal("high"))
                    .low(json_vom.getBigDecimal("low"))
                    .volume(json_vom.getBigDecimal("volume"))
                    .sell(new BigDecimal(asks.getString(0)))
                    .buy(new BigDecimal(bids.getString(0)))
                    .last(json.getBigDecimal("price"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("msg {} ", "Return the data can not be empty ！！！", tickerStr + tickerStrBid + tickerStrVom);
            return null;
        }
    }
}
