package cc.newex.dax.perpetual.common.constant;

import cc.newex.dax.perpetual.domain.Contract;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @date 2018/9/25
 */
public class PerpetualConstants {
  /**
   * 杠杆进度
   */
  public static int LEVER_SCALE = 0;
  /**
   * 默认的券商id
   */
  public static int DEFAULT_BROKERID = 1;
  /**
   * BigDecimal默认精度
   */
  public static final int SCALE = 16;
  /**
   * 系统收取手续费账户
   */
  public static final long FEE_COUNT = 9L;
  /**
   * 测试币发币账户
   */
  public static final long MONEY_COUNT = 8L;

  public static String PERPETUAL = "perpetual";

  public static String LONG_ORDER = "long_order";
  public static String SHORT_ORDER = "short_order";
  /**
   * 最大价格,一亿
   */
  public static final BigDecimal MAX_PRICE = new BigDecimal(100_000_000);

  /**
   * 获取kline的分钟数
   */
  public static String FETCH_KLINE_MINUTE = PerpetualConstants.PERPETUAL + "_fetch_kline_minute";
  /**
   * 券商ID
   */
  public static final String CURRENT_USER_BROKER_ID =
      PerpetualConstants.PERPETUAL + "_user_broker_id";
  /**
   * 风险限额
   */
  public static final String RISK_LIMITS = PerpetualConstants.PERPETUAL + "_risk_limit";
  /**
   * 市价:最小价格
   */
  public static final BigDecimal MIN_MARKET_PRICE = BigDecimal.ZERO;


  public final static int KLINE_SIZE = 2000;

  /**
   * 订单分表前缀
   */
  public final static String ORDER_SHARDING_PREFIX = "order";
  /**
   * 已完成订单分表前缀
   */
  public final static String ORDER_FINISH_SHARDING_PREFIX = "order_finish";
  /**
   * 订单历史分表前缀
   */
  public final static String ORDER_HISTORY_SHARDING_PREFIX = "order_history";
  /**
   * 成交明细分表前缀
   */
  public final static String PENDING_SHARDING_PREFIX = "pending";
  /**
   * 成交明细分表前缀
   */
  public final static String MARKETDATA_PREFIX = "market_data";

  // spot的biz
  public static final String PERPETUAL_BIZ = "PERPETUAL";

  /**
   * 构建channel name
   */
  public static String buildChannel(final Contract contract) {
    return PERPETUAL_BIZ.concat("_").concat(contract.getPairCode().toUpperCase());
  }
}
