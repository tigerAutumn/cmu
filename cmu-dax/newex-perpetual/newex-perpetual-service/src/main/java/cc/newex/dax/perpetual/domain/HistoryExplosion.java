package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 爆仓流水表
 *
 * @author newex-team
 * @date 2018-11-20 18:26:40
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryExplosion {
    /**
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 业务方ID
     */
    private Integer brokerId;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 仓位类型，long多，short空
     */
    private String side;

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
     * 标记价格
     */
    private BigDecimal marketPrice;

    /**
     * 预强平价
     */
    private BigDecimal preLiqudatePrice;

    /**
     * 强平价
     */
    private BigDecimal liqudatePrice;

    /**
     * 破产价
     */
    private BigDecimal brokerPrice;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}