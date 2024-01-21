package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 合约币对
 *
 * @author newex-team
 * @date 2019-01-10 14:48:21
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CurrencyPair {
    /**
     */
    private Integer id;

    /**
     * 业务分类:perpetual,future
     */
    private String biz;

    /**
     * 指数基础货币,如BTC、ETH
     */
    private String indexBase;

    /**
     * 基础货币名,如btc、fbtc
     */
    private String base;

    /**
     * 计价货币名，usd,cny,usdt
     */
    private String quote;

    /**
     * 方向 0:正向,1:反向
     */
    private Integer direction;

    /**
     * 是base和quote之间的组合 pp_btc_usd，fr_btc_usd
     */
    private String pairCode;

    /**
     * 保险金账号
     */
    private Long insuranceAccount;

    /**
     * 一张合约对应的面值,默认1
     */
    private BigDecimal unitAmount;

    /**
     * 每笔最小挂单张数
     */
    private BigDecimal minOrderAmount;

    /**
     * 每笔最大挂单张数
     */
    private BigDecimal maxOrderAmount;

    /**
     * 单人最大挂单订单数
     */
    private Integer maxOrders;

    /**
     * 基础货币最小交易小数位
     */
    private Integer minTradeDigit;

    /**
     * 计价货币最小交易小数位
     */
    private Integer minQuoteDigit;

    /**
     * perpetual;week,nextweek,month,quarter 合约类型列表, perpetual为永续,周,次周,月,季度
     */
    private String type;

    /**
     * 预强平价格阈值:强平价格+(开仓价格-强平价格)*阈值
     */
    private BigDecimal preLiqudatePriceThreshold;

    /**
     * 溢价指数阈值下限
     */
    private BigDecimal premiumMinRange;

    /**
     * 溢价指数阈值上限
     */
    private BigDecimal premiumMaxRange;

    /**
     * 溢价指数加权买卖价的取值深度,单位为基础货币
     */
    private BigDecimal premiumDepth;

    /**
     * 绝对的资金费率上限为 起始保证金 - 维持保证金 的 百分比
     */
    private BigDecimal fundingCeiling;

    /**
     * 单位是base,最低档位
     */
    private BigDecimal minGear;

    /**
     * 单位是base,每档的差值
     */
    private BigDecimal diffGear;

    /**
     * 单位是base,最高档位
     */
    private BigDecimal maxGear;

    /**
     * 维持保证金费率,每升一档都加一个维持保证金费率
     */
    private BigDecimal maintainRate;

    /**
     * 清算周期，以小时: 从北京中午12:00点开始
     */
    private Integer liquidationHour;

    /**
     * 点卡抵扣手续费 0:不使用,1:使用
     */
    private Integer dkFee;

    /**
     * 是否测试盘 0:线上盘,1:测试盘
     */
    private Integer env;

    /**
     * 状态:0下线;1预发;2线上可用
     */
    private Integer online;

    /**
     * 利率，用于计算资金费率
     */
    private BigDecimal interestRate;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     * 标记价格最小单位
     */
    private Integer marketPriceDigit;
}