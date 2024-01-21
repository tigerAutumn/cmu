package cc.newex.dax.perpetual.domain;

import java.math.BigDecimal;
import java.util.Date;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 账单
 *
 * @author newex-team
 * @date 2018-11-20 18:27:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserBill {
    /**
     * 账单表
     */
    private Long id;

    /**
     * 唯一id，system_bill使用uid关联user_bill
     */
    private String uId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 是Base和Quote之间的组合 P_BTC_USDT，R_BTC_USDT1109
     */
    private String contractCode;

    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 类型（11.充值 12.提现 13.转入 14.转出 15.多/买 16.空/卖 17.系统收取手续费 18.保险金 19.结算 20.穿仓对敲）
     */
    private Integer type;

    /**
     * 交易类型 1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short
     */
    private String detailSide;

    /**
     * 成交张数
     */
    private BigDecimal amount;

    /**
     * 成交价
     */
    private BigDecimal price;

    /**
     * 成交金额
     */
    private BigDecimal size;

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
     * 前余额
     */
    private BigDecimal beforePosition;

    /**
     * 后余额
     */
    private BigDecimal afterPosition;

    /**
     * 前余额
     */
    private BigDecimal beforeBalance;

    /**
     * 后余额
     */
    private BigDecimal afterBalance;

    /**
     * 1:maker 2:taker
     */
    private Integer makerTaker;

    /**
     * 账单关联记录 id
     */
    private Long referId;

    /**
     * 钱包的交易编码
     */
    private String tradeNo;

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