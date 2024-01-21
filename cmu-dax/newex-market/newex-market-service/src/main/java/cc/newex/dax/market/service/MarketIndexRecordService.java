package cc.newex.dax.market.service;

import cc.newex.dax.market.domain.CoinConfig;
import cc.newex.dax.market.domain.MarketIndexRecord;

import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface MarketIndexRecordService {
    long insertMarketIndexRecord(MarketIndexRecord marketIndexRecord, CoinConfig coinConfig);

    int countMarketIndexRecord(int marketFrom, int invalid, CoinConfig coinConfig);

    void deleteMarketIndexRecordBefore24Hour(CoinConfig coinConfig);

    long batchInsertMarketIndexRecord(List<MarketIndexRecord> records, CoinConfig coinConfig);
}
