package cc.newex.dax.extra.enums.cgm;

/**
 * @author newex-team
 * @date 2018/8/13 下午5:30
 */
public enum SweetsStatusEnum {

    //未支付
    NO_PAID(0),

    //已支付
    PAID(1);

    private final Integer code;

    SweetsStatusEnum(final Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return this.code;
    }

}
