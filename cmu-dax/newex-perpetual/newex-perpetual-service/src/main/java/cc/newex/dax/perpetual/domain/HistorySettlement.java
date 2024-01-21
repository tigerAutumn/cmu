package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 清算历史记录表
 *
 * @author newex-team
 * @date 2018-11-20 18:26:51
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistorySettlement {
    /**
     * 主键id
     */
    private Long id;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 类型 1.交割 2.清算
     */
    private Integer type;

    /**
     * 交割日期
     */
    private Date settlementDate;

    /**
     * 交割合约张数
     */
    private BigDecimal settlementAmount;

    /**
     * 交割平均价
     */
    private BigDecimal settlementPrice;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}