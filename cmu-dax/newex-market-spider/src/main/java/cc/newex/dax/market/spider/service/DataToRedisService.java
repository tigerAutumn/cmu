package cc.newex.dax.market.spider.service;

import cc.newex.dax.market.spider.domain.FetchingDataPath;

import java.util.List;

/**
 * @author newex-team
 * @date 2018/03/18
 **/
public interface DataToRedisService {
    /**
     * 路径存到缓存
     */
    void pathToRedis();

    /**
     * ticker放到redis
     */
    void tickerToRedis();

    /**
     * rate放到redis
     */
    void rateToRedis();

    /**
     * 矿池数据放到redis
     */
    void orePoolToRedis();

    /**
     * 获取采集地址
     */
    List<FetchingDataPath> getAllPath();
}
