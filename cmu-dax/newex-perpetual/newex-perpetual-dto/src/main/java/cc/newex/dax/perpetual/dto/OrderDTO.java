package cc.newex.dax.perpetual.dto;

import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author harry
 * @Date: 2018/6/6 14:03
 * @Description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = -5274467372916034720L;

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
     * 被动委托：0:不care 1:只做maker，如果是taker就撤单
     */
    private Integer beMaker;

    /**
     * 只对爆仓使用，爆仓时的价值
     */
    private BigDecimal brokerSize;
}
