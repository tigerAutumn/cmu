package cc.newex.dax.market.spider.common.util.gateio;

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
public class GateIoUtil {
    public static LatestTicker gateioBuildTicker(final String json) {
        try {
            final JSONObject jsonObject = JSON.parseObject(json);
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(jsonObject.getBigDecimal("high24hr"))
                    .low(jsonObject.getBigDecimal("low24hr"))
                    .buy(jsonObject.getBigDecimal("highestBid"))
                    .sell(jsonObject.getBigDecimal("lowestAsk"))
                    .volume(jsonObject.getBigDecimal("baseVolume"))
                    .last(jsonObject.getBigDecimal("last"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }
}
