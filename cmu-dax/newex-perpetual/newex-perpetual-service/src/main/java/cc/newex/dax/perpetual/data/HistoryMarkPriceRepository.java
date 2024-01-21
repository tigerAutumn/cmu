package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.HistoryMarkPriceExample;
import cc.newex.dax.perpetual.domain.HistoryMarkPrice;
import org.springframework.stereotype.Repository;

/**
 * 溢价指数流水表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-15 19:24:22
 */
@Repository
public interface HistoryMarkPriceRepository extends CrudRepository<HistoryMarkPrice, HistoryMarkPriceExample, Long> {
}