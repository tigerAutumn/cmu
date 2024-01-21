package cc.newex.commons.dictionary.util;

import cc.newex.commons.dictionary.consts.CacheKeys;

/**
 * @author newex-team
 * @date 2017/12/09
 */
public class CacheKeyUtil {
    /**
     * 获取深度缓存key
     *
     * @param symbol currencypair.symbol
     * @return key name
     */
    public static String getDepthKey(final String symbol) {
        return CacheKeys.DEPTH_PREFIX + CacheKeys.DELIMITER + symbol;
    }

    /**
     * 获取成交缓存key
     *
     * @param symbol currencypair.symbol
     * @return key name
     */
    public static String getDealKey(final String symbol) {
        return CacheKeys.DEAL_PREFIX + CacheKeys.DELIMITER + symbol;
    }

    /**
     * 返回ticker
     *
     * @param marketFrom
     * @return key name
     */
    public static String getTickerPrefix(final int marketFrom) {
        return CacheKeys.TICKER_PREFIX + CacheKeys.DELIMITER + marketFrom;
    }

    /**
     * @param marketFrom
     * @param period
     * @return key name
     */
    public static String getKlinePrefix(final Integer marketFrom, final byte period) {
        return CacheKeys.MARKET_PREFIX + CacheKeys.DELIMITER + marketFrom + CacheKeys.DELIMITER + period;
    }

    /**
     * 根据ProductId 获取信息
     *
     * @param symbol
     * @return key name
     */
    public static String getMergeTypePrefix(final String symbol) {
        return CacheKeys.MERGE_TYPE_PREFIX + CacheKeys.DELIMITER + symbol;
    }

    /**
     * 价格预警 key
     *
     * @return key name
     */
    public static String getPriceAlertPrefix(final String key) {
        return CacheKeys.PRICE_ALERT_PREFIX + CacheKeys.DELIMITER + key;
    }

    /**
     * @param marketFrom
     * @return key name
     */
    public static String getFechingDataPath(final String marketFrom) {
        return CacheKeys.FETCHING_PATH_KEY + marketFrom;
    }

    /**
     * 获取存放指定市场币价的Key。
     *
     * @return key name
     */
    public static String getKey4LatestTickerMarketfrom(final int marketFrom) {
        return CacheKeys.MARKET_LATEST_TICKER_MARKETFROM + marketFrom;
    }
}
