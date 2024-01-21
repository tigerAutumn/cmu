package cc.newex.dax.market.job.service;

import cc.newex.dax.market.domain.CoinConfig;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface MarketIndexCacheBuildAndPushService {
    /**
     * 构建指数行情
     * 取当日最低价、当日最高价、当日开盘价、当日最新价
     * 构建出行情、并且通过最新价、开盘价 算出涨跌幅
     *
     * @param coinConfig
     */
    void marketIndexCacheBuildAndPush(CoinConfig coinConfig);
}
