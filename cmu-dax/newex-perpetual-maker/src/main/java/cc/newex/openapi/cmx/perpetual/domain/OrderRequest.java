package cc.newex.openapi.cmx.perpetual.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderRequest {

    /**
     * 计划委托类型
     * INDEX("index"), //指数价格market
     * MARK("mark"), //标记价格mark
     * LAST("last"),//最新价格last
     */
    private String triggerBy;
    /**
     * 触发价格
     */
    private BigDecimal triggerPrice;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 券商id
     */
    private Integer brokerId;
    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;
    /**
     * LIMIT(10), // 限价
     * MARKET(11), // 市价
     * FORCED_LIQUIDATION(13), // 强平
     * EXPLOSION(14), // 爆仓
     */
    private Integer type;
    /**
     * 下单方向
     */
    private String side;
    /**
     * 合约id
     */
    @Builder.Default
    private Long pid = 0L;
    /**
     * 价格
     */
    private BigDecimal price;
    /**
     * 下单数量
     */
    private BigDecimal amount;
    /**
     * 合理价格
     */
    private BigDecimal reasonablePrice;
    /**
     * 爆仓价
     */
    private BigDecimal brokerPrice;
    /**
     * 被动委托：0:不care 1:只做maker，如果是taker就撤单
     */
    private Integer beMaker;
}
