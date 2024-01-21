package cc.newex.dax.users.rest.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用户个人设置
 *
 * @author liutiejun
 * @date 2018-11-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSettingsVO {

    /**
     * 是否开启登录2次验证默认开启
     */
    private Boolean enableLoginAuth;
    /**
     * Google验证码绑定状态 false未绑定 true绑定
     */
    private Boolean enableGoogleAuth;
    /**
     * Google是否绑定 false未绑定 true绑定
     */
    private Boolean isGoogleBind;
    /**
     * 邮箱验证状态 false未绑定 true绑定
     */
    private Boolean enableEmailAuth;
    /**
     * 手机验证状态 false未绑定 true绑定
     */
    private Boolean enableMobileAuth;
    /**
     * 交易密码绑定状态表示交易时是否需要输入交易密码 false未绑定 true绑定
     */
    private Boolean enableTradeAuth;
    /**
     * 登录密码强度级别(低，中，高)|(low,middle,high)
     */
    private String loginPwdStrength;
    /**
     * 交易密码强度级别(低，中，高)|(low,middle,high)
     */
    private String tradePwdStrength;
    /**
     * 交易时输入资金密码设置0:每次输入;1每2小时输入;2不输入;默认为1
     * 每次输入(always,every2hours,never)
     */
    private String tradePwdInput;
    /**
     * 微信支付开关 false:关闭,true:打开
     */
    private Boolean enableAlipayAuthFlag;
    /**
     * 微信支付开关 false:关闭,true:打开
     */
    private Boolean enableWechatPayAuthFlag;
    /**
     * 银行卡是否绑定 false:未绑定,true:绑定
     */
    private Boolean enableBankPayAuthFlag;
    /**
     * 银行卡是否绑定 false:未绑定,true:绑定
     */
    private Boolean enableBankCardFlag;
    /**
     * 提现风控,false:可提现,true:不可提现
     */
    private Boolean enableWithdrawLimit;
    /**
     * 是否同意协议false表示是,true表示否
     */
    private Boolean enableSpotProtocolFlag;
    /**
     * 是否同意协议false表示是,true表示否
     */
    private Boolean enableC2CProtocolFlag;
    /**
     * 是否同意协议false表示是,true表示否
     */
    private Boolean enablePortfolioProtocolFlag;
    /**
     * 是否同意协议false表示是,true表示否
     */
    private Boolean enablePerpetualProtocolFlag;

}
