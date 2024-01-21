package cc.newex.dax.market.spider.common.util.anxbtc;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author newex-team
 */
@Slf4j
public class AnxbtcUtil {

    /**
     * Anxbtc btcTrade数据
     */
    public static LatestTicker anxbtcBuildTicker(final String json) {
        try {
            final LatestTicker anxbtcTicker = new LatestTicker();
            final JSONObject ticker = JSONObject.parseObject(json);
            if ("success".equalsIgnoreCase(ticker.getString("result"))) {
                final JSONObject data = ticker.getJSONObject("data");
                if (data != null) {
                    data.keySet().forEach(key -> {
                        if (!key.equals("now") && !key.equals("dataUpdateTime")) {
                            final BigDecimal value = data.getJSONObject(key).getBigDecimal("value");
                            switch (key) {
                                case "high":
                                    anxbtcTicker.setHigh(value);
                                    break;
                                case "low":
                                    anxbtcTicker.setLow(value);
                                    break;
                                case "last":
                                    anxbtcTicker.setLast(value);
                                    break;
                                case "buy":
                                    anxbtcTicker.setBuy(value);
                                    break;
                                case "sell":
                                    anxbtcTicker.setSell(value);
                                    break;
                                case "vol":
                                    anxbtcTicker.setVolume(value);
                                    break;
                                default:
                                    break;
                            }
                        }
                    });
                }
            }
            return LatestTickerConverter.convertTicker(anxbtcTicker);
        } catch (final NullPointerException e) {
            log.error("msg {} ", "Return the data can not be empty ！！！", json);
            return null;
        }

    }
}
