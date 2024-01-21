package cc.newex.dax.perpetual.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.OrderHistoryShardingExample;
import cc.newex.dax.perpetual.data.OrderHistoryShardingRepository;
import cc.newex.dax.perpetual.domain.OrderHistory;
import cc.newex.dax.perpetual.service.OrderHistoryShardingService;
import lombok.extern.slf4j.Slf4j;

/**
 * 订单归档表 服务实现
 *
 * @author newex-team
 * @date 2018-10-30 18:49:55
 */
@Slf4j
@Service
public class OrderHistoryShardingServiceImpl extends
    AbstractCrudService<OrderHistoryShardingRepository, OrderHistory, OrderHistoryShardingExample, Long>
    implements OrderHistoryShardingService {
  @Autowired
  private OrderHistoryShardingRepository orderHistoryRepository;

  @Override
  protected OrderHistoryShardingExample getPageExample(final String fieldName,
      final String keyword) {
    final OrderHistoryShardingExample example = new OrderHistoryShardingExample();
    example.createCriteria().andFieldLike(fieldName, keyword);
    return example;
  }

  @Override
  public int createOrderHistoryIfNotExists(final ShardTable shardTable) {
    return this.orderHistoryRepository.createOrderHistoryIfNotExists(shardTable);
  }
}
