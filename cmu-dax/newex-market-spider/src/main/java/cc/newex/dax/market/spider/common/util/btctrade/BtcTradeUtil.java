package cc.newex.dax.market.spider.common.util.btctrade;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BtcTradeUtil {
    /**
     * btcTrade数据
     *
     * @param json
     * @return
     */
    public static LatestTicker btcTradeBuildTicker(final String json) {

        try {
            final JSONObject ticker = JSONObject.parseObject(json);
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(ticker.getBigDecimal("high"))
                    .low(ticker.getBigDecimal("low"))
                    .sell(ticker.getBigDecimal("sell"))
                    .volume(ticker.getBigDecimal("vol"))
                    .last(ticker.getBigDecimal("last"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("msg {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }
}
