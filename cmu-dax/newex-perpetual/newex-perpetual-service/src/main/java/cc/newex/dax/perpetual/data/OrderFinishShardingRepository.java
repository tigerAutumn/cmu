package cc.newex.dax.perpetual.data;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.OrderFinishShardingExample;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderFinish;

/**
 * 定单完成总表 数据访问类
 *
 * @author newex-team
 * @date 2018-10-30 18:49:51
 */
@Repository
public interface OrderFinishShardingRepository
    extends CrudRepository<OrderFinish, OrderFinishShardingExample, Long> {

  int createOrderFinishIfNotExists(@Param("shardTable") ShardTable shardTable);

  int batchInsertByOrder(@Param("records") List<Order> orderList,
      @Param("shardTable") ShardTable shardTable);
}
