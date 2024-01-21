package cc.newex.commons.dictionary.enums;

import java.util.Arrays;
import java.util.Objects;

/**
 * @author allen
 * @date 2018/3/15
 * @des 下单类型
 */
public enum OrderTypeEnum {
    LIMITED((byte)0, "limit"),
    MARKET((byte)1, "market");

    private final Byte type;
    private final String typeName;

    OrderTypeEnum(final byte type, final String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static String getTypeName(final Byte type) {
        return Arrays.stream(OrderTypeEnum.values())
                .filter(x -> x.getType().equals(type))
                .findFirst()
                .map(OrderTypeEnum::getTypeName)
                .orElse(null);
    }

    public static Byte getType(final String typeName) {
        Objects.requireNonNull(typeName);
        return Arrays.stream(OrderTypeEnum.values())
                .filter(x -> typeName.contains(x.typeName))
                .findFirst()
                .map(OrderTypeEnum::getType)
                .orElse(null);
    }

    public Byte getType() {
        return this.type;
    }

    public String getTypeName() {
        return this.typeName;
    }

}
