package cc.newex.dax.perpetual.common.enums;

import lombok.AllArgsConstructor;

/**
 * 持仓的成交方向
 *
 * @author xionghui
 * @date 2018/11/13
 */
@AllArgsConstructor
public enum PositionTargetEnum {
    OPEN("open"), // 持仓
    CLOSE("close"), //平仓
    ;

    private final String type;

    public String getType() {
        return this.type;
    }
}
