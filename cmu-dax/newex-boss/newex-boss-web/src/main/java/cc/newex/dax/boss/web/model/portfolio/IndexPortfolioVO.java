package cc.newex.dax.boss.web.model.portfolio;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 指数组合
 *
 * @author liutiejun
 * @date 2018-07-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class IndexPortfolioVO {

    /**
     * 产品ID
     */
    private Long id;

    /**
     * 产品类型，1-指数组合，2-未来权益组合，3-主动管理组合，4-杠杆组合，对应PortfolioTypeEnum
     */
    @NotNull
    private Integer portfolioType;

    /**
     * 产品名称 FBSCTF5
     */
    @NotBlank
    private String name;

    /**
     * 中文信息对应的id
     */
    private Long zhId;

    /**
     * 英文信息对应的id
     */
    private Long enId;

    /**
     * 产品副标题（中文）
     */
    private String zhSubtitle;

    /**
     * 产品副标题（英文）
     */
    private String enSubtitle;

    /**
     * 产品介绍（中文）
     */
    private String zhProductIntro;

    /**
     * 产品介绍（英文）
     */
    private String enProductIntro;

    /**
     * 发行方ID
     */
    private Long issuerId;

    /**
     * 发行方名称（中文）
     */
    private String zhIssuerName;

    /**
     * 发行方名称（英文）
     */
    private String enIssuerName;

    /**
     * 发行方介绍（中文）
     */
    private String zhIssuerIntro;

    /**
     * 发行方介绍（英文）
     */
    private String enIssuerIntro;

    /**
     * 发行方logo
     */
    private String issuerLogo;

    /**
     * 对应的混合指数
     */
    private String mixedIndexName;

    /**
     * 预售时间
     */
    @NotBlank
    private String prePurchStart;

    /**
     * 初始申购时间 - 开始时间
     */
    @NotBlank
    private String purchStart;

    /**
     * 初始申购时间 - 结束时间
     */
    @NotBlank
    private String purchEnd;

    /**
     * 计价货币 例USDT
     */
    private String quoteCurrency;

    /**
     * 初始申购总额
     */
    @NotNull
    private BigDecimal purchLimit;

    /**
     * 最低申购额度
     */
    @NotNull
    private BigDecimal minPurch;

    /**
     * 个人最大可申请额度
     */
    @NotNull
    private BigDecimal personLimit;

    /**
     * 交易数量最大小数位位数
     */
    @NotNull
    @Min(0)
    private Integer maxSizeDigit;

    /**
     * 中途申购方式开关:0不允许;1允许
     */
    private Integer midwaySwitch;

    /**
     * 中途申购时间，1-每日
     */
    private Integer midwayPurchTime;

    /**
     * 中途申购 - 每次申购总额
     */
    private BigDecimal midwayPurchLimit;

    /**
     * 中途申购时间 - 开始时间
     */
    private String midwayPurchStart;

    /**
     * 中途申购时间 - 结束时间
     */
    private String midwayPurchEnd;

    /**
     * 赎回为成分，0-关，1-开
     */
    private Integer redeemComponent;

    /**
     * 赎回为USDT，0-关，1-开
     */
    private Integer redeemUsdt;

    /**
     * 是否可划转至币币账户，0-关（不可以），1-开（可以）
     */
    private Integer transferSpot;

    /**
     * 持仓人数，实时计算
     */
    private Integer holdNumber;

    /**
     * 平台发行份额，实时计算
     */
    private BigDecimal issueTotal;

    /**
     * 申购手续费对应数据的ID
     */
    private Integer purchId;

    /**
     * 申购手续费，收取组合成分数，是一个百分比
     */
    @NotNull
    private BigDecimal purchFee;

    /**
     * 赎回手续费对应数据的ID
     */
    private Integer redeemId;

    /**
     * 赎回手续费
     */
    @NotNull
    private BigDecimal redeemFee;

    /**
     * 组合资产管理费对应数据的ID
     */
    private Integer manageId;

    /**
     * 组合资产管理费
     */
    @NotNull
    private BigDecimal manageFee;

    /**
     * 上线状态:0初始状态，1预发，2线上
     */
    @NotNull
    private Integer envStatus;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date updatedDate;

}
