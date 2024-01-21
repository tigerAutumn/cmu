package cc.newex.dax.perpetual.data;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.PendingShardingExample;
import cc.newex.dax.perpetual.domain.Pending;

/**
 * 定单交易流水表 数据访问类
 *
 * @author newex-team
 * @date 2018-10-30 18:50:04
 */
@Repository
public interface PendingShardingRepository
    extends CrudRepository<Pending, PendingShardingExample, Long> {

  /**
   * 建表
   */
  int createPendingIfNotExists(@Param("shardTable") ShardTable shardTable);

  /**
   * 查询最新成交
   */
  List<Pending> selectRecentDeal(@Param("size") int size,
      @Param("contractCode") String contractCode, @Param("shardTable") ShardTable shardTable);

  /**
   * 批量插入成交明细
   */
  int batchInsertWithId(@Param("list") List<Pending> records,
      @Param("shardTable") ShardTable shardTable);
}
