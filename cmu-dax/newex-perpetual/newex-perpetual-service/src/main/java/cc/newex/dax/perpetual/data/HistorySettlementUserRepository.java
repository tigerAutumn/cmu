package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.HistorySettlementUserExample;
import cc.newex.dax.perpetual.domain.HistorySettlementUser;
import org.springframework.stereotype.Repository;

/**
 * 清算历史记录表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-28 16:10:25
 */
@Repository
public interface HistorySettlementUserRepository extends CrudRepository<HistorySettlementUser, HistorySettlementUserExample, Long> {
}