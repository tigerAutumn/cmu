package cc.newex.openapi.bitmex.enums;

/**
 * @author liutiejun
 * @date 2019-04-26
 */
public enum BitMexOrderTypeEnum {

    MARKET("Market"),
    LIMIT("Limit");

    private String type;

    BitMexOrderTypeEnum(final String type) {
        this.type = type;
    }

    public String getType() {
        return this.type;
    }
}
