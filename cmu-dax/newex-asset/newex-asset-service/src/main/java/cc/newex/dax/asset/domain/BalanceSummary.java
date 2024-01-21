package cc.newex.dax.asset.domain;

import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018-06-26
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class BalanceSummary {
    /**
     *
     */
    private Long id;
    /**
     * 币种
     */
    private Integer currency;
    /**
     * 钱包余额
     */
    private BigDecimal walletBalance;
    /**
     *
     */
    private String bizBalance;
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
     * 创建时间
     */
    private Date createDate;
}