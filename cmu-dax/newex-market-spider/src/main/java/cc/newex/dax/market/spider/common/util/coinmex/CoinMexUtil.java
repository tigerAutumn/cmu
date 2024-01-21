package cc.newex.dax.market.spider.common.util.coinmex;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import lombok.extern.slf4j.Slf4j;

/**
 * @author newex-team
 * @date 2018/8/8
 */
@Slf4j
public class CoinMexUtil {
    public static LatestTicker coinMexBuildTicker(final String json) {
        try {
            final JSONArray jsonArray = JSON.parseArray(json);
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(jsonArray.getBigDecimal(1))
                    .low(jsonArray.getBigDecimal(2))
                    .buy(jsonArray.getBigDecimal(4))
                    .sell(jsonArray.getBigDecimal(5))
                    .volume(jsonArray.getBigDecimal(4))
                    .last(jsonArray.getBigDecimal(3))
                    .build());
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }
}
