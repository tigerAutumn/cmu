package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.OrderShardingExample;
import cc.newex.dax.perpetual.domain.Order;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * 订单表 数据访问类
 *
 * @author newex-team
 * @date 2018-10-30 18:49:48
 */
@Repository
public interface OrderShardingRepository extends CrudRepository<Order, OrderShardingExample, Long> {
    /**
     * 按照id和pairCode分页查询订单列表
     *
     * @param contractCode
     * @return
     */
    List<Order> selectByContractCode(@Param("contractCode") String contractCode, @Param("shardTable") ShardTable shardTable);

    /**
     * 建表
     *
     * @param shardTable
     * @return
     */
    int createOrderIfNotExists(@Param("shardTable") ShardTable shardTable);

    /**
     * 根据matchStatus查询订单
     *
     * @param contractCode 合约code
     * @param matchStatus  状态
     * @param size         查询的条数
     * @param shardTable   分表对象
     * @return 记录列表
     */
    List<Order> selectByMatchStatus(@Param("contractCode") String contractCode,
                                    @Param("matchStatus") int matchStatus,
                                    @Param("size") Integer size,
                                    @Param("shardTable") ShardTable shardTable);

    /**
     * 批量查询用户挂单
     *
     * @param contractCode
     * @param brokerId
     * @param userIds
     * @param shardTable
     * @return
     */
    List<Order> selectBatchOrder(@Param("contractCode") String contractCode,
                                 @Param("brokerId") Integer brokerId, @Param("set") Set<Long> userIds,
                                 @Param("shardTable") ShardTable shardTable);

    /**
     * 批量删除
     *
     * @param records
     * @param shardTable
     */
    void batchDelete(@Param("records") List<Order> records, @Param("shardTable") ShardTable shardTable);
}
