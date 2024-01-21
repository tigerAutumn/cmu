package cc.newex.dax.market.service;

import cc.newex.dax.market.domain.CoinConfig;

/**
 * @author newex-team
 * @date 2018/3/12
 */
public interface CreatorTableService {
    /**
     * 生成MarketData
     *
     * @param coinConfig
     */
    void checkMarketDataOrCreate(CoinConfig coinConfig);

    /**
     * 生成MarketIndexRecord
     *
     * @param coinConfig
     */
    void checkMarketIndexRecordOrCreate(CoinConfig coinConfig);

}
