package cc.newex.dax.users.common.consts;

/**
 * @author newex-team
 * @date 2018/03/18
 */
public class UserFiatConsts {

    /**
     * 正常用户
     **/
    public static final int USER_STATUS_0 = 0;
    /**
     * 冻结用户
     **/
    public static final int USER_STATUS_1 = 1;
    /**
     * 法币设置类型 1:支付宝 2:微信
     **/
    public static final int USER_FIAT_ALIPAY = 1;
    public static final int USER_FIAT_WECHATPAY = 2;
    public static final int USER_FIAT_BANKPAY = 3;

    /**
     * 支付宝设置开关 0:关 1:开
     **/
    public static final int USER_FIAT_ALIPAY_0 = 0;
    public static final int USER_FIAT_ALIPAY_1 = 1;
    /**
     * 微信设置开关 0:关 1:开
     **/
    public static final int USER_FIAT_WECHATPAY_0 = 0;
    public static final int USER_FIAT_WECHATPAY_1 = 1;

    /**
     * 银行卡设置开关 0:关 1:开
     **/
    public static final int USER_FIAT_BANKPAY_0 = 0;
    public static final int USER_FIAT_BANKPAY_1 = 1;

    /**
     * 法币设置-支付方式 1:银行卡,2:支付宝,3:微信支付
     */
    public static final int USER_FIAT_PAYTYPE_1 = 1;
    public static final int USER_FIAT_PAYTYPE_2 = 2;
    public static final int USER_FIAT_PAYTYPE_3 = 3;

}
