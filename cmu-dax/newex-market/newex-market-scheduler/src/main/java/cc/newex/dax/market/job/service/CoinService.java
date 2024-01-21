package cc.newex.dax.market.job.service;

import cc.newex.dax.market.domain.CoinConfig;

import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public interface CoinService {

    List<CoinConfig> getCoinConfigListCache() throws ExecutionException;

    /**
     * 取得所有币种列表,本机缓存数据。只查一次DB
     *
     * @return
     */
    List<CoinConfig> getCoinConfigList();

    String getIndexCacheKey(CoinConfig coinConfig);


    String getLastIndexCacheKey(CoinConfig coinConfig);

    /***
     * 刷新缓存
     */
    void reloadCache();
}
