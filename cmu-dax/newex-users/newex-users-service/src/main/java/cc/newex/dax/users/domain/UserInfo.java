package cc.newex.dax.users.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户信息表
 *
 * @author newex-team
 * @date 2018-04-10
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 用户id(分布式唯一id)
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
     * google验证码
     */
    private String googleCode;
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
     * 全球手机编号(如中国86)
     */
    private Integer areaCode;
    /**
     * 发送邮件包含的防钓鱼码
     */
    private String antiPhishingCode;
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

    public static UserInfo getInstance() {
        return UserInfo.builder()
                .id(0L)
                .parentId(0L)
                .uid("")
                .email("")
                .mobile("")
                .password("")
                .tradePassword("")
                .googleCode("")
                .authorities("")
                .nickname("")
                .realName("")
                .avatar("")
                .areaCode(0)
                .antiPhishingCode("")
                .channel(0)
                .frozen(0)
                .type(0)
                .regFrom(0)
                .status(0)
                .version(0L)
                .regIp(0L)
                .memo("")
                .updatedDate(new Date())
                .brokerId(1)
                .build();
    }
}