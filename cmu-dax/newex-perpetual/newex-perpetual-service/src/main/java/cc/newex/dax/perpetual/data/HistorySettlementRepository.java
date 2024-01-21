package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.HistorySettlementExample;
import cc.newex.dax.perpetual.domain.HistorySettlement;
import org.springframework.stereotype.Repository;

/**
 * 清算历史记录表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-10 15:01:30
 */
@Repository
public interface HistorySettlementRepository extends CrudRepository<HistorySettlement, HistorySettlementExample, Long> {
}