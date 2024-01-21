package cc.newex.dax.boss.web.model.portfolio;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 未来权益组合
 *
 * @author liutiejun
 * @date 2018-07-23
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FuturePortfolioVO {

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
     * 包含的币种
     */
    private String currencyInfos;

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
     * 个人最大可申请份额
     */
    @NotNull
    private BigDecimal personLimit;

    /**
     * 交割时间
     */
    private String deliveryTime;

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
     * 申购手续费，收取组合成分数，是一个百分比
     */
    private BigDecimal purchFee;

    /**
     * 赎回手续费
     */
    private BigDecimal redeemFee;

    /**
     * 组合资产管理费
     */
    private BigDecimal manageFee;

    /**
     * 上线状态:0初始状态，1预发，2线上
     */
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
