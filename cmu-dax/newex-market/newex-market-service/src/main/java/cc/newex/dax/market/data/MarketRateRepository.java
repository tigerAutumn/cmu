package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.market.criteria.MarketRateExample;
import cc.newex.dax.market.domain.MarketRate;
import org.springframework.stereotype.Repository;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface MarketRateRepository
        extends CrudRepository<MarketRate, MarketRateExample, Long> {

    /**
     * 获取汇率名称
     *
     * @param name
     * @return
     */
    MarketRate getRateByName(String name);
}