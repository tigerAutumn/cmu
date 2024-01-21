package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 计划委托条件表
 *
 * @author newex-team
 * @date 2018-12-18 10:55:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class OrderCondition {
    /**
     */
    private Long id;

    /**
     * 触发类型：指数价格，标记价格，最新价格
     */
    private String type;

    /**
     * 触发方向，greater大于，less小于
     */
    private String direction;

    /**
     * 触发价格
     */
    private BigDecimal conditionPrice;

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
     * 用户订单委托或者破产价格
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
     * 每张合约摊到的保证金
     */
    private BigDecimal avgMargin;

    /**
     * 0 未触发 1 已经触发 -1 已经撤销
     */
    private Integer status;

    /**
     * 下单来源 0网页下单,2openApi,3IOS,4android
     */
    private Integer orderFrom;

    /**
     * 10:限价 11:市价 13:强平单 14:爆仓单
     */
    private Integer systemType;

    /**
     * 订单关联id
     */
    private Long relationOrderId;

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