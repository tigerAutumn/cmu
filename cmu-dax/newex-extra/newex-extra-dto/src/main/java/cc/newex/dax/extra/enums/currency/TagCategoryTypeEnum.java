package cc.newex.dax.extra.enums.currency;

import java.util.Objects;

/**
 * 类别，1-币种标签，2-币对标签
 *
 * @author liutiejun
 * @date 2018-09-27
 */
public enum TagCategoryTypeEnum {

    /**
     * 1-币种标签，2-币对标签
     */
    CURRENCY(1, "currency"),

    /**
     * 1-币种标签，2-币对标签
     */
    PRODUCT(2, "product");

    private final Integer code;
    private final String name;

    TagCategoryTypeEnum(final Integer code, final String name) {
        this.code = code;
        this.name = name;
    }

    public Integer getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static TagCategoryTypeEnum getByCode(final Integer code) {
        if (Objects.isNull(code)) {
            return null;
        }

        switch (code) {
            case 1:
                return CURRENCY;
            case 2:
                return PRODUCT;
            default:
                return null;
        }
    }
}
