package cc.newex.dax.asset.common.consts;

/**
 * @author newex-team
 * @data 2018/5/5
 */

public class RedisKeyCons {
    public final static String SPOT_ASSET_CURRENCY_KEY = "newex-asset:currency:spot";

    /**
     * 存储充值和提现数据的汇总结果，一小时更新一次
     */
    public final static String ASSET_DATA_KEY = "newex-asset:record:statistics";

    /**
     * 存储充值和提现数据的汇总结果时，缓存最后一次查询的id，下次直接从此id开始查询
     */
    public final static String LAST_GET_RECORD_ID = "newex-asset:record:last:id";

    /**
     * 对账时最后一条record
     */
    public final static String LAST_RECONCILIATION_RECORD_ID = "newex-asset:reconciliation:last:id";
    @Deprecated
    public final static String ASSET_RECORD_WITHDRAW_KEY_PRE = "newex-asset:record:withdraw";

    public final static String ASSET_RECORD_WITHDRAW_KEY_PRE_NEW = "newex-asset:record:withdraw:{0}:brokerId:{1}";
    public final static String ASSET_RECORD_WITHDRAW_UNCONFIRM_KEY = "newex-asset:record:unconfirmed";
    /**
     * 报警阈值设置，5分钟有3次报警，触发风控策略
     */
    public final static String ALERT_TIMES = "newex-asset:alert:times";

    public final static String ASSET_WITHDRAW_BTC_AMOUNT_LIMIT = "newex-asset:withdraw:limit:btc";

    //阈值 超过多少额度需要人工审核
    public final static String ASSET_WITHDRAW_BTC_AUDIT_AMOUNT_LIMIT = "newex-asset:withdraw:audit:limit:btc";

    public final static String ASSET_CURRENCY_ONE_STEP_FROZEN_PRE = "newex-asset:currency:frozen";


    public final static String NOTIFY_DEPOSIT_WITHDRAW_FAILED = "newex-asset:notify:deposit:withdraw:failed";

}
