package cc.newex.dax.market.spider.common.converter;

import cc.newex.dax.market.spider.domain.LatestTicker;

import java.math.BigDecimal;


/**
 * @author newex-team
 * @date 2018/03/18
 **/
public class LatestTickerConverter {
    /**
     * 确保必要数据不能为空
     *
     * @param latestTicker
     * @return
     */
    public static LatestTicker convertTicker(final LatestTicker latestTicker) {
        if (latestTicker.getHigh() == null) {
            latestTicker.setHigh(BigDecimal.ZERO);
        }
        if (latestTicker.getLast() == null) {
            latestTicker.setLast(BigDecimal.ZERO);
        }
        if (latestTicker.getSell() == null) {
            latestTicker.setSell(BigDecimal.ZERO);
        }
        if (latestTicker.getLow() == null) {
            latestTicker.setLow(BigDecimal.ZERO);
        }
        if (latestTicker.getBuy() == null) {
            latestTicker.setBuy(BigDecimal.ZERO);
        }
        if (latestTicker.getMarketFrom() == null) {
            latestTicker.setMarketFrom(-1);
        }
        if (latestTicker.getVolume() == null) {
            latestTicker.setVolume(BigDecimal.ZERO);
        }
        if (latestTicker.getChange24() == null) {
            latestTicker.setChange24(BigDecimal.ZERO);
        }
        return latestTicker;
    }

}
