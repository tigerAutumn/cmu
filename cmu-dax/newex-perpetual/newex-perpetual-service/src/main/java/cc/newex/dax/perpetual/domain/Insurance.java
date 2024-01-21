package cc.newex.dax.perpetual.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 风险准备金
 *
 * @author newex-team
 * @date 2018-09-26 17:27:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Insurance {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 0:增加风险金 1:减少风险金
     */
    private Integer code;

    /**
     * 是Base和quote之间的组合 P_BTC_USD
     */
    private String pairCode;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 操作金额
     */
    private BigDecimal avaible;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}