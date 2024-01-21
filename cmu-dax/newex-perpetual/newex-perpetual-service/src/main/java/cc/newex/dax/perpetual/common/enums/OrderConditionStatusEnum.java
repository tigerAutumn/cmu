package cc.newex.dax.perpetual.common.enums;

import lombok.AllArgsConstructor;

/**
 * 计划委托订单状态类型：0 未触发 1 已经触发 -1 已经撤销
 *
 * @author xionghui
 * @date 2018/11/13
 */
@AllArgsConstructor
public enum OrderConditionStatusEnum {
    NODEAL(0),  // 未触发
    DEAL(1),    // 已经触发
    CANCEL(-1), // 已经撤销
    ;

    private final int status;

    public int getStatus() {
        return this.status;
    }
}
