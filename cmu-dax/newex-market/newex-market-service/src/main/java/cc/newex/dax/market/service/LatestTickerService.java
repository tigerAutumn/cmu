package cc.newex.dax.market.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.market.criteria.LatestTickerExample;
import cc.newex.dax.market.domain.LatestTicker;

import java.util.Date;
import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface LatestTickerService
        extends CrudService<LatestTicker, LatestTickerExample, Long> {

    /**
     * 根据marketFrom字段获取对应的记录并更新。
     *
     * @param ticker
     */
    ResponseResult updateTickerByMarketFrom(LatestTicker ticker, int marketFrom);

    List<LatestTicker> getAllTickerToRedis();


    void putTickerRedis();


    /**
     * 根据marketFrom 查询
     *
     * @param marketFrom
     * @return
     */
    List<LatestTicker> getLatestTickerFromMarketFromArray(int[] marketFrom);


    /***
     * 获取指定时间之前的latestTicker
     */
    List<LatestTicker> getValidTickerLessThanTime(Date dateInMinuteAgo);

}
