package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.MarkPriceExample;
import cc.newex.dax.perpetual.domain.MarkPrice;
import org.springframework.stereotype.Repository;

/**
 *
 * @author newex-team
 * @date 2019-01-07 21:55:01
 */
@Repository
public interface MarkPriceRepository extends CrudRepository<MarkPrice, MarkPriceExample, Long> {
}