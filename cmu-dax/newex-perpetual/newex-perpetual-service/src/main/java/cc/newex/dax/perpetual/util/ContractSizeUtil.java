package cc.newex.dax.perpetual.util;

import cc.newex.dax.perpetual.common.constant.PerpetualConstants;
import cc.newex.dax.perpetual.common.enums.DirectionEnum;
import cc.newex.dax.perpetual.domain.Contract;

import java.math.BigDecimal;

/**
 * @author newex-team
 * @date 2018/12/21
 */
public class ContractSizeUtil {

    public static BigDecimal countSizeWithScale(final Contract contract, final BigDecimal amount, final BigDecimal price) {
        if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
            return BigDecimalUtil.multiply(amount, contract.getUnitAmount(), price)
                    .setScale(PerpetualConstants.SCALE, BigDecimal.ROUND_DOWN);
        }
        return BigDecimalUtil.divide(BigDecimalUtil.multiply(amount, contract.getUnitAmount()),
                price, PerpetualConstants.SCALE);
    }

    public static BigDecimal countSize(final Contract contract, final BigDecimal amount, final BigDecimal price) {
        if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
            return BigDecimalUtil.multiply(amount, contract.getUnitAmount(), price)
                    .setScale(contract.getMinQuoteDigit(), BigDecimal.ROUND_DOWN);
        }
        return BigDecimalUtil.divide(BigDecimalUtil.multiply(amount, contract.getUnitAmount()),
                price, contract.getMinTradeDigit());
    }

    public static BigDecimal countAmount(final Contract contract, final BigDecimal size, final BigDecimal price) {
        if (contract.getDirection() == DirectionEnum.POSITIVE.getDirection()) {
            return BigDecimalUtil.divide(BigDecimalUtil.divide(size, price), contract.getUnitAmount());
        }
        return BigDecimalUtil.divide(BigDecimalUtil.multiply(size, price), contract.getUnitAmount());
    }
}
