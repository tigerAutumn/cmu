package cc.newex.dax.market.spider.common.util.btcc;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * Created by tiny on 2017/8/2.
 */
@Slf4j
public class BtccUtil {
    /**
     * btcc数据
     */
    public LatestTicker btccBuildTicker(final String json) {

        try {
            final JSONObject ticker = JSON.parseObject(json).getJSONObject("ticker");
            return LatestTickerConverter.convertTicker(LatestTicker
                    .builder()
                    .high(ticker.getBigDecimal("High"))
                    .low(ticker.getBigDecimal("Low"))
                    .buy(ticker.getBigDecimal("BidPrice"))
                    .sell(ticker.getBigDecimal("AskPrice"))
                    .volume(ticker.getBigDecimal("Volum24H"))
                    .last(ticker.getBigDecimal("Last"))
                    .build());
        } catch (final NullPointerException e) {
            log.error("json {} Return the data can not be empty ！！！", json);
        }

        return null;
    }

}
