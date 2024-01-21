package cc.newex.commons.dictionary.enums;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author allen
 * @date 2018/3/15
 * @des 交易来源
 */
public enum SourceEnum {

    WEB((byte) 0, "web"),
    APP((byte)1, "app"),
    ANDROID((byte)2, "android"),
    IOS((byte)3, "ios"),
    OPENAPI((byte)4, "openapi");

    private final byte type;
    private final String typeName;

    SourceEnum(final byte type, final String typeName) {
        this.type = type;
        this.typeName = typeName;
    }

    public static String getTypeName(final byte type) {
        return Arrays.stream(SourceEnum.values())
                .filter(x -> x.type == type)
                .findFirst()
                .map(x -> x.typeName)
                .orElse(StringUtils.EMPTY);
    }

    public static int getType(final String typeName) {
        final String name = StringUtils.defaultString(typeName, StringUtils.EMPTY);
        return Arrays.stream(SourceEnum.values())
                .filter(x -> name.equals(x.typeName))
                .findFirst()
                .map(x -> x.type)
                .orElse((byte)0);

    }

    public byte getType() {
        return this.type;
    }

    public String getTypeName() {
        return this.typeName;
    }
}
