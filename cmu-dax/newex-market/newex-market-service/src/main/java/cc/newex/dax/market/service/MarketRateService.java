package cc.newex.dax.market.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.market.criteria.MarketRateExample;
import cc.newex.dax.market.domain.MarketRate;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */
public interface MarketRateService
        extends CrudService<MarketRate, MarketRateExample, Long> {

    MarketRate getRateByName(String name);

    List getLastFourWeekUsdCnyRate_cache();

    void putRateCnyCache(double twoWeekRateAvg);

}
