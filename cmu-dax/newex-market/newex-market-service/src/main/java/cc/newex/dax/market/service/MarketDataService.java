package cc.newex.dax.market.service;

import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.MarketData;

import java.util.Date;
import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface MarketDataService {

    /**
     * 取得10分钟内的K线数据
     */
    List<MarketData> getLastMarketDataByType(CoinConfig coinConfig, int type, Date date);


    /**
     * 取得对应K线数据
     */
    List<MarketData> getLastListByTypeAndLimit(CoinConfig coinConfig, int type, int limit);

    /**
     * 取得对应K线数据
     */
    List<MarketData> getListByType(CoinConfig coinConfig, int type, int pageSize);

    /**
     * 取得对应K线数据
     */
    MarketData getLastListByType(CoinConfig coinConfigx, int type);

    int updateMarketDataById(CoinConfig coinConfig, MarketData marketData);

    long updateMarketData(CoinConfig coinConfig, MarketData marketData, double open, double high, double low, double close, double volume);

    long updateMarketData(CoinConfig coinConfig, MarketData marketData);

    int insertMarketData(CoinConfig coinConfig, int marketFrom, double open, double high, double low, double close, double volume, int type, Date createdDate);

    int insertMarketDataWithId(CoinConfig coinConfig, MarketData lastTicker);

    int insertMarketData(CoinConfig coinConfig, MarketData lastTicker);

    /**
     * 取得24小时内开盘价
     */
    MarketData getTodayOpenPrice(CoinConfig coinConfig, Date day);

    List<MarketData> getByTypeAndDate(CoinConfig coinConfig, int type, Date date, int limit);

    void deleteMarketDataLessThanId(Long id, CoinConfig coinConfig);

    int deleteMarketData(CoinConfig coinConfig, Date date, int type);

    /**
     * 获取K线数据
     * String[][时间],[开],[高],[低],[收],[交易量]|[合约币]
     */
    List<String[]> getMarketDataFromCacheArray(Integer marketFrom, int type, int limit, long unixTime);

    /**
     * 初始化新币marketData数据
     */
    void initMarketData(CoinConfig coinConfig);
}
