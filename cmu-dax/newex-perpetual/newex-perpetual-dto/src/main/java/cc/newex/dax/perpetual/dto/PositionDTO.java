package cc.newex.dax.perpetual.dto;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author harry
 * @Date: 2018/8/23 21:36
 * @Description:
 */
@Data
public class PositionDTO {
    /**
     * 仓位杠杆
     */
    BigDecimal leverage;
    /**
     * 初始保证金
     */
    BigDecimal initMargin = BigDecimal.ZERO;
    /**
     * 持仓数量
     */
    BigDecimal currentQty = BigDecimal.ZERO;
    /**
     * 开多仓持仓量
     */
    BigDecimal openOrderBuyQty = BigDecimal.ZERO;
    /**
     * 开多仓保证金
     */
    BigDecimal openOrderBuyCost = BigDecimal.ZERO;
    /**
     * 开多仓额外值
     */
    BigDecimal openOrderBuyPremium = BigDecimal.ZERO;
    /**
     * 开空仓持仓量
     */
    BigDecimal openOrderSellQty = BigDecimal.ZERO;
    /**
     * 开空仓保证金
     */
    BigDecimal openOrderSellCost = BigDecimal.ZERO;
    /**
     * 开空仓额外值
     */
    BigDecimal openOrderSellPremium = BigDecimal.ZERO;
}