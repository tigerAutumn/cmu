package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.OrderConditionExample;
import cc.newex.dax.perpetual.domain.OrderCondition;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 计划委托订单表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-06 20:39:03
 */
@Repository
public interface OrderConditionRepository
    extends CrudRepository<OrderCondition, OrderConditionExample, Long> {
  /**
   * 加锁查询
   *
   * @param orderId
   */
  OrderCondition selectForUpdate(@Param("orderId") Long orderId);

  /**
   * 批量锁
   *
   * @param orderIds
   * @return
   */
  List<OrderCondition> batchSelectForUpdate(@Param("orderIds") List<Long> orderIds);
}
