package cc.newex.dax.market.spider.common.config;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public final class UrlConfig {

    /**
     * ticker url
     */
    public static final String TICKER_URL = "/inner/v1/market/data/ticker/receive";

    /**
     * rate url
     */
    public static final String RATE_URL = "/inner/v1/market/data/rate/receive";

    /**
     * pool url
     */
    public static final String POOL_URL = "/inner/v1/market/data/pool/receive";

    /**
     * latest path to redis url
     */
    public static final String PATH_REDIS_URL = "/inner/v1/market/data/pathToRedis";

    /**
     * ticker to redis  url
     */
    public static final String TICKER_REDIS_URL = "/inner/v1/market/data/tickerToRedis";

    /**
     * rate to redis  url
     */
    public static final String RATE_RESDIS_URL = "/inner/v1/market/data/rateToRedis";

    /**
     * pool to redis  url
     */
    public static final String POOL_REDIS_URL = "/inner/v1/market/data/orePoolToRedis";

    /**
     * get path to redis  url
     */
    public static final String ALL_PATH_URL = "/inner/v1/market/data/getAllPath";

    /**
     * get rate data by name
     */
    public static final String GET_RATE_URL = "/inner/v1/market/rate";

}
