package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.market.criteria.MarketAllRateExample;
import cc.newex.dax.market.domain.MarketAllRate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface MarketAllRateRepository
    extends CrudRepository<MarketAllRate, MarketAllRateExample, Long> {

    MarketAllRate getRateByName(String name);

    MarketAllRate getRateByNameOrderBy(String name);

    List<MarketAllRate> selectAll();

    List<MarketAllRate> getMarketAllRateTwoWeekList(String name);

    List<MarketAllRate> getMarketAllRateTwoWeekListUSD_CNY(String name);

    BigDecimal selectPairAvg(@Param(value = "name") String name, @Param(value = "date") Date date);
}
