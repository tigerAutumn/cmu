package cc.newex.dax.market.spider.common.util.bitstamp;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BitStampUtil {
    /**
     * BitStamp获取数据
     */
    public static LatestTicker bitStampBuildTicker(final String json) {
        try {
            final JSONObject jsonObject = JSON.parseObject(json);
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(jsonObject.getBigDecimal("high"))
                    .low(jsonObject.getBigDecimal("low"))
                    .buy(jsonObject.getBigDecimal("bid"))
                    .sell(jsonObject.getBigDecimal("ask"))
                    .volume(jsonObject.getBigDecimal("volume"))
                    .last(jsonObject.getBigDecimal("last"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }

}
