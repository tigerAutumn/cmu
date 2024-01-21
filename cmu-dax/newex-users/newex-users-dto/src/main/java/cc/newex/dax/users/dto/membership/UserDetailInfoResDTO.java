package cc.newex.dax.users.dto.membership;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailInfoResDTO implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id
     */
    private Long id;
    /**
     * 对外提供的唯一id(rsa加密的id)
     */
    private String uid;
    /**
     * 邮箱
     */
    private String email;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户权限角色
     */
    private String authorities;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 发送邮件包含的防钓鱼码
     */
    private String antiPhishingCode;
    /**
     * google验证码
     */
    private String googleAuthCode;
    /**
     * 用户账号冻结状态 1已冻结;0未冻结 默认为0
     */
    private Byte frozen;
    /**
     * 用户类型:0普通用户;1公司内部用户;默认为0;其他保留
     */
    private Byte type;
    /**
     * 全球手机编号(如中国+86)
     */
    private String phoneCode;
    /**
     * 用户状态0为开启，1为禁用，其他保留，默认为0
     */
    private Byte status;
    /**
     * Google验证码绑定状态 0未绑定 1绑定
     */
    private Byte googleAuthFlag;
    /**
     * 邮箱验证状态 0：未开启 1：开启
     */
    private Byte emailAuthFlag;
    /**
     * 手机验证状态 0：未开启 1：开启
     */
    private Byte mobileAuthFlag;
    /**
     * 交易密码绑定状态表示交易时是否需要输入交易密码 0未绑定 1绑定
     */
    private Byte tradeAuthFlag;
    /**
     * 登录密码强度级别
     */
    private Byte loginPwdStrength;
    /**
     * 交易密码强度级别
     */
    private Byte tradePwdStrength;
    /**
     * 交易时输入资金密码设置0:每次输入;1每2小时输入;2不输入;默认为1
     */
    private Byte tradePwdInput;
    /**
     * 微信支付开关 0：未开启 1：开启
     */
    private Integer alipayAuthFlag;
    /**
     * 微信支付开关 0：未开启 1：开启
     */
    private Integer wechatPayAuthFlag;
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
}
