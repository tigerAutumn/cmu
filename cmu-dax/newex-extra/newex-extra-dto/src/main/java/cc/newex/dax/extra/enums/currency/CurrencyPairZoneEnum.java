package cc.newex.dax.extra.enums.currency;

/**
 * 币对上线状态
 *
 * @author newex-team
 * @date 2018/6/21
 */
public enum CurrencyPairZoneEnum {
    //主板
    MAIN(1),
    //创新板
    NEW(2),
    //创业板
    SECOND(3);

    private final Integer code;

    CurrencyPairZoneEnum(final Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

}
