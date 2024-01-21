package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 合约表
 *
 * @author newex-team
 * @date 2019-01-10 14:48:18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Contract {
    /**
     */
    private Long id;

    /**
     * 业务分类:perpetual,future
     */
    private String biz;

    /**
     * 指数基础货币,如BTC、ETH
     */
    private String indexBase;

    /**
     * 基础货币名,如BTC、FBTC
     */
    private String base;

    /**
     * 计价货币名，USD,CNY,USDT
     */
    private String quote;

    /**
     * 方向 0:正向,1:反向
     */
    private Integer direction;

    /**
     * 是Base和Quote之间的组合 PP_BTC_USD，FR_BTC_USD
     */
    private String pairCode;

    /**
     * 一张合约对应的面值,默认1
     */
    private BigDecimal unitAmount;

    /**
     * 保险金账号
     */
    private Long insuranceAccount;

    /**
     * 基础货币最小交易小数位
     */
    private Integer minTradeDigit;

    /**
     * 计价货币最小交易小数位
     */
    private Integer minQuoteDigit;

    /**
     * 是Base和Quote之间的组合 pp_btc_usdt，fr_btc_usdt1109
     */
    private String contractCode;

    /**
     * perpetual;week,nextweek,month,quarter 合约类型列表, perpetual为永续,周,次周,月,季度
     */
    private String type;

    /**
     * 预强平价格阈值:强平价格+-(开仓价格-强平价格)*阈值
     */
    private BigDecimal preLiqudatePriceThreshold;

    /**
     * 清算时间
     */
    private Date liquidationDate;

    /**
     * 结算日期
     */
    private Date settlementDate;

    /**
     * 状态 0:可用 1:过期
     */
    private Integer expired;

    /**
     * 是否测试盘 0:线上盘,1:测试盘
     */
    private Integer env;

    /**
     * 合约状态 0 正常 1 清算中 2 清算结束
     */
    private Integer status;

    /**
     * 业务方ID
     */
    private Integer brokerId;

    /**
     * 利率，用于计算资金费率
     */
    private BigDecimal interestRate;

    /**
     * 清算时刻的标记价格
     */
    private String marketPrice;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 更新时间
     */
    private Date modifyDate;

    /**
     */
    private Integer marketPriceDigit;
}