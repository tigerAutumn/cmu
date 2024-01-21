package cc.newex.dax.market.job.service;

import cc.newex.dax.market.domain.CoinConfig;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface MarketIndexGeneratorService {

    /**
     * 入库,写缓存
     */
    void generateMarketIndexAndCached(CoinConfig coinConfig);

    boolean generatePerpetualMarketIndexAndCached(CoinConfig coinConfig);

}
