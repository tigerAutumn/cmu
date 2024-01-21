package cc.newex.commons.dictionary.enums;

import java.util.Arrays;

/**
 * @author allen
 * @date 2018/3/15
 * @des 交易方向
 */
public enum SideTypeEnum {

    BUY((byte) 1, "buy"),
    SELL((byte) 2, "sell");

    private final Byte type;
    private final String sideName;

    SideTypeEnum(final Byte type, final String sideName) {
        this.type = type;
        this.sideName = sideName;
    }

    public static String getTypeName(final Byte type) {
        return Arrays.stream(SideTypeEnum.values())
                .filter(x -> x.getType().equals(type))
                .findFirst().map(SideTypeEnum::getSideName).orElse(null);

    }

    public static Byte getType(final String sideName) {
        return Arrays.stream(SideTypeEnum.values())
                .filter(x -> sideName.contains(x.getSideName()))
                .findFirst()
                .map(SideTypeEnum::getType)
                .orElse(null);
    }

    public Byte getType() {
        return this.type;
    }

    public String getSideName() {
        return this.sideName;
    }
}
