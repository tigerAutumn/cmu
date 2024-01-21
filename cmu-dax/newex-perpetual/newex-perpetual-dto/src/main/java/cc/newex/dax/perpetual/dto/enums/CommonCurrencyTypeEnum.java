package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/5/30 17:03
 * @Description:
 */
public enum CommonCurrencyTypeEnum {
    REVERSE(1, "reverse", "反向合约"),
    FORWARD(2, "forward", "正向合约"),
    ;

    private final String name;
    private final String codeString;
    private final Integer code;

    CommonCurrencyTypeEnum(final int code, final String codeString, final String name) {
        this.code = code;
        this.codeString = codeString;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getCodeString() {
        return this.codeString;
    }

    public static boolean isReverse(final String type) {
        if (("" + REVERSE.code).equalsIgnoreCase(type)) {
            return true;
        }
        return false;
    }
}






