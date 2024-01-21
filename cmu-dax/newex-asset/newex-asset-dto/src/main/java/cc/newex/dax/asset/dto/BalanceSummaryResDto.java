package cc.newex.dax.asset.dto;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018-04-06
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BalanceSummaryResDto {
    /**
     * 币种
     */
    private Integer currency;
    /**
     * 钱包余额
     */
    private BigDecimal walletBalance;
    /**
     * 法币余额
     */
    private BigDecimal spotBalance;
    /**
     * 币币余额
     */
    private BigDecimal c2cBalance;
    /**
     * 合约余额
     */
    private BigDecimal contractBalance;
    /**
     * 充值未确认
     */
    private BigDecimal depositUnconfirmed;
    /**
     * 提现未确认
     */
    private BigDecimal withdrawUnconfirmed;
    /**
     * 差额
     */
    private BigDecimal difference;
    /**
     * 数据获取时间
     */
    private Date timeNode;
    /**
     * 阈值
     */
    private BigDecimal threshold;
}