package cc.newex.dax.boss.web.model.spot;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class ProductVO {
    /**
     * 币对id
     */
    private Integer id;
    /**
     * 币对
     */
    private String code;
    /**
     * 交易货币
     */
    private Integer baseCurrency;

    /**
     * 交易货币的code
     */
    private String baseCurrencyCode;

    /**
     * 计价货币
     */
    private Integer quoteCurrency;

    /**
     * 计价货币Code
     */
    private String quoteCurrencyCode;

    /**
     * 最小委托量 min_trade_size
     */
    private BigDecimal minTradeSize;

    /**
     * 交易价格小数位数 交易价格小数位数
     */
    private Byte maxPriceDigit;

    /**
     * 交易数量小数位数
     */
    private Byte maxSizeDigit;

    /**
     * 计价货币数量单位精度
     */
    private Integer quotePrecision;

    /**
     * 交易位数最小单位
     */
    private BigDecimal quoteIncrement;
    /**
     * 交易数量最小单位
     */
    private BigDecimal baseIncrement;

    /**
     * 是否开启杠杆
     */
    private boolean marginOpen;

    /**
     * 最大杠杆倍数
     */
    private Integer maxMarginLeverage;

    /**
     * 杠杆预爆仓风险系数
     */
    private Float marginRiskPreRatio;

    /**
     * 杠杆爆仓风险系数
     */
    private Float marginRiskRatio;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 币对状态 下线 预撮合 预发 上线
     */
    private Byte online;

    /**
     * 最新价格
     */
    private BigDecimal lastPrice;

    /**
     * 名称
     */
    private String name;

    /**
     * 合并深度
     */
    private BigDecimal[] MergeType;

    /**
     * 计价币 限制
     */
    private BigDecimal baseLimit;

    /**
     * 计价币 用户 限制
     */
    private BigDecimal baseUserLimit;

    /**
     * 交易 总限额
     */
    private BigDecimal quoteLimit;
    /**
     * 交易 用户限额
     */
    private BigDecimal quoteUserLimit;

    /**
     * 挂单手续费
     */
    private BigDecimal makerRate;

    /**
     * 吃单手续费
     */
    private BigDecimal tickerRate;

    /**
     * 市场序号
     */
    private BigDecimal marketFrom;

    /**
     * 交易币 利率
     */
    private BigDecimal baseInterestRate;
    /**
     * 计价币利率
     */
    private BigDecimal quoteInterestRate;

    /**
     * 交易分区，1-主板块，2-创新板，3-币创板
     */
    private Integer zone;

    /**
     * 过期时间
     */
    private String expireDate;

    /**
     * 是否显示过期时间,1:显示，0:不显示
     */
    private Integer status;

    /**
     * 币对支持的券商id
     */
    private List<Integer> brokerIds;

}
