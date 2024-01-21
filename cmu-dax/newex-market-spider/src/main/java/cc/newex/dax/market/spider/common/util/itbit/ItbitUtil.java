package cc.newex.dax.market.spider.common.util.itbit;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * add by zhulei
 * <p>
 * 2014年8月11日 下午6:28:32
 **/
@Slf4j
public class ItbitUtil {

    /**
     * Itbit获取数据
     *
     * @param json
     * @return
     */
    public static LatestTicker itbitBuildTicker(final String json) {
        try {
            final JSONObject ticker = JSON.parseObject(json);
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(ticker.getBigDecimal("high24h"))
                    .low(ticker.getBigDecimal("low24h"))
                    .buy(ticker.getBigDecimal("ask"))
                    .sell(ticker.getBigDecimal("bid"))
                    .volume(ticker.getBigDecimal("volume24h"))
                    .last(ticker.getBigDecimal("lastPrice"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("msg {} ", "Return the data can not be empty ！！！", json);
            return null;
        }

    }
}
