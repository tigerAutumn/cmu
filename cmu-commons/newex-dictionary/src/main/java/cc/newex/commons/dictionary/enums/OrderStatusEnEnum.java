package cc.newex.commons.dictionary.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author allen
 * @date 2018/3/15
 * @des 订单状态字典表
 */
public enum OrderStatusEnEnum {
    OPEN((byte) 0, "open"),
    PARTIALLY((byte) 1, "partially-filled"),
    FILLED((byte) 2, "filled"),
    CANCEL((byte) 3, "cancel"),
    CANCELED((byte) -1, "canceled");

    private final Byte type;
    private final String typeName;

    OrderStatusEnEnum(final byte type, final String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static String getTypeName(final Byte type) {
        return Arrays.stream(OrderStatusEnEnum.values())
                .filter(x -> x.getType().equals(type))
                .findFirst()
                .map(OrderStatusEnEnum::getTypeName)
                .orElse(null);
    }

    public static Byte getType(final String typeName) {
        Objects.requireNonNull(typeName);
        return Arrays.stream(OrderStatusEnEnum.values())
                .filter(x -> typeName.contains(x.typeName))
                .findFirst()
                .map(OrderStatusEnEnum::getType)
                .orElse(null);
    }

    public Byte getType() {
        return this.type;
    }

    public String getTypeName() {
        return this.typeName;
    }

}
