package cc.newex.dax.users.dto.membership;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfoResDTO {
    /**
     * 用户id
     */
    private Long id;
    /**
     * 对外提供的唯一id
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
     * 发送邮件包含的防钓鱼码
     */
    private String antiPhishingCode;
    /**
     * 用户账号冻结状态 1已冻结;0未冻结 默认为0
     */
    private Integer frozen;
    /**
     * 渠道来源(用户统计搞活动时注册的用户)
     */
    private Integer channel;
    /**
     * 用户类型:0普通用户;1公司内部用户;默认为0;其他保留
     */
    private Integer type;
    /**
     * 全球手机编号(如中国+86)
     */
    private String areaCode;
    /**
     * 用户状态0为开启，1为禁用，其他保留，默认为0
     */
    private Integer status;
    /**
     * 注册地IP
     */
    private String regIp;
    /**
     * 说明备注
     */
    private String memo;
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
     * 创建时间
     */
    private Date createdDate;
    /**
     * 更新时间
     */
    private Date updatedDate;
    /**
     * 开始时间
     */
    private Date startTime;
    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 券商id
     */
    private Integer brokerId;
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

}
