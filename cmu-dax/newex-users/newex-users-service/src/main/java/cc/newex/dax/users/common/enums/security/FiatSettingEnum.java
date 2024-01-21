package cc.newex.dax.users.common.enums.security;

public enum FiatSettingEnum {

    FIAT_ALIPAY_SETTING(1, "设置alipay"),
    FIAT_WEPAY_SETTING(2, "设置微信支付");
    /**
     * 类型
     */
    private final int type;
    /**
     * 描述
     */
    private final String desc;

    FiatSettingEnum(final int type, final String desc) {
        this.type = type;
        this.desc = desc;
    }

    public int getType() {
        return this.type;
    }

    public String getDesc() {
        return this.desc;
    }
}
