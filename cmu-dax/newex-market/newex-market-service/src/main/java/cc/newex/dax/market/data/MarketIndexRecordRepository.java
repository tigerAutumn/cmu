package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.dax.market.criteria.MarketIndexRecordExample;
import cc.newex.dax.market.domain.MarketIndexRecord;
import org.springframework.stereotype.Repository;

/**
 *  数据访问类
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface MarketIndexRecordRepository
    extends CrudRepository<MarketIndexRecord, MarketIndexRecordExample, Long> {
}