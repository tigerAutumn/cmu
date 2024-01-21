package cc.newex.dax.perpetual.dto.enums;

/**
 * 用户资金账户状态
 *
 * @author newex-team
 * @date 2018-12-13
 */
public enum UserBalanceStatusEnum {
    /**
     * 正常
     */
    NORMAL(0),
    /**
     * 强平中
     */
    LIQUIDATE(1),
    /**
     * 爆仓中
     */
    EXPLOSION(2),
    ;

    private final int code;

    UserBalanceStatusEnum(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

    public static UserBalanceStatusEnum fromInteger(final Integer code) {
        if (code == null) {
            return null;
        }

        for (final UserBalanceStatusEnum u : values()) {
            if (code.equals(u.getCode())) {
                return u;
            }
        }
        return null;
    }
}
