package cc.newex.dax.perpetual.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单归档表
 *
 * @author newex-team
 * @date 2018-09-26 17:27:27
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class HistoryOrders {
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
     * 是Base和quote之间的组合 P_BTC_USD
     */
    private String pairCode;

    /**
     * 0:下单 1:撤单
     */
    private Integer clazz;

    /**
     * 1.开多 2.开空 3.平多 4.平空
     */
    private Integer type;

    /**
     * 委托数量
     */
    private BigDecimal amount;

    /**
     * 一张对应的美元数
     */
    private BigDecimal unitAmount;

    /**
     * 用户订单委托价格
     */
    private BigDecimal orderPrice;

    /**
     * 平均成交价
     */
    private BigDecimal dealPriceAvg;

    /**
     * 已成交数量
     */
    private BigDecimal dealAmount;

    /**
     * 手续费率
     */
    private BigDecimal feeRate;

    /**
     * 手续费
     */
    private BigDecimal fee;

    /**
     * 杠杆
     */
    private Integer leverage;

    /**
     * 0:未处理 1:已处理
     */
    private Integer operateStatus;

    /**
     * 0 等待成交 1 部分成交 2 已经成交 -1 已经撤销
     */
    private Integer status;

    /**
     * 下单来源 0网页下单,2openApi,3IOS,4android
     */
    private Integer orderFrom;

    /**
     * 0:普通 1:交割 2:强平 3:强减 4:全平 5:系统反单
     */
    private Integer systemType;

    /**
     * 订单关联id
     */
    private Long relationOrderId;

    /**
     * 创建时间
     */
    private Date createdDate;

    /**
     * 修改时间
     */
    private Date modifyDate;
}