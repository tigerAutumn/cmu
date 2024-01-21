package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 预爆仓备份表(不会被任务删除)
 *
 * @author newex-team
 * @date 2018-11-20 18:26:46
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryLiquidate {
    /**
     * 平仓表主键
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
     * 平仓前持仓数量
     */
    private BigDecimal beforePositionQuantity;

    /**
     * 平仓后持仓数量
     */
    private BigDecimal afterPositionQuantity;

    /**
     * 平仓张数
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
     * 强平单 id
     */
    private Long orderId;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}