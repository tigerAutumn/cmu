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
public class ReconciliationConf {
    /**
     *
     */
    private Long id;
    /**
     * 币种
     */
    private Integer currency;
    /**
     *
     */
    private String bizBalance;
    /**
     * 总余额阈值
     */
    private BigDecimal totalThreshold;
    /**
     * 创建时间
     */
    private Date createDate;
    /**
     * 更新时间
     */
    private Date updateDate;

    public ReconciliationConf selfReference() {
        return this;
    }
}