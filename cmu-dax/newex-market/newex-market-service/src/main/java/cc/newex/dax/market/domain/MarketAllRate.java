package cc.newex.dax.market.domain;

import java.math.BigDecimal;
import java.util.Date;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class MarketAllRate {
    /**
     * 汇率
     */
    private Long id;
    /**
     * 汇率名
     */
    private String rateName;
    /**
     *
     */
    private Date modifyTime;
    /**
     * 1可用0不可用
     */
    private Integer status;
    /**
     *
     */
    private Date createTime;
    /**
     * 汇率
     */
    private BigDecimal rateParities;
    /**
     * 两周内的平均汇率
     */
    private BigDecimal pairAvg;
}