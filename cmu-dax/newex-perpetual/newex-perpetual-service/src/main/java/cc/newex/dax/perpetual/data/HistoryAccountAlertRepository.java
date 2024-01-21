package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.HistoryAccountAlertExample;
import cc.newex.dax.perpetual.domain.HistoryAccountAlert;
import org.springframework.stereotype.Repository;

/**
 * 对账报警流水表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-10 15:00:53
 */
@Repository
public interface HistoryAccountAlertRepository extends CrudRepository<HistoryAccountAlert, HistoryAccountAlertExample, Long> {
}