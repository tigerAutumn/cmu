package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/5/30 17:03
 * @Description:
 */
public enum ContractStatusEnum {
    IN_USING(0, "in using", "合约可用"),
    EXPIRED(1, "expired", "合约过期"),
    ;

    private final int code;
    private final String name;
    private final String desc;

    ContractStatusEnum(final int code, final String name, final String desc) {
        this.code = code;
        this.name = name;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public static ContractStatusEnum valueOf(final int code) {
        for (final ContractStatusEnum emailEnum : values()) {
            if (emailEnum.getCode() == code) {
                return emailEnum;
            }
        }
        return null;
    }

    public static boolean isInUsing(final int code) {
        if (IN_USING.code == code) {
            return true;
        } else {
            return false;
        }
    }
}
