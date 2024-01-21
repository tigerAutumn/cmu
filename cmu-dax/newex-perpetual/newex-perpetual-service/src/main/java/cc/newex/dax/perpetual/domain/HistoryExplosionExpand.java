package cc.newex.dax.perpetual.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 爆仓流水扩展表
 *
 * @author newex-team
 * @date 2018-11-20 18:26:43
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryExplosionExpand {
    /**
     *
     */
    private Long id;

    /**
     * 爆仓流水 id
     */
    private Long historyExplosionId;

    /**
     * 关联业务 id，如 爆仓单 id，对敲用户id
     */
    private Long referId;

    /**
     * 1 dealOrder, 2 user
     */
    private Integer type;

    /**
     * 爆仓前持仓数量
     */
    private BigDecimal beforePositionQuantity;

    /**
     * 爆仓后持仓数量
     */
    private BigDecimal afterPositionQuantity;

    /**
     * 爆仓张数
     */
    private BigDecimal closePositionQuantity;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}