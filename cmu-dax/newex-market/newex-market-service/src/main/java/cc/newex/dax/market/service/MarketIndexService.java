package cc.newex.dax.market.service;

import cc.newex.dax.market.domain.MarketIndex;

import java.util.Date;
import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface MarketIndexService {
    /**
     * 获取最新行情Map集合
     *
     * @return 返回当前行情
     */
    MarketIndex getMarketIndex(int symbol);

    /**
     * 取得近100条数据
     *
     * @param symbol
     * @param limit
     * @return
     */
    List<MarketIndex> getMarketIndexAndLimit(int symbol, int limit);

    long insertMarketIndex(MarketIndex marketIndex);

    MarketIndex getMarketIndexById(long id);

    double getIndexBefore24HourLow(int symbol, Date date);

    double getIndexBefore24HourHigh(int symbol, Date date);

    /**
     * 删除MarketIndex数据 保留最新的600条
     */
    void deleteMarketIndex(int symbol);

    long getMarketIndexStartId(int symbol, Date startDateStr);

    long getMarketIndexEndId(int symbol, Date endDateStr);

    double getMarketIndexHighPrice(long startRecordId, long endRecordId, int symbol);

    double getMarketIndexLowPrice(long startRecordId, long endRecordId, int symbol);

    double getMarketIndexFirstPrice(long startRecordId, long endRecordId, int symbol);

    double getMarketIndexLastPrice(long startRecordId, long endRecordId, int symbol);

    MarketIndex getLatestMarketIndex(int symbol, Date date);

    MarketIndex getLatestMarketIndex(int symbol);

    List<MarketIndex> getMarketIndexWithinDate(int symbol, Date date);

    double getMarketIndexAvg(Date date, int symbol);

    MarketIndex getTickerFromCache(String symbolName);
}

