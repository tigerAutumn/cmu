package cc.newex.dax.market.spider.common.util.okcoin;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OKCoinUtil {
    /**
     * OKCoin获取数据
     *
     * @param json
     * @return
     */
    public static LatestTicker oKCoinCNBuildTicker(final String json) {
        try {
            final JSONObject ticker = JSON.parseObject(json).getJSONObject("ticker");
            final LatestTicker build = LatestTicker
                    .builder()
                    .high(ticker.getBigDecimal("high"))
                    .low(ticker.getBigDecimal("low"))
                    .buy(ticker.getBigDecimal("buy"))
                    .sell(ticker.getBigDecimal("sell"))
                    .volume(ticker.getBigDecimal("vol"))
                    .last(ticker.getBigDecimal("last"))
                    .build();
            return LatestTickerConverter.convertTicker(build);
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }

    public static LatestTicker OKEXBuildTicker(final String json) {
        final JSONObject jsonObject = JSON.parseObject(json);
        final JSONObject ticker = JSONObject.parseObject(jsonObject.getString("ticker"));
        final LatestTicker build = LatestTicker
                .builder()
                .high(ticker.getBigDecimal("high"))
                .low(ticker.getBigDecimal("low"))
                .buy(ticker.getBigDecimal("buy"))
                .sell(ticker.getBigDecimal("sell"))
                .volume(ticker.getBigDecimal("vol"))
                .last(ticker.getBigDecimal("last"))
                .build();
        return LatestTickerConverter.convertTicker(build);
    }

}
