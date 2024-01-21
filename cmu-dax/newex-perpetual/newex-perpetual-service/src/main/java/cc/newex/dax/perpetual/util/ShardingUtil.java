package cc.newex.dax.perpetual.util;

import cc.newex.commons.mybatis.sharding.ShardTable;
import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.domain.Contract;

/**
 * 分表工具类
 *
 * @author xionghui
 * @date 2018/11/01
 */
public class ShardingUtil {

  public static ShardTable buildContractShardTable(final Contract contract, final String prefix) {
    return ShardTable.builder()
        .name(new StringBuilder(prefix).append("_").append(contract.getBase().toLowerCase())
            .append("_").append(contract.getQuote().toLowerCase()).toString())
        .build();
  }

  public static ShardTable buildContractOrderShardTable(final Contract contract) {
    return buildContractShardTable(contract, PerpetualConstants.ORDER_SHARDING_PREFIX);
  }

  public static ShardTable buildContractOrderFinishShardTable(final Contract contract) {
    return buildContractShardTable(contract, PerpetualConstants.ORDER_FINISH_SHARDING_PREFIX);
  }

  public static ShardTable buildContractOrderHistoryShardTable(final Contract contract) {
    return buildContractShardTable(contract, PerpetualConstants.ORDER_HISTORY_SHARDING_PREFIX);
  }

  public static ShardTable buildContractPendingShardTable(final Contract contract) {
    return buildContractShardTable(contract, PerpetualConstants.PENDING_SHARDING_PREFIX);
  }

  public static ShardTable buildContractMarketDataShardTable(final Contract contract) {
    return buildContractShardTable(contract, PerpetualConstants.MARKETDATA_PREFIX);
  }
}
