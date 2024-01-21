package cc.newex.dax.market.spider.common.util.gemini;

import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;

/**
 * GEMINI交易所行情提取
 *
 * @author newex-team
 * @date 2018/03/18
 **/
public class GEMINIUtil {

    public static LatestTicker getTicker(final String result) {

        final JSONObject ticker = JSON.parseObject(result);
        final BigDecimal bid = ticker.getBigDecimal("bid");
        final BigDecimal ask = ticker.getBigDecimal("ask");
        final BigDecimal last = ticker.getBigDecimal("last");
        final JSONObject volume = ticker.getJSONObject("volume");
        final BigDecimal btc = volume.getBigDecimal("BTC");

        final LatestTicker latestTicker = new LatestTicker();
        latestTicker.setBuy(bid);
        latestTicker.setSell(ask);
        latestTicker.setLast(last);
        latestTicker.setVolume(btc);

        return latestTicker;
    }
}
