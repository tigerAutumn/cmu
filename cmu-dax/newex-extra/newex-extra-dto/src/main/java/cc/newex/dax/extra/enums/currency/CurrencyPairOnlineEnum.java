package cc.newex.dax.extra.enums.currency;

/**
 * 币对上线状态
 *
 * @author newex-team
 * @date 2018/6/21
 */
public enum CurrencyPairOnlineEnum {
    //下线
    OFFLINE((byte) 0),
    //已上线
    ONLINE((byte) 1),
    //预发
    PREP((byte) 2);

    private final Byte code;

    CurrencyPairOnlineEnum(final Byte code) {
        this.code = code;
    }

    public Byte getCode() {
        return this.code;
    }

}
