package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.dax.perpetual.criteria.OrderFinishShardingExample;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.Order;
import cc.newex.dax.perpetual.domain.OrderFinish;

import java.util.List;

/**
 * 定单完成总表 服务接口
 *
 * @author newex-team
 * @date 2018-10-30 18:49:51
 */
public interface OrderFinishShardingService
        extends CrudService<OrderFinish, OrderFinishShardingExample, Long> {

    static ShardTable getShardTable(final Contract contract) {
        return ShardTable.builder().prefix("order_finish_").name(contract.getBase() + "_" + contract.getQuote()).build();
    }

    /**
     * 如果表不存在则创建表
     *
     * @param shardTable
     * @return
     */
    int createOrderFinishIfNotExists(final ShardTable shardTable);

    /**
     * 按order批量插入
     *
     * @param orderList
     * @param shardTable
     * @return
     */
    int batchInsertByOrder(List<Order> orderList, ShardTable shardTable);

    ShardTable getOrderFinishShardTable(String contractCode);

    List<OrderFinish> queryOrderList(Long userId, Integer brokerId, String contractCode);
}
