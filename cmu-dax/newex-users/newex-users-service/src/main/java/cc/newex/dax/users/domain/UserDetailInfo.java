package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

/**
 * 用户详细信息
 *
 * @author newex-team
 * @date 2018-04-07
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserDetailInfo {

    /**
     * 用户id
     */
    private Long id;
    /**
     * 账号所属的主账号ID(如果为0表示主账号,否则为子账号)
     */
    private Long parentId;
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
    private String mobile;
    /**
     * 登录密码
     */
    private String password;
    /**
     * 交易密码
     */
    private String tradePassword;
    /**
     * 用户权限角色
     */
    private String authorities;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 用户真实姓名
     */
    private String realName;
    /**
     * 用户头像url
     */
    private String avatar;
    /**
     * 发送邮件包含的防钓鱼码
     */
    private String antiPhishingCode;
    /**
     * google验证码
     */
    private String googleCode;
    /**
     * 渠道来源(用户统计搞活动时注册的用户)
     */
    private Integer channel;
    /**
     * 用户账号冻结状态 1已冻结;0未冻结 默认为0
     */
    private Integer frozen;
    /**
     * 用户类型:0普通用户;1公司内部用户;默认为0;其他保留
     */
    private Integer type;
    /**
     * 全球手机编号(如中国86)
     */
    private Integer areaCode;
    /**
     * 注册位置0:网站注册1:客服注册2:微博注册3:qq注册4:微信注册
     */
    private Integer regFrom;
    /**
     * 用户状态0为开启，1为禁用，其他保留，默认为0
     */
    private Integer status;
    /**
     * 用于乐观锁
     */
    private Long version;
    /**
     * 注册地IP
     */
    private Long regIp;
    /**
     * 说明备注
     */
    private String memo;
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
     * 支付宝支付开关 0：未开启 1：开启
     */
    private Integer alipayAuthFlag;
    /**
     * 微信支付开关 0：未开启 1：开启
     */
    private Integer wechatPayAuthFlag;

    /**
     * 银行卡支付开关 0：未开启 1：开启
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
     * 更新时间
     */
    private Date updatedDate;

    /**
     * 券商ID
     */
    private Integer brokerId;

    public static UserDetailInfo getInstance() {
        return UserDetailInfo.builder()
                .parentId(0L)
                .uid(UUID.randomUUID().toString())
                .email("")
                .mobile("")
                .areaCode(0)
                .password("")
                .tradePassword("")
                .frozen(0)
                .nickname("")
                .regFrom(0)
                .authorities("ROLE_USER")
                .avatar("")
                .antiPhishingCode("")
                .googleCode("")
                .channel(0)
                .type(0)
                .status(0)
                .version(0L)
                .regIp(0L)
                .memo("")
                .realName("")
                .loginAuthFlag(1)
                .googleAuthFlag(0)
                .emailAuthFlag(0)
                .mobileAuthFlag(0)
                .tradeAuthFlag(0)
                .loginPwdStrength(0)
                .tradePwdStrength(0)
                .tradePwdInput(1)
                .alipayAuthFlag(0)
                .wechatPayAuthFlag(0)
                .bankPayAuthFlag(0)
                .spotFrozenFlag(0)
                .c2cFrozenFlag(0)
                .contractsFrozenFlag(0)
                .assetFrozenFlag(0)
                .spotProtocolFlag(0)
                .c2cProtocolFlag(0)
                .portfolioProtocolFlag(0)
                .perpetualProtocolFlag(0)
                .updatedDate(new Date())
                .brokerId(1)
                .build();
    }
}
