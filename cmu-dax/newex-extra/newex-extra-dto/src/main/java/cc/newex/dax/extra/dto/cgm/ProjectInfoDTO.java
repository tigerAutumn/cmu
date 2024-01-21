package cc.newex.dax.extra.dto.cgm;

import lombok.*;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户上币申请详情，提供给前端使用
 *
 * @author liutiejun
 * @date 2018-08-13
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectInfoDTO implements Serializable {

    private static final long serialVersionUID = -1803893662646107983L;

    /**
     * 用户id
     */
    private Long userId;

    /**------------------- 关于币种 -------------------**/

    /**
     * 币种名称（币种全称）
     */
    @NotBlank
    @Length(min = 2, max = 100)
    @Pattern(regexp = "[a-zA-Z]+")
    private String token;

    /**
     * 币种代码（币种简称）
     */
    @NotBlank
    @Length(min = 2, max = 100)
    @Pattern(regexp = "[a-zA-Z]+")
    private String tokenSymbol;

    /**
     * 币种logo，支持png、jpg、jpeg格式，图片最大5M
     */
    @NotBlank
    private String imgName;

    /**
     * 币种类型，zhuwangbi-主网币，daibi-代币
     */
    @NotBlank
    @Length(max = 100)
    private String type;

    /**
     * 发行总量
     */
    @NotBlank
    private String issue;

    /**
     * 流通总量
     */
    @NotBlank
    private String circulating;

    /**
     * coinmarketcap url
     */
    @URL
    private String coinmarketcap;

    /**
     * 是否上线交易所 0未上线、1上线
     */
    @NotNull
    private Byte online;

    /**
     * 交易所名称
     */
    @Length(max = 500)
    private String exchangeName;

    /**
     * 24小时成交量
     */
    @Length(max = 1000)
    private String tradeVolume;

    /**
     * Token/Coin当前价格（美元）
     */
    @DecimalMin("0")
    private BigDecimal price;

    /**
     * 拥有用户数
     */
    @Min(0)
    private Integer possessUsers;

    /**
     * 发币时间
     */
    private Date startTime;

    /**
     * 券商ID
     */
    private Integer brokerId;

    /**------------------- 关于项目 -------------------**/

    /**
     * 用户名称
     */
    @NotBlank
    @Length(min = 2, max = 100)
    private String name;

    /**
     * 公司职务
     */
    @NotBlank
    @Length(min = 2, max = 100)
    private String companyPosition;

    /**
     * 联系电话
     */
    @NotBlank
    @Length(min = 1, max = 20)
    @Pattern(regexp = "[0-9]+")
    private String mobile;

    /**
     * 用户邮箱
     */
    @NotBlank
    @Email
    private String email;

    /**
     * 公司名称
     */
    @NotBlank
    @Length(min = 2, max = 500)
    private String company;

    /**
     * 官方网站
     */
    @NotBlank
    @URL
    private String website;

    /**
     * 白皮书
     */
    @NotBlank
    private String whitePaper;

    /**
     * 项目简介
     */
    @NotBlank
    @Length(max = 3000)
    private String projectInfo;

    /**
     * 项目阶段
     */
    @Length(max = 3000)
    private String projectStage;

    /**
     * 项目目标
     */
    @Length(max = 3000)
    private String projectObjective;

    /**
     * 项目进展
     */
    @Length(max = 3000)
    private String projectProgress;

    /**
     * 项目是否可以看到产品 0 未看到、1 看到
     */
    private Byte see;

    /**
     * 项目产品地址
     */
    @Length(max = 1000)
    private String productAddress;

    /**
     * git hub url
     */
    @NotBlank
    @URL
    private String githubUrl;

    /**
     * 团队成员
     */
    @NotBlank
    @Length(min = 1, max = 3000)
    private String team;

    /**
     * 团队顾问
     */
    @NotBlank
    @Length(min = 1, max = 3000)
    private String teamCounselor;

    /**
     * 全职人员
     */
    @Min(0)
    private Integer fulltimeNumber;

    /**
     * 非全职人员
     */
    @Min(0)
    private Integer noFulltimeNumber;

    /**------------------- 私募&ico -------------------**/

    /**
     * 是否举行过私募 0 否、1 是
     */
    @NotNull
    private Byte raise;

    /**
     * 私募总量
     */
    @Length(max = 100)
    private String raiseTotal;

    /**
     * 私募价格
     */
    @Length(max = 100)
    private String raisePrice;

    /**
     * 私募投资参与者
     */
    @Min(0)
    private Integer raiseInvest;

    /**
     * 私募比例
     */
    @Length(max = 100)
    private String raiseRatio;

    /**
     * 私募时间
     */
    @Length(max = 100)
    private String raiseDate;

    /**
     * 私募规则
     */
    @Length(max = 3000)
    private String raiseRule;

    /**
     * 是否ico 0 否、1 是
     */
    @NotNull
    private Byte ico;

    /**
     * ico总量
     */
    @Length(max = 100)
    private String icoTotal;

    /**
     * ico价格
     */
    @Length(max = 100)
    private String icoPrice;

    /**
     * ico投资者
     */
    @Min(0)
    private Integer icoInvest;

    /**
     * ico比例
     */
    @Length(max = 100)
    private String icoRatio;

    /**
     * ico时间
     */
    @Length(max = 100)
    private String icoDate;

    /**
     * ico规则
     */
    @Length(max = 3000)
    private String icoRule;

    /**------------------- 社群&钱包 -------------------**/

    /**
     * telegram link
     */
    private String telegramLink;

    /**
     * telegram link  members
     */
    private String telegramLinkMembers;

    /**
     * wechat_link
     */
    private String wechatLink;

    /**
     * wechat_link_members
     */
    private String wechatLinkMembers;

    /**
     * qq
     */
    private String qq;

    /**
     * kakao talk
     */
    private String kakaoTalk;

    /**
     * twitter
     */
    private String twitter;

    /**
     * facebook
     */
    private String facebook;

    /**
     * weibo
     */
    private String weibo;

    /**
     * reddit
     */
    private String reddit;

    /**
     * others
     */
    private String others;

    /**
     * 1:erc20,0:其他
     */
    @NotNull
    private Integer walletType;

    /**
     * 合约地址
     */
    @Length(max = 100)
    private String contract;

    /**
     * 项目钱包的说明
     */
    @Length(max = 3000)
    private String wallet;

}
