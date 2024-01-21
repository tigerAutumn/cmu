package cc.newex.dax.market.spider.common.util.poloniex;

import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
public class Poloniex {

    private static final TypeReference<Map<String, JSONObject>> TYPE = new TypeReference<Map<String, JSONObject>>() {
    };

    public static Map<String, LatestTicker> getTicker(final String json) {

        return JSON.parseObject(json, Poloniex.TYPE)
                .entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> Poloniex.getLatestTicker(e.getValue())));


    }

    private static LatestTicker getLatestTicker(final JSONObject ticker) {
        final BigDecimal bid = ticker.getBigDecimal("highestBid");
        final BigDecimal ask = ticker.getBigDecimal("lowestAsk");
        final BigDecimal percentChange = ticker.getBigDecimal("percentChange");
        final BigDecimal volume = ticker.getBigDecimal("quoteVolume");
        final BigDecimal last = ticker.getBigDecimal("last");
        final BigDecimal high = ticker.getBigDecimal("high24hr");
        final BigDecimal low = ticker.getBigDecimal("low24hr");


        final LatestTicker ticker1 = new LatestTicker();

        ticker1.setVolume(volume);
        ticker1.setLast(last);
        ticker1.setSell(ask);
        ticker1.setBuy(bid);
        ticker1.setHigh(high);
        ticker1.setLow(low);
        ticker1.setChange(percentChange);
        return ticker1;
    }

}
