package cc.newex.dax.boss.web.common.enums;

/**
 * 产品线
 *
 * @author liutiejun
 * @date 2018-08-27
 */
public enum CurrencyBizEnum {

    INTERNAL(0, "internal"),
    SPOT(1, "spot"),
    C2C(2, "c2c"),
    CONTRACT(3, "contract"),
    PORTFOLIO(4, "portfolio"),
    ASSET(5, "asset"),
    PERPETUAL(6, "perpetual");

    private final Integer index;
    private final String name;

    CurrencyBizEnum(final Integer index, final String name) {
        this.index = index;
        this.name = name;
    }

    public Integer getIndex() {
        return this.index;
    }

    public String getName() {
        return this.name;
    }

}
