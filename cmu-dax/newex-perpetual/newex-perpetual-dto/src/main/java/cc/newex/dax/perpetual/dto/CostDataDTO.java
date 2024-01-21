package cc.newex.dax.perpetual.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CostDataDTO {
    /**
     * 倍数 解决小数问题
     */
    public long times;//: this.contractInfo.multiplier,
    /**
     * 币种pairCode
     */
    public String pairCode;
    /**
     * 开仓价格
     */
    public BigDecimal price = BigDecimal.ZERO;// this.orderForm.triggerBy === 1 ? this.orderForm.price : this.market['contractId'+this.$route.params.id].fairPrice,
    /**
     * 数量
     */
    public BigDecimal amount;// Number(this.orderForm.size),
    /**
     * 卖一价（深度中）
     */
    public BigDecimal askFirstPrice = BigDecimal.ZERO;// this.askFirstPrice,
    /**
     * 买一价（深度中）
     */
    public BigDecimal bidFirstPrice = BigDecimal.ZERO;// this.bidFirstPrice,
    /**
     * 标记价格
     */
    public BigDecimal fairMarkPrice = BigDecimal.ZERO;// this.market['contractId'+this.$route.params.id] ? this.market['contractId'+this.$route.params.id].fairPrice: 0,
    /**
     * 开仓杠杆,100
     */
    public BigDecimal leverage = new BigDecimal(100);// this.position['contractId'+this.$route.params.id] ? this.position['contractId'+this.$route.params.id].leverage: 100,
    /**
     * 1USDT价值
     */
    public BigDecimal usdValue;// this.contractInfo.value,
    /**
     * 最大杠杆
     */
    public BigDecimal maxLeverage = new BigDecimal(100); // this.contractInfo.maxLeverage,
    /**
     * 维持保证金率
     */
    public BigDecimal maintenanceMarginRate;// this.contractInfo.maintenanceMarginRate, //
    /**
     * 互换补偿率
     */
    public BigDecimal fundingRate = BigDecimal.ZERO;//this.market['contractId'+this.$route.params.id] ? Math.abs(this.market['contractId'+this.$route.params.id].fundingRate): 0,
    /**
     * 持仓数量
     */
    public BigDecimal positionQty = BigDecimal.ZERO;//this.position['contractId'+this.$route.params.id] ? this.position['contractId'+this.$route.params.id].netPositionQty: 0,
    /**
     * 初始保证金
     */
    public BigDecimal initMargin = BigDecimal.ZERO;// this.position['contractId'+this.$route.params.id] ? this.position['contractId'+this.$route.params.id].initMargin: 0, //
    /**
     * 开仓买单数量
     */
    public BigDecimal openOrderBuyQty = BigDecimal.ZERO;// this.position['contractId'+this.$route.params.id] ? this.position['contractId'+this.$route.params.id].openOrderBuyQty: 0,//
    /**
     * 开仓买单价值
     */
    public BigDecimal openOrderBuyCost = BigDecimal.ZERO;// this.position['contractId'+this.$route.params.id] ? this.position['contractId'+this.$route.params.id].openOrderBuyCost: 0,//
    /**
     * 开仓买单额外值
     */
    public BigDecimal openOrderBuyPremium = BigDecimal.ZERO;// this.position['contractId'+this.$route.params.id] ? this.position['contractId'+this.$route.params.id].openOrderBuyPremium: 0,//
    /**
     * 开仓卖单数量
     */
    public BigDecimal openOrderSellQty = BigDecimal.ZERO;// this.position['contractId'+this.$route.params.id] ? this.position['contractId'+this.$route.params.id].openOrderSellQty: 0,//
    /**
     * 开仓卖单价值
     */
    public BigDecimal openOrderSellCost = BigDecimal.ZERO;//this.position['contractId'+this.$route.params.id] ? this.position['contractId'+this.$route.params.id].openOrderSellCost: 0,//
    /**
     * 开仓卖单额外值
     */
    public BigDecimal openOrderSellPremium = BigDecimal.ZERO;//this.position['contractId'+this.$route.params.id] ? this.position['contractId'+this.$route.params.id].openOrderSellPremium: 0//

}