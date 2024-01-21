package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.LatestTickerExample;
import cc.newex.dax.perpetual.domain.LatestTicker;
import org.springframework.stereotype.Repository;

/**
 * 最新报价 数据访问类
 *
 * @author newex-team
 * @date 2018-11-16 13:24:53
 */
@Repository
public interface LatestTickerRepository extends CrudRepository<LatestTicker, LatestTickerExample, Long> {
}