package cc.newex.dax.asset.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 币种的属性配置
 *
 * @author lilaizhen
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyExtendAttributes {
    /**
     * 币种符号 单位符号 fix
     */
    private String sign;
    /**
     * 全称  fix
     */
    private String fullName;
    /**
     *
     */
    private String currencyPictureUrl;

    /**
     * 杠杆利息配置
     */
    private Double marginInterestRate;
    /**
     * fix
     */
    private String txExplorerUrl;
    /**
     * 是否需要标签
     */
    private Integer needTag;
    /**
     * 是否需要弹窗
     */
    @Builder.Default
    private Integer needAlert = 0;
    /**
     * 标签字段
     */
    private String tagField;
    /**
     * fix
     */
    private String currencySummary;
    /**
     * 币种地址
     */
    private String currencyUrl;
    /**
     * 属于哪个市场 1.正式区 2是试验区
     */
    @Builder.Default
    private Integer zone = 1;

    /**
     * 中文url
     */
    @Builder.Default
    private String cnWikiUrl = "";
    /**
     * 繁体url
     */
    @Builder.Default
    private String twWikiUrl = "";
    /**
     * 英文url
     */
    @Builder.Default
    private String usWikiUrl = "";

    private Long expireDate;
    /**
     * 区块浏览器
     */
    private String blockBrowser;
    /**
     * c2c 创建时间
     */
    private Date createdDate;
    /**
     * c2c  0=数字货币,1=法币
     */
    private Integer type;
    /**
     * decimal_places 小数点后几位
     */
    private Integer decimalPlaces;
    /**
     * 佣金费率，抽取溢价部分做为平台的佣金
     */
    private BigDecimal platformCommissionRate;
    /**
     * 单笔订单额度最小值
     */
    private BigDecimal minOrderTotalPerOrder;
    /**
     * 单笔订单额度最小值
     */
    private BigDecimal maxOrderTotalPerOrder;

    /**
     * 所有未完成订单总额度最大值。只有法币能用于支付订单
     */
    private BigDecimal maxIncompleteOrderTotalPerUser;
    /**
     * 保证金
     */
    private BigDecimal deposit;
    /**
     * 委托单最小金额
     */
    private BigDecimal minOrderTotalPerTradingOrder;
    /**
     * 委托单最大金额
     */
    private BigDecimal maxOrderTotalPerTradingOrder;
    /**
     * 资金管理页面可见  0 不可见 1可见
     */
    private Integer isAssetVisible;
    /**
     * 关联dapps表的id
     * 业务线[ c2c ]
     */
    private String dapps;
    /**
     * 发行价
     * 业务线[ c2c ]
     */
    private BigDecimal issuePrice;
}
