package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.dax.perpetual.criteria.OrderHistoryShardingExample;
import cc.newex.dax.perpetual.domain.OrderHistory;

/**
 * 订单归档表 服务接口
 *
 * @author newex-team
 * @date 2018-10-30 18:49:55
 */
public interface OrderHistoryShardingService
    extends CrudService<OrderHistory, OrderHistoryShardingExample, Long> {

  int createOrderHistoryIfNotExists(final ShardTable shardTable);
}
