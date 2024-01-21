package cc.newex.dax.market.spider.common.util.btce;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BtceUtil {

    /**
     * btce抓取数据转换
     *
     * @param json
     * @return
     */
    public static LatestTicker btceTickerByJson(final String json) {

        try {
            final JSONObject ticker = JSON.parseObject(json).getJSONObject("ticker");
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(ticker.getBigDecimal("high"))
                    .low(ticker.getBigDecimal("low"))
                    .buy(ticker.getBigDecimal("buy"))
                    .sell(ticker.getBigDecimal("sell"))
                    .volume(ticker.getBigDecimal("vol_cur"))
                    .last(ticker.getBigDecimal("last"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("json { } ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }

}
