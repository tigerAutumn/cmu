package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/6/21 19:40
 * @Description:
 */
public enum UserBalanceFrozenStatus {
    FREEZE(1, "冻结"),
    UNFROZEN(0, "未冻结"),;

    private final int code;
    private final String name;

    private UserBalanceFrozenStatus(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static UserBalanceFrozenStatus fromInteger(Integer code) {
        if (code == null) {
            return null;
        }

        for (UserBalanceFrozenStatus u : UserBalanceFrozenStatus.values()) {
            if (u.getCode() == code) {
                return u;
            }
        }
        return null;
    }
}
