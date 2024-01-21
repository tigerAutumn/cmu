package cc.newex.dax.market.spider.common.util.bittrex;

import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * Bittrex的行情
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
public class BittrexUtil {


    public static LatestTicker getTicker(final String result) {

        try {
            final JSONObject ticker = JSON.parseObject(result);
            final Boolean success = ticker.getBoolean("success");
            if (success) {
                final JSONArray all = ticker.getJSONArray("result");
                if (all == null) {
                    return null;
                }
                final JSONObject resultAll = JSONObject.parseObject(all.getString(0));
                final BigDecimal bid = resultAll.getBigDecimal("Bid");
                final BigDecimal ask = resultAll.getBigDecimal("Ask");
                final BigDecimal volume = resultAll.getBigDecimal("Volume");
                final BigDecimal last = resultAll.getBigDecimal("Last");
                final BigDecimal high = resultAll.getBigDecimal("High");
                final BigDecimal low = resultAll.getBigDecimal("Low");

                final LatestTicker ticker1 = new LatestTicker();

                ticker1.setVolume(volume);
                ticker1.setLast(last);
                ticker1.setSell(ask);
                ticker1.setBuy(bid);
                ticker1.setHigh(high);
                ticker1.setLow(low);
                return ticker1;
            }

        } catch (final NullPointerException e) {
            log.error("json {} Return the data can not be empty ！！！", result);
        }

        return null;
    }

}
