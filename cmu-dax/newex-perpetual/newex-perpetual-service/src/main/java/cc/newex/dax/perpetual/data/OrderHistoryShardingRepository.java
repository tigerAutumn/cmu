package cc.newex.dax.perpetual.data;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.OrderHistoryShardingExample;
import cc.newex.dax.perpetual.domain.OrderHistory;

/**
 * 订单归档表 数据访问类
 *
 * @author newex-team
 * @date 2018-10-30 18:49:55
 */
@Repository
public interface OrderHistoryShardingRepository
    extends CrudRepository<OrderHistory, OrderHistoryShardingExample, Long> {

  int createOrderHistoryIfNotExists(@Param("shardTable") ShardTable shardTable);
}
