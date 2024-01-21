package cc.newex.dax.market.spider.common.util.huobi;

import cc.newex.dax.market.spider.common.converter.LatestTickerConverter;
import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.util.List;

@Slf4j
public class HuobiUtil {

    /**
     * 火币抓取数据
     *
     * @param json
     * @return
     */
    public static LatestTicker huoBi(final String json) {
        try {
            final JSONObject ticker = JSON.parseObject(json).getJSONObject("tick");

            final LatestTicker huoBiticker = new LatestTicker();
            huoBiticker.setHigh(ticker.getBigDecimal("high"));
            huoBiticker.setLow(ticker.getBigDecimal("low"));
            huoBiticker.setVolume(ticker.getBigDecimal("vol"));
            huoBiticker.setBuy(ticker.getJSONArray("bid").getBigDecimal(0));
            huoBiticker.setSell(ticker.getJSONArray("ask").getBigDecimal(0));
            huoBiticker.setLast(ticker.getBigDecimal("close"));
            huoBiticker.setVolume(ticker.getBigDecimal("amount"));
            huoBiticker.setBuy(ticker.getBigDecimal("open"));

            return LatestTickerConverter.convertTicker(huoBiticker);
        } catch (final NullPointerException e) {
            log.error("json {} ", "Return the data can not be empty ！！！", json);
        }
        return null;
    }

    /**
     * 火币22小时成交量
     */
    public static BigDecimal recentHuoBi22HoursVol(final String json) {
        final List<JSONArray> data = JSON.parseObject(json, List.class);
        Double amount = 0d;
        if (data != null) {
            int start = data.size() - 22 * 12;
            if (start < 0) {
                return BigDecimal.ZERO;
            }
            for (; start < data.size(); start++) {
                final JSONArray item = data.get(start);
                amount += Double.valueOf(String.valueOf(item.get(5)));
            }
        }
        return BigDecimal.valueOf(amount);
    }

}