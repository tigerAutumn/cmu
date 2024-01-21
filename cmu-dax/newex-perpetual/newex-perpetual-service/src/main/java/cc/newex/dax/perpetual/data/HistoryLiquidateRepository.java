package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.HistoryLiquidateExample;
import cc.newex.dax.perpetual.domain.HistoryLiquidate;
import org.springframework.stereotype.Repository;

/**
 * 预爆仓备份表(不会被任务删除) 数据访问类
 *
 * @author newex-team
 * @date 2018-11-02 11:19:47
 */
@Repository
public interface HistoryLiquidateRepository extends CrudRepository<HistoryLiquidate, HistoryLiquidateExample, Long> {
}