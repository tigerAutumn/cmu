package cc.newex.dax.users.dto.membership;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户开关设置表
 *
 * @author
 * @date 2018-04-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserSettingsDTO {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是否开启登录2次验证默认开启
     */
    private Integer loginAuthFlag;

    /**
     * Google验证码绑定状态 0未绑定 1绑定
     */
    private Integer googleAuthFlag;

    /**
     * 邮箱验证状态 0：未开启 1：开启
     */
    private Integer emailAuthFlag;

    /**
     * 手机验证状态 0：未开启 1：开启
     */
    private Integer mobileAuthFlag;

    /**
     * 交易密码绑定状态表示交易时是否需要输入交易密码 0未绑定 1绑定
     */
    private Integer tradeAuthFlag;

    /**
     * 登录密码强度级别
     */
    private Integer loginPwdStrength;

    /**
     * 交易密码强度级别
     */
    private Integer tradePwdStrength;

    /**
     * 交易时输入资金密码设置0:每次输入;1每2小时输入;2不输入;默认为1
     */
    private Integer tradePwdInput;

    /**
     * 支付宝开关 0:关闭,1:打开
     */
    private Integer alipayAuthFlag;

    /**
     * 微信支付开关 0:关闭,1:打开
     */
    private Integer wechatPayAuthFlag;

    /**
     * 银行卡支付开关 0:关闭,1:打开
     */
    private Integer bankPayAuthFlag;

    /**
     * 是否冻结现货业务1表示是,0表示否，默认为0
     */
    private Integer spotFrozenFlag;

    /**
     * 是否冻结C2C业务1表示是,0表示否，默认为0
     */
    private Integer c2cFrozenFlag;

    /**
     * 是否冻结合约业务1表示是,0表示否，默认为0
     */
    private Integer contractsFrozenFlag;

    /**
     * 是否冻结资金管理业务1表示是,0表示否，默认为0
     */
    private Integer assetFrozenFlag;

    /**
     * 是否同意协议1表示是,0表示否，默认为0
     */
    private Integer spotProtocolFlag;

    /**
     * 是否同意协议1表示是,0表示否，默认为0
     */
    private Integer c2cProtocolFlag;

    /**
     * 是否同意协议1表示是,0表示否，默认为0
     */
    private Integer portfolioProtocolFlag;

    /**
     * 是否同意协议1表示是,0表示否，默认为0
     */
    private Integer perpetualProtocolFlag;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date updatedDate;

}