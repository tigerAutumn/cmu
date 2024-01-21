package cc.newex.dax.market.spider.common.util.kraken;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

/**
 * @author newex-team
 */
@Slf4j
public class KrakenUtil {
    /**
     * kraken数据
     *
     * @param json
     * @param key
     * @return
     */
    public static LatestTicker krakenBuildTicker(final String json, final String key) {
        try {
            final LatestTicker krakenTicker = new LatestTicker();
            final JSONObject jsonObject = JSONObject.parseObject(json);
            final JSONObject result = jsonObject.getJSONObject("result");
            if (result != null && result.getString(key) != null) {
                final JSONObject btzusd = result.getJSONObject(key);
                btzusd.keySet().forEach(btz -> {
                    if (!btz.equalsIgnoreCase("o")) {
                        final JSONArray btzusdJson = btzusd.getJSONArray(btz);
                        switch (btz) {
                            case "h":
                                krakenTicker.setHigh(btzusdJson.getBigDecimal(0));
                                break;
                            case "l":
                                krakenTicker.setLow(btzusdJson.getBigDecimal(1));
                                break;
                            case "c":
                                krakenTicker.setLast(btzusdJson.getBigDecimal(0));
                                break;
                            case "b":
                                krakenTicker.setBuy(btzusdJson.getBigDecimal(0));
                                break;
                            case "a":
                                krakenTicker.setSell(btzusdJson.getBigDecimal(0));
                                break;
                            case "v":
                                krakenTicker.setVolume(btzusdJson.getBigDecimal(1));
                                break;
                            default:
                                break;
                        }
                    }
                });
            }
            return LatestTickerConverter.convertTicker(krakenTicker);
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }

}
