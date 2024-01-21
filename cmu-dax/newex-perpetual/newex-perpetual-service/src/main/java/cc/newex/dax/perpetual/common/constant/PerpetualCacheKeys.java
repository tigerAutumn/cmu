package cc.newex.dax.perpetual.common.constant;

/**
 * @author newex-team
 * @date 2018/9/25
 */
public class PerpetualCacheKeys {

    private static final String PERPETUAL_PREFIX = "PERPETUAL";

    private static final String ORDER = "ORDER";

    private static final String LONG_S_ORDER = PERPETUAL_PREFIX + "_LONG_ORDER";

    private static final String SHORT_S_ORDER = PERPETUAL_PREFIX + "_SHORT_ORDER";

    private static final String H_ORDER = PERPETUAL_PREFIX + "_H_ORDER";

    /**
     * 缓存币对key
     */
    public static final String CURRENCY_PAIR = PERPETUAL_PREFIX + "_CURRENCY_PAIR";

    /**
     * kline查询数据库的开关，1表示查库，非1走缓存
     */
    public static final String KLINE_RELOAD_SWITCH = PERPETUAL_PREFIX + "_KLINE_RELOAD_SWITCH";

    /**
     * 缓存结算的key
     */
    private static final String SETTLEMENT_SIGN = PERPETUAL_PREFIX + "_SETTLEMENT_SIGN";

    /**
     * market data
     *
     * @param contractCode
     * @param period
     * @return
     */
    public static String getKlineKey(final String contractCode, final Integer period) {
        return PERPETUAL_PREFIX + "_" + "MARKET_" + contractCode.toUpperCase() + "_" + period;
    }

    /**
     * 深度key
     */
    public static String getDepthKey(final String contractCode) {
        return PERPETUAL_PREFIX + "_DEPTH_" + contractCode.toUpperCase();
    }

    /**
     * 深度买一卖一价
     *
     * @param contractCode
     * @return
     */
    public static String getFirstDepthData(final String contractCode) {
        return PERPETUAL_PREFIX + "_FIRST_DEPTH_" + contractCode.toUpperCase();
    }

    /**
     * 最新成交key
     */
    public static String getDealKey(final String contractCode) {
        return PERPETUAL_PREFIX + "_DEAL_" + contractCode.toUpperCase();
    }

    /**
     * ticker key
     */
    public static String getTickerKey(final String contractCode) {
        return PERPETUAL_PREFIX + "_TICKER_" + contractCode.toUpperCase();
    }

    /**
     * 溢价指数 key
     */
    public static String getPremiumIndexKEY(final String contractCode) {
        return PERPETUAL_PREFIX + "_PREMIUM_INDEX_" + contractCode.toUpperCase();
    }

    /**
     * 资金费率
     */
    public static String getCapitalRateKey(final String contractCode) {
        return PERPETUAL_PREFIX + "_CAPITAL_RATE_" + contractCode.toUpperCase();
    }

    /**
     * 资金费率
     */
    public static String getReasonableMarkPriceKey(final String contractCode) {
        return PERPETUAL_PREFIX + "_CAPITAL_Mark_PRICE_" + contractCode.toUpperCase();
    }

    /**
     * 扫描账单
     *
     * @return
     */
    public static String getScanUserBillKey() {
        return PERPETUAL_PREFIX + "_SCAN_USER_BILL";
    }

    public static String getMarketPriceKey() {
        return PERPETUAL_PREFIX + "_Mark_PRICE";
    }

    /**
     * 用户最大下单数
     *
     * @param contractCode
     * @param userId
     * @param brokerId
     * @return
     */
    public static String getUserOrderNumKey(final String contractCode, final Long userId,
                                            final Integer brokerId) {
        return PERPETUAL_PREFIX + "_ORDER_" + contractCode.toUpperCase() + "_" + userId + "_"
                + brokerId;

    }

    /**
     * 获取指数价格
     *
     * @param currencyCode
     * @return
     */
    public static String getPerpetualIndexPriceKey(final String currencyCode) {
        return PERPETUAL_PREFIX + "_INDEX_" + currencyCode.toUpperCase();
    }

    /**
     * 获取用户排名
     *
     * @param contractCode
     * @return
     */
    public static String getLongUserRankKey(final String contractCode) {
        return PERPETUAL_PREFIX + "_L_USER_RANK_" + contractCode.toUpperCase();
    }

    public static String getShortUserRankKey(final String contractCode) {
        return PERPETUAL_PREFIX + "_S_USER_RANK_" + contractCode.toUpperCase();
    }

    public static String getPushChannel(final String contractCode) {
        return PERPETUAL_PREFIX + "_" + contractCode.toUpperCase();
    }
}
