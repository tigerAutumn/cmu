package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 *
 * @author newex-team
 * @date 2019-01-07 21:55:01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarkPrice {
    /**
     */
    private Long id;

    /**
     * 合约
     */
    private String contractCode;

    /**
     * 计价货币
     */
    private String quoteCurrency;

    /**
     * 交易货币
     */
    private String baseCurrency;

    /**
     * 标记价格
     */
    private BigDecimal markPrice;

    /**
     * 指数价格
     */
    private BigDecimal indexPrice;

    /**
     * 资金费率
     */
    private BigDecimal feeRate;

    /**
     * 预估的资金费率
     */
    private BigDecimal estimateFeeRate;

    /**
     * 最新成交价
     */
    private BigDecimal lastPrice;

    /**
     */
    private Date createdDate;

    /**
     */
    private Date modifyDate;
}