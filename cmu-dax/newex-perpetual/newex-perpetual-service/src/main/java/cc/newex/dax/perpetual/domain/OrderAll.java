package cc.newex.dax.perpetual.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 全部订单表
 *
 * @author newex-team
 * @date 2019-01-03 14:24:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderAll {
    /**
     */
    private Long id;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 0:下单 1:撤单
     */
    private Integer clazz;

    /**
     * 被动委托：0:不care 1:只做maker，如何是taker就撤单
     */
    private Integer mustMaker;

    /**
     * 仓位方向，long多，short空
     */
    private String side;

    /**
     * 1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short
     */
    private String detailSide;

    /**
     * 委托数量
     */
    private BigDecimal amount;

    /**
     * 用户订单委托价格
     */
    private BigDecimal price;

    /**
     * 平均成交价格
     */
    private BigDecimal avgPrice;

    /**
     * 已成交数量
     */
    private BigDecimal dealAmount;

    /**
     * 委托价值
     */
    private BigDecimal size;

    /**
     * 已经成交价值
     */
    private BigDecimal dealSize;

    /**
     * 开仓保险金
     */
    private BigDecimal openMargin;

    /**
     * 额外价格偏移保证金
     */
    private BigDecimal extraMargin;

    /**
     * 0 等待成交 1 部分成交 2 已经成交 -1 已经撤销
     */
    private Integer status;

    /**
     * 下单来源 0网页下单,2openApi,3IOS,4android
     */
    private Integer orderFrom;

    /**
     * 10:限价 11:市价 13:强平单 14:爆仓单 15：穿仓 16：强减
     */
    private Integer systemType;

    /**
     * 订单关联id
     */
    private Long relationOrderId;

    /**
     * 该笔订单成交后对应的盈亏: 正表示盈利,负表示亏损
     */
    private BigDecimal profit;

    /**
     * 该笔订单成交后交的手续费: 正表示得手续费,负表示付手续费
     */
    private BigDecimal fee;

    /**
     * 该笔订单取消的理由，0是默认值，没有理由
     */
    private Integer reason;

    /**
     * 业务方ID
     */
    private Integer brokerId;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}