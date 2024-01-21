package cc.newex.dax.perpetual.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author newex-team
 * @date 2018/12/20
 */
@AllArgsConstructor
@Getter
public enum DirectionEnum {
    /**
     * 正向合约
     */
    POSITIVE(0),
    /**
     * 反向合约
     */
    REVERSE(1),;

    private final int direction;
}
