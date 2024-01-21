package cc.newex.dax.extra.enums.currency;

import java.util.Objects;

/**
 * 币种信息状态，1-待审核，2-已发布，3-已下架
 *
 * @author liutiejun
 * @date 2018-08-21
 */
public enum CurrencyStatusEnum {

    CHECK(1, "check"),

    PUBLISH(2, "publish"),

    SHELVE(3, "shelve");

    private final Integer code;
    private final String name;

    CurrencyStatusEnum(final Integer code, final String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static CurrencyStatusEnum getByCode(final Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }

        switch (code) {
            case 1:
                return CHECK;
            case 2:
                return PUBLISH;
            case 3:
                return SHELVE;
            default:
                return null;
        }
    }
}
