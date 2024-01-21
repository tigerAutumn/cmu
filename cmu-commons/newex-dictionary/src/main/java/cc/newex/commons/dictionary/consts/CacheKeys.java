package cc.newex.commons.dictionary.consts;

/**
 * @author newex-team
 * @date 2017/12/09
 */
public class CacheKeys {
    public static final String PRICE_ALERT_PREFIX = "PRICELERT";
    /**
     * 分隔符
     */
    public static final char DELIMITER = '_';
    /**
     * 行情key前缀
     */
    public static final String MARKET_PREFIX = "MARKET";
    /**
     * 深度key前缀
     */
    public static final String DEPTH_PREFIX = "DEPTH";
    /**
     * 成交key前缀
     */
    public static final String DEAL_PREFIX = "DEAL";
    /**
     * k线key前缀
     */
    public static final String KLINE_PREFIX = "KLINE";
    /**
     *现货
     */
    public static final String TICKER_PREFIX = "TICKER";
    public static final String CURRENCY_PAIR_PREFIX = "CURRENCYPAIR";
    public static final String MERGE_TYPE_PREFIX = "MEAGETYPE";
    public static final String CURRENCY_PREFIX = "CURRENCY";
    public static final String FEATURECOINKEY = "furureCoinIdInUsing";
    /**
     * 各网站行情
     */
    public static final String MARKET_ALLTICKER_KEY = "ALLTICKER";
    public static final String LATEST_ALL_TICKER_KEY_NEW = "latestallTickerNew";
    public static final String MARKET_ALL_TICKER_KEY = "MarketAlltickerKey";
    public static final String FETCHING_PATH_ALL_KEY = "fechingAllDataPath";
    public static final String FETCHING_PATH_KEY = "fechingDataPath_";
    /**
     * 存放所有币价列表。
     */
    public static final String MARKET_LATEST_TICKER_ALL = "MARKET_LATEST_TICKER_LIST";
    /**
     * 存放指定marketFrom的币价数据。
     */
    public static final String MARKET_LATEST_TICKER_MARKETFROM = "MARKET_LATEST_TICKER_";
    public static final String BLACK_CURRENCY_ALL = "black_currency_all";

    /**
     * spot 雪花算法 存放 ip 地址
     */
    public static final String CCEX_ID_GENERATOR_ADDRESS = "CCEX_ID_GENERATOR_ADDRESS";
    /**
     * spot 雪花算法 存放 ip 映射的 work id
     */
    public static final String CCEX_ID_GENERATOR_WORKER_ID = "CCEX_ID_GENERATOR_WORKER_ID";
    /**
     * 合约 雪花算法 存放 ip 地址
     */
    public static final String PERPETUAL_ID_GENERATOR_ADDRESS = "PERPETUAL_ID_GENERATOR_ADDRESS";
    /**
     * 合约 雪花算法 存放 ip 映射的 work id
     */
    public static final String PERPETUAL_ID_GENERATOR_WORKER_ID = "PERPETUAL_ID_GENERATOR_WORKER_ID";
    /**
     * 存放券商与ProductID的关系
     */
    public static final String CURRENCY_PAIR_BROKER_RELATION_PRODUCTIDS = "CURRENCY_PAIR_BROKER_RELATION_PRODUCTIDS";

    /**
     * 存放ProductID与券商的关系
     */
    public static final String CURRENCY_PAIR_BROKER_RELATION_BROKERIDS = "CURRENCY_PAIR_BROKER_RELATION_BROKERIDS";

    /**
     * 获取深度缓存key
     *
     * @param symbol currencypair.symbol
     * @return
     */
    public static String getDepthKey(final String symbol) {
        return DEPTH_PREFIX + DELIMITER + symbol;
    }

    /**
     * 获取成交缓存key
     *
     * @param symbol currencypair.symbol
     * @return
     */
    public static String getDealKey(final String symbol) {
        return DEAL_PREFIX + DELIMITER + symbol;
    }

    /**
     * 获取币值key
     *
     * @return
     */
    public static String getCurrencyPairKey() {
        return CURRENCY_PAIR_PREFIX;
    }

    /**
     * 返回ticker
     *
     * @param marketFrom
     * @return
     */
    public static String getTickerPrefix(final int marketFrom) {
        return TICKER_PREFIX + DELIMITER + marketFrom;
    }

    /**
     * @param marketFrom
     * @param period
     * @return
     */
    public static String getKlinePrefix(final Integer marketFrom, final byte period) {
        return MARKET_PREFIX + DELIMITER + marketFrom + DELIMITER + period;
    }

    /**
     * 根据ProductId 获取信息
     *
     * @param symbol
     * @return
     */
    public static String getMergeTypePrefix(final String symbol) {
        return MERGE_TYPE_PREFIX + DELIMITER + symbol;
    }

    /**
     * 获取currency 的key
     *
     * @return
     */
    public static String getCurrencyKey() {
        return CURRENCY_PREFIX;
    }

    /**
     * 价格预警 key
     *
     * @return
     */
    public static String getPriceAlertPrefix(final String key) {
        return PRICE_ALERT_PREFIX + DELIMITER + key;
    }

    public static String getAllTickerKey() {
        return MARKET_ALLTICKER_KEY;
    }

    public static String getLatestAllTickerKeyNew() {
        return LATEST_ALL_TICKER_KEY_NEW;
    }

    public static String getMarketAlltickerKey() {
        return MARKET_ALL_TICKER_KEY;
    }

    public static String getFeaturecoinkey() {
        return FEATURECOINKEY;
    }

    public static String getFechingDataPath(final String marketFrom) {
        return FETCHING_PATH_KEY + marketFrom;
    }

    public static String getFechingDataALLPath() {
        return FETCHING_PATH_ALL_KEY;
    }

    /**
     * 获取存放所有币价列表的Key。
     *
     * @return
     */
    public static String getKey4LatestTickerAll() {
        return MARKET_LATEST_TICKER_ALL;
    }

    /**
     * 获取存放指定市场币价的Key。
     *
     * @return
     */
    public static String getKey4LatestTickerMarketfrom(final int marketFrom) {
        return MARKET_LATEST_TICKER_MARKETFROM + marketFrom;
    }

    public static String getBlackCurrencyAll() {
        return BLACK_CURRENCY_ALL;
    }
}
