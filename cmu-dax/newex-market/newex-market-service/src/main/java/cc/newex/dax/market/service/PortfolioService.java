package cc.newex.dax.market.service;

import cc.newex.dax.market.domain.CoinConfig;

/**
 * Created by wj on 2018/7/24.
 */
public interface PortfolioService {
    //添加币种
    void createCoinConfig(CoinConfig coinConfig);

    //初始价格
    void initialPrice(CoinConfig coinConfig);

    //生成marketIndex
    void createMarketIndex(CoinConfig coinConfig);
}
