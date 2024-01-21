package cc.newex.dax.perpetual.common.enums;

import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

/**
 * @author newex-team
 * @date 2018/12/18
 */
@AllArgsConstructor
public enum OrderDirectionEnum {
    GREATER("greater", (o1, o2) -> {
        // 价格大于触发价格
        return o1.compareTo(o2);
    }), // 大于
    LESS("less", (o1, o2) -> {
        // 价格小于触发价格
        return o2.compareTo(o1);
    }), // 小于
    ;

    private final String direction;
    private final Comparator<BigDecimal> priceComparator;

    private static final Map<String, OrderDirectionEnum> DIRECTION_MAP = new HashMap<>();

    static {
        for (final OrderDirectionEnum orderDirectionEnum : OrderDirectionEnum.values()) {
            DIRECTION_MAP.put(orderDirectionEnum.direction, orderDirectionEnum);
        }
    }

    public String getDirection() {
        return this.direction;
    }

    public static Comparator<BigDecimal> getPriceComparator(final String direction) {
        final OrderDirectionEnum orderDirectionEnum = DIRECTION_MAP.get(direction);
        return orderDirectionEnum == null ? null : orderDirectionEnum.priceComparator;
    }
}
