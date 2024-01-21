package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 交易流水表
 *
 * @author newex-team
 * @date 2018-11-30 10:10:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Pending {
    /**
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 订单id
     */
    private Long orderId;

    /**
     * 方向：1开多,2开空,3平多,4平空
     */
    private String side;

    /**
     * 对手方向：1开多,2开空,3平多,4平空
     */
    private String otherSide;

    /**
     * 成交数量
     */
    private BigDecimal amount;

    /**
     * 成交价：注意不是委托价
     */
    private BigDecimal price;

    /**
     * 成交额
     */
    private BigDecimal size;

    /**
     * 1部分成交 2完全成交
     */
    private Integer status;

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