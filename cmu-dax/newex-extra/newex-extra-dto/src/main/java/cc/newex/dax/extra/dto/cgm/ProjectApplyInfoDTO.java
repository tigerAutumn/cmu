package cc.newex.dax.extra.dto.cgm;

import lombok.*;

import java.util.Date;

/**
 * 项目信息表
 *
 * @author mbg.generated
 * @date 2018-08-16 15:16:03
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectApplyInfoDTO {
    /**
     * 主键
     */
    private Long id;

    /**
     * 项目id
     */
    private Long tokenInfoId;

    /**
     * 语言(zh-cn/en-us等)
     */
    private String locale;

    /**
     * 公司名称
     */
    private String company;

    /**
     * 官方网站
     */
    private String website;

    /**
     * 白皮书
     */
    private String whitePaper;

    /**
     * 项目简介
     */
    private String projectInfo;

    /**
     * 项目阶段
     */
    private String projectStage;

    /**
     * 项目目标
     */
    private String projectObjective;

    /**
     * 项目进展
     */
    private String projectProgress;

    /**
     * 项目是否可以看到产品 0 未看到、1 看到
     */
    private Byte see;

    /**
     * git hub url
     */
    private String githubUrl;

    /**
     * 团队成员
     */
    private String team;

    /**
     * 团队顾问
     */
    private String teamCounselor;

    /**
     * 全职人员
     */
    private Integer fulltimeNumber;

    /**
     * 非全职人员
     */
    private Integer noFulltimeNumber;

    /**
     * 是否举行过私募 0 否、1 是
     */
    private Byte raise;

    /**
     * 私募总量
     */
    private String raiseTotal;

    /**
     * 私募价格
     */
    private String raisePrice;

    /**
     * 私募投资参与者
     */
    private Integer raiseInvest;

    /**
     * 私募比例
     */
    private String raiseRatio;

    /**
     * 私募时间
     */
    private String raiseDate;

    /**
     * 私募规则
     */
    private String raiseRule;

    /**
     * 是否ico 0 否、1 是
     */
    private Byte ico;

    /**
     * ico总量
     */
    private String icoTotal;

    /**
     * ico价格
     */
    private String icoPrice;

    /**
     * ico投资者
     */
    private Integer icoInvest;

    /**
     * ico比例
     */
    private String icoRatio;

    /**
     * ico时间
     */
    private String icoDate;

    /**
     * ico规则
     */
    private String icoRule;

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
     * reddit
     */
    private String others;

    /**
     * 1:erc20,0:其他
     */
    private Integer walletType;

    /**
     * 合约地址
     */
    private String contract;

    /**
     * 项目钱包的说明
     */
    private String wallet;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

    /**
     * 公司职务
     */
    private String companyPosition;

    /**
     * 项目产品地址
     */
    private String productAddress;

    /**
     * 券商ID
     */
    private Integer brokerId;
}