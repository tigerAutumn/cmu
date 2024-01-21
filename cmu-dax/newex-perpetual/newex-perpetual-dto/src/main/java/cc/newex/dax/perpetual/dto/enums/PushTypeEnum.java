package cc.newex.dax.perpetual.dto.enums;

/**
 * push type
 *
 * @author xionghui
 * @date 2018/10/20
 */
public enum PushTypeEnum {
    DEPTH, // 深度
    FILLS, // 最新成交
    ASSET, // 资产
    ORDER, // 订单
    CONDITION_ORDER, // 条件订单
    POSITION, // 持仓
    FUND_RATE,//合约指数
    FUND_RATES,//合约指数
    RANK,//头部用户排名
    TOTAL_POSITION,//所有持仓
    ;
}
