package cc.newex.dax.perpetual.service;

import java.util.List;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.CrudService;
import cc.newex.dax.perpetual.criteria.PendingShardingExample;
import cc.newex.dax.perpetual.domain.Pending;

/**
 * 定单交易流水表 服务接口
 *
 * @author newex-team
 * @date 2018-10-30 18:50:04
 */
public interface PendingShardingService extends CrudService<Pending, PendingShardingExample, Long> {

  /**
   * 建表
   */
  int createPendingIfNotExists(final ShardTable shardTable);

  /**
   * 获得最新成交
   */
  List<Pending> getRecentDeal(final int size, final String contractCode,
      final ShardTable shardTable);

  /**
   * 批量插入成交明细
   */
  void batchInsertWithId(final List<Pending> pendingList, final ShardTable shardTable);
}
