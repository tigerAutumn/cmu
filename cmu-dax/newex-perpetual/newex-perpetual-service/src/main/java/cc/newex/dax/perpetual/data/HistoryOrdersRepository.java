package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.HistoryOrdersExample;
import cc.newex.dax.perpetual.domain.HistoryOrders;
import org.springframework.stereotype.Repository;

/**
 * 订单归档表 数据访问类
 *
 * @author newex-team
 * @date 2018-09-10 15:01:23
 */
@Repository
public interface HistoryOrdersRepository extends CrudRepository<HistoryOrders, HistoryOrdersExample, Long> {
}