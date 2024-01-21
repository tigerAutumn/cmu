package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 对账流水表
 *
 * @author newex-team
 * @date 2018-11-20 18:27:42
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SystemBill {
    /**
     * 系统手续费
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 手续费对应的币种，可能是币种，可能是点卡
     */
    private String feeCurrencyCode;

    /**
     * 手续费: 正表示付手续费,负表示得手续费
     */
    private BigDecimal fee;

    /**
     * 该笔交易对应的盈亏: 正表示盈利,负表示亏损
     */
    private BigDecimal profit;

    /**
     * user_bill的uid
     */
    private String uId;

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