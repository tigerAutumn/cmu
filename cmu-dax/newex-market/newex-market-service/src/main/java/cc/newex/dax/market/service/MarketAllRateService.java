package cc.newex.dax.market.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.market.criteria.MarketAllRateExample;
import cc.newex.dax.market.domain.MarketAllRate;

import java.math.BigDecimal;
import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface MarketAllRateService
        extends CrudService<MarketAllRate, MarketAllRateExample, Long> {

    /**
     * 根据名称更新市场汇率数据。
     *
     * @param marketRate
     * @return
     */
    ResponseResult updateMarketRate(MarketAllRate marketRate);

    MarketAllRate getRateByName(String name);

    MarketAllRate getRateByNameOrderBy(String name);

    /**
     * @param name 跟据汇率名称存到redis(最新10条)
     */
    void putRateRedis(String name);

    BigDecimal selectPairAcg2Week(String name);

    /**
     * 查询平台汇率
     *
     * @param name
     * @return
     */
    List<MarketAllRate> getMarketAllRateTwoWeekList(String name);

    /**
     * 查询两周汇率
     *
     * @param name
     * @return
     */
    List<MarketAllRate> getMarketAllRateTwoWeekListUSD_CNY(String name);
}
