package cc.newex.openapi.bitmex.enums;

import cc.newex.openapi.cmx.perpetual.enums.OrderSideEnum;

import java.util.Arrays;

/**
 * @author liutiejun
 * @date 2019-04-24
 */
public enum BitMexSideEnum {

    SELL("Sell"),
    BUY("Buy");

    private String sideName;

    BitMexSideEnum(final String sideName) {
        this.sideName = sideName;
    }

    public static BitMexSideEnum getBySideName(final String sideName) {
        return Arrays.stream(values())
                .filter(x -> x.getSideName().equalsIgnoreCase(sideName))
                .findFirst()
                .orElse(null);
    }

    public static BitMexSideEnum parseCmxSide(final OrderSideEnum side) {
        if (side == null) {
            return null;
        }

        if (OrderSideEnum.LONG.equals(side)) {
            return BUY;
        }

        if (OrderSideEnum.SHORT.equals(side)) {
            return SELL;
        }

        return null;
    }

    public String getSideName() {
        return this.sideName;
    }

}
