package cc.newex.dax.market.spider.common.util.bithumb;

import cc.newex.dax.market.spider.domain.LatestTicker;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;

/**
 * bithumb tickr util
 *
 * @author newex-team
 * @date 2018/03/18
 **/
@Slf4j
public class BithumbUtil {

    /**
     * 数据
     *
     * @param json 返回的值
     * @param rate 韩元对美元的汇率
     * @return
     */
    public static LatestTicker bithumbBuildTicker(final String json, final BigDecimal rate) {


        final LatestTicker ticker = new LatestTicker();

        final JSONObject result = JSONObject.parseObject(json);

        if (result.getString("status").equals("0000")) {
            final JSONObject data = result.getJSONObject("data");
            ticker.setSell(data.getBigDecimal("sell_price").multiply(rate));
            ticker.setBuy(data.getBigDecimal("buy_price").multiply(rate));
            ticker.setVolume(data.getBigDecimal("volume_1day"));
            ticker.setOpen(data.getBigDecimal("opening_price").multiply(rate));
            ticker.setLast(data.getBigDecimal("closing_price").multiply(rate));
            ticker.setHigh(data.getBigDecimal("max_price").multiply(rate));
            ticker.setLow(data.getBigDecimal("min_price").multiply(rate));
            return ticker;
        }

        log.error("msg: bithum  data can't  empty ");
        return null;

    }


}
