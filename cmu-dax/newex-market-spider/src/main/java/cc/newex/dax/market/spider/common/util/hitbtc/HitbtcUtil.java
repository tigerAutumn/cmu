package cc.newex.dax.market.spider.common.util.hitbtc;

import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
public class HitbtcUtil {

    public static LatestTicker hitbtcBuildTicker(final String json) {
        try {
            final JSONObject ticker = JSON.parseObject(json);
            final BigDecimal bid = ticker.getBigDecimal("bid");
            final BigDecimal ask = ticker.getBigDecimal("ask");
            final BigDecimal last = ticker.getBigDecimal("last");
            final BigDecimal open = ticker.getBigDecimal("open");
            final BigDecimal high = ticker.getBigDecimal("high");
            final BigDecimal low = ticker.getBigDecimal("low");
            final BigDecimal volume = ticker.getBigDecimal("volume");

            final LatestTicker latestTicker = new LatestTicker();
            latestTicker.setBuy(bid);
            latestTicker.setSell(ask);
            latestTicker.setLast(last);
            latestTicker.setVolume(volume);
            latestTicker.setLow(low);
            latestTicker.setOpen(open);
            latestTicker.setHigh(high);

            return latestTicker;
        } catch (final Exception e) {
            log.error("hitbtcBuildTicker error, ticker: {}, msg: {}", json, e.getMessage(), e);
        }

        return null;
    }

}
