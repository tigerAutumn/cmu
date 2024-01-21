package cc.newex.dax.market.spider.common.util.upbit;

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
public class UpBitUtil {
    public static LatestTicker upBitBuildTicker(final String json) {
        try {
            final JSONObject jsonObject = JSON.parseObject(json);
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(jsonObject.getBigDecimal("high_price"))
                    .low(jsonObject.getBigDecimal("low_price"))
                    .buy(jsonObject.getBigDecimal("opening_price"))
                    .sell(jsonObject.getBigDecimal("prev_closing_price"))
                    .volume(jsonObject.getBigDecimal("trade_volume"))
                    .last(jsonObject.getBigDecimal("trade_price"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }
}
