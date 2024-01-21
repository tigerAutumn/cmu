package cc.newex.dax.market.job.service;

import cc.newex.commons.dictionary.enums.KlineEnum;
import cc.newex.dax.market.domain.CoinConfig;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface MarketDataKlineService {
    /**
     * 生成1min kline
     *
     * @param coinConfig
     * @return
     */
    boolean generateOuterIndex1Minute(CoinConfig coinConfig);


    /**
     * 生成多min kline
     * @param coinConfig
     * @param klineEnum
     */
    void marketDataOuterMerge(CoinConfig coinConfig, final KlineEnum klineEnum);
}
