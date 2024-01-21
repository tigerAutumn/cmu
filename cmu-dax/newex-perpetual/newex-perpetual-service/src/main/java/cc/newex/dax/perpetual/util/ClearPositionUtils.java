package cc.newex.dax.perpetual.util;

import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.domain.Contract;
import cc.newex.dax.perpetual.domain.UserPosition;
import cc.newex.dax.perpetual.dto.MarkIndexPriceDTO;

import java.math.BigDecimal;

public class ClearPositionUtils {

    public static final BigDecimal takeTotalSize(final Contract contract, final UserPosition userPosition, final MarkIndexPriceDTO price) {
        // 用户持仓期望得到的资金费用
        final BigDecimal value;
        if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
            value = BigDecimalUtil.multiply(price.getFeeRate().abs(), price.getMarkPrice());
        } else {
            value = BigDecimalUtil.divide(price.getFeeRate().abs(), price.getMarkPrice());
        }
        return BigDecimalUtil.multiply(BigDecimalUtil.multiply(userPosition.getAmount(), contract.getUnitAmount()), value);
    }
}
