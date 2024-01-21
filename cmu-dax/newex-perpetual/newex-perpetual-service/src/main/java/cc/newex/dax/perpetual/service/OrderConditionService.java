package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.OrderConditionExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.OrderCondition;

import java.util.List;

/**
 * 计划委托订单表 服务接口
 *
 * @author newex-team
 * @date 2018-11-06 20:39:03
 */
public interface OrderConditionService
    extends CrudService<OrderCondition, OrderConditionExample, Long> {

  /**
   * 检查条件
   */
  List<OrderCondition> checkCondition();

  List<OrderCondition> list(Long userId, Integer brokerId, String contractCode);

  List<OrderCondition> listAll(String contractCode);

  /**
   * 创建订单
   */
  void dealCondition(OrderCondition orderCondition);

  void cancelOrder(Long userId, Integer brokerId, Long id, final String contractCode);

  /**
   * 批量取消订单
   */
  void cancelOrder(List<OrderCondition> list, Contract contract);
}
