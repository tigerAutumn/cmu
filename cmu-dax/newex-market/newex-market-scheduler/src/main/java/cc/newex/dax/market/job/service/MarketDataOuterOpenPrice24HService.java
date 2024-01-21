package cc.newex.dax.market.job.service;

import cc.newex.dax.market.domain.CoinConfig;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface MarketDataOuterOpenPrice24HService {

    void openPriceBefore24H(CoinConfig coinConfig);
}
