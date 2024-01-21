package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.market.criteria.MarketIndexExample;
import cc.newex.dax.market.domain.MarketIndex;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface MarketIndexRepository
        extends CrudRepository<MarketIndex, MarketIndexExample, Long> {

    /**
     * 获取指数
     *
     * @param marketIndexExample
     * @return
     */
    List<Map<String, Object>> selectMarketIndexAvg(@Param("example") MarketIndexExample marketIndexExample);
}