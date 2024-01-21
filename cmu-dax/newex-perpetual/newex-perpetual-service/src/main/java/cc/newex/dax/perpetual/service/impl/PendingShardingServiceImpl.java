package cc.newex.dax.perpetual.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.commons.mybatis.sharding.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.PendingShardingExample;
import cc.newex.dax.perpetual.data.PendingShardingRepository;
import cc.newex.dax.perpetual.domain.Pending;
import cc.newex.dax.perpetual.service.PendingShardingService;

/**
 * 定单交易流水表 服务实现
 *
 * @author newex-team
 * @date 2018-10-30 18:50:04
 */
@Service
public class PendingShardingServiceImpl
    extends AbstractCrudService<PendingShardingRepository, Pending, PendingShardingExample, Long>
    implements PendingShardingService {
  @Autowired
  private PendingShardingRepository pendingRepository;

  @Override
  protected PendingShardingExample getPageExample(final String fieldName, final String keyword) {
    final PendingShardingExample example = new PendingShardingExample();
    example.createCriteria().andFieldLike(fieldName, keyword);
    return example;
  }

  @Override
  public int createPendingIfNotExists(final ShardTable shardTable) {
    return this.pendingRepository.createPendingIfNotExists(shardTable);
  }

  @Override
  public List<Pending> getRecentDeal(final int size, final String contractCode,
      final ShardTable shardTable) {
    return this.pendingRepository.selectRecentDeal(size, contractCode, shardTable);
  }

  @Override
  public void batchInsertWithId(final List<Pending> pendingList, final ShardTable shardTable) {
    this.pendingRepository.batchInsertWithId(pendingList, shardTable);
  }
}
