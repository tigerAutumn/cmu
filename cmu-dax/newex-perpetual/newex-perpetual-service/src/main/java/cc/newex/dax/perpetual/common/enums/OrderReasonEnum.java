package cc.newex.dax.perpetual.common.enums;

import lombok.AllArgsConstructor;

/**
 * @author newex-team
 * @date 2019/1/3
 */
@AllArgsConstructor
public enum OrderReasonEnum {
    NORMAL(0), // 正常
    MUSTMAKER_TAKER(1), // 被动委托是taker则撤单
    DEAL_TOO_MUCH(2), // 吃单深度太大
    TAKER_ROBOT(3), // taker为机器人,只会显示给机器人
    MAKER_ROBOT(4), // maker为机器人,只会显示给机器人
    AMOUNT_ZERO(5), // 挂单张数剩0
    EXPLOSION_FOK(6), // 爆仓单fok
    INSURANCE_LESS(7), // 保险金不够
    MARKET_DEPTH(8), // 市价单深度不够
    CANCEL(9), // 手动撤单
    FORCED_LIQUIDATION(10), // 强平撤单
    EXPLOSION(11), // 爆仓撤单
    MARGIN_LESS(12), // 保证金不够自动取消
    SETTLEMENT(13), // 清算取消订单
    BROKER_PRICE(14), // 订单价格不合法，低于或者高于破产价格
    INSURANCE_TOO_MUCH(15), // 穿仓保险金吃太多，超过仓位开仓价值
    ;

    private final int reason;

    public int getReason() {
        return this.reason;
    }
}
