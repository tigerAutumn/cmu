package cc.newex.dax.market.spider.common.util.exmo;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

@Slf4j
public class ExmoUtil {
    public static LatestTicker buildTicker(final String symbol, final String json) {
        try {
            if (StringUtils.isBlank(json)) {
                return null;
            }
            final JSONObject jsonObject = JSON.parseObject(json).getJSONObject(symbol.toUpperCase());
            if (jsonObject == null) {
                return null;
            }
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(jsonObject.getBigDecimal("high"))
                    .low(jsonObject.getBigDecimal("low"))
                    .buy(jsonObject.getBigDecimal("buy_price"))
                    .sell(jsonObject.getBigDecimal("sell_price"))
                    .volume(jsonObject.getBigDecimal("vol"))
                    .last(jsonObject.getBigDecimal("last_trade"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }
}
