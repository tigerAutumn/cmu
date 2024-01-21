package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.HistoryExplosionExpandExample;
import cc.newex.dax.perpetual.domain.HistoryExplosionExpand;
import org.springframework.stereotype.Repository;

/**
 * 爆仓流水扩展表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-02 11:33:46
 */
@Repository
public interface HistoryExplosionExpandRepository extends CrudRepository<HistoryExplosionExpand, HistoryExplosionExpandExample, Long> {
}