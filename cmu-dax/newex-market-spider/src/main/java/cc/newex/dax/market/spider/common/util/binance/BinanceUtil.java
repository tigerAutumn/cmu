package cc.newex.dax.market.spider.common.util.binance;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author newex-team
 * @date 2018/7/24
 */
@Slf4j
public class BinanceUtil {
    public static LatestTicker binanceBuildTicker(final String json) {
        try {
            final JSONObject jsonObject = JSON.parseObject(json);
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(jsonObject.getBigDecimal("highPrice"))
                    .low(jsonObject.getBigDecimal("lowPrice"))
                    .buy(jsonObject.getBigDecimal("bidPrice"))
                    .sell(jsonObject.getBigDecimal("askPrice"))
                    .volume(jsonObject.getBigDecimal("volume"))
                    .last(jsonObject.getBigDecimal("lastPrice"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }
}
