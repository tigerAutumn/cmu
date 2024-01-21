package cc.newex.dax.extra.enums.currency;

import java.util.Objects;

/**
 * 状态，0-禁用，1-启用
 *
 * @author liutiejun
 * @date 2018-08-21
 */
public enum PermissionStatusEnum {

    DISABLE(0, "disable"),

    ENABLE(1, "enable");

    private final Integer code;
    private final String name;

    PermissionStatusEnum(final Integer code, final String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static PermissionStatusEnum getByCode(final Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }

        switch (code) {
            case 0:
                return DISABLE;
            case 1:
                return ENABLE;
            default:
                return null;
        }
    }

}
