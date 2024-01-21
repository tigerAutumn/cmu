package cc.newex.dax.market.spider.common.util.zb;

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
public class ZbUtil {
    public static LatestTicker zbBuildTicker(final String json) {
        try {
            final JSONObject jsonObject = JSON.parseObject(json).getJSONObject("ticker");
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(jsonObject.getBigDecimal("high"))
                    .low(jsonObject.getBigDecimal("low"))
                    .buy(jsonObject.getBigDecimal("buy"))
                    .sell(jsonObject.getBigDecimal("sell"))
                    .volume(jsonObject.getBigDecimal("vol"))
                    .last(jsonObject.getBigDecimal("last"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }
}
