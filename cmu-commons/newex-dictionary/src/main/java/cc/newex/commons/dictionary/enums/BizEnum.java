package cc.newex.commons.dictionary.enums;

/**
 * @author newex-team
 * @data 27/03/2018
 */

public enum BizEnum {
    INTERNAL(0, "internal"),
    SPOT(1, "spot"),
    C2C(2, "c2c"),
    CONTRACT(3, "contract"),
    PORTFOLIO(4, "portfolio"),
    PERPETUAL(5, "perpetual");

    private final Integer index;
    private final String name;

    BizEnum(final Integer index, final String name) {
        this.index = index;
        this.name = name;
    }

    public static BizEnum parseBiz(final Integer index) {
        for (final BizEnum biz : BizEnum.values()) {
            if (biz.getIndex() == index) {
                return biz;
            }
        }

        throw new IllegalArgumentException(String.valueOf(index));
    }

    public static BizEnum parseBiz(final String name) {
        for (final BizEnum biz : BizEnum.values()) {
            if (biz.getName().equalsIgnoreCase(name.toLowerCase())) {
                return biz;
            }
        }

        throw new IllegalArgumentException(name);
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }


}
