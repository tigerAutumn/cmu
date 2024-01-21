package cc.newex.dax.market.spider.common.util.bitfinex;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BtcFinexUtil {
    /**
     * btcFinex获取数据
     *
     * @param json
     * @return
     */
    public static LatestTicker btcFinexBuildTicker(final String json) {
        try {
            final JSONObject jsonObject = JSON.parseObject(json);
            return LatestTickerConverter.convertTicker(
                    LatestTicker
                            .builder()
                            .low(jsonObject.getBigDecimal("low"))
                            .volume(jsonObject.getBigDecimal("volume"))
                            .sell(jsonObject.getBigDecimal("ask"))
                            .buy(jsonObject.getBigDecimal("bid"))
                            .high(jsonObject.getBigDecimal("high"))
                            .last(jsonObject.getBigDecimal("last_price"))
                            .build()
            );
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }

}
