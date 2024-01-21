package cc.newex.dax.market.spider.domain;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Ticker {

    private double high;
    private double low;
    private double last;
    private double buy;
    private double sell;
    private double volume;
    private double change;

    public static Ticker convertedBean(final String toString) {
        return JSON.parseObject(toString, Ticker.class);
    }

    public static Ticker convertedSymbol(final String strTicker) {
        final Ticker ticker = new Ticker();
        if (strTicker != null) {
            final JSONObject jsonObject = JSON.parseObject(strTicker);
            if (jsonObject.get("High") != null) {
                ticker.setHigh(Double.valueOf(jsonObject.get("High").toString()));
            }
            if (jsonObject.get("Low") != null) {
                ticker.setLow(Double.valueOf(jsonObject.get("Low").toString()));
            }
            if (jsonObject.get("Last") != null) {
                ticker.setLast(Double.valueOf(jsonObject.get("Last").toString()));
            }
            if (jsonObject.get("Volume24H") != null) {
                ticker.setVolume(Double.valueOf(jsonObject.get("Volume24H").toString()));
            }
            if (jsonObject.get("BidPrice") != null) {
                ticker.setSell(Double.valueOf(jsonObject.get("BidPrice").toString()));
            }
            if (jsonObject.get("AskPrice") != null) {
                ticker.setBuy(Double.valueOf(jsonObject.get("AskPrice").toString()));
            }
        }
        return ticker;
    }
}
