package cc.newex.dax.extra.enums.cgm;

/**
 * @author newex-team
 * @date 2018/8/13 下午5:34
 */
public enum CurrencyTypeEnum {
    //保证金
    DEPOSIT(1),

    //糖果币
    SWEETS(2);

    private final Integer code;

    CurrencyTypeEnum(final Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

}
