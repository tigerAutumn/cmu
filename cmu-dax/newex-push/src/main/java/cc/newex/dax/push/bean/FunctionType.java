package cc.newex.dax.push.bean;

/**
 * 类型
 *
 * @author xionghui
 * @date 2018/09/14
 */
public final class FunctionType {

  /**
   * k线
   */
  public static final String CANDLES = "candles";

  /**
   * 深度
   */
  public static final String DEPTH = "depth";

  /**
   * 行情
   */
  public static final String TICKER = "ticker";

  /**
   * spot行情
   */
  public static final String TICKERS = "tickers";

  /**
   * 最新成交
   */
  public static final String FILLS = "fills";

  /**
   * 份额
   */
  public static final String SHARE = "share";

  /**
   * 币对
   */
  public static final String CURRENCY_PAIR = "currency_pairs";

  /**
   * 资产
   */
  public static final String ASSETS = "assets";

  /**
   * 订单
   */
  public static final String ORDERS = "orders";

  /**
   * 条件订单，合约使用
   */
  public static final String CONDITION_ORDERS = "condition_orders";

  /**
   * 合约持仓
   */
  public static String POSITION = "position";

  /**
   * 合约指数价格,标记价格和资金费率
   */
  public static String FUND_RATE = "fund_rate";

  /**
   * 所有合约指数价格,标记价格和资金费率
   */
  public static String FUND_RATES = "fund_rates";

  /**
   * 五分位排名
   */
  public static String RANK = "rank";

  /**
   * 总的用户持仓
   */
  public static String TOTAL_POSITION = "total_position";
}
