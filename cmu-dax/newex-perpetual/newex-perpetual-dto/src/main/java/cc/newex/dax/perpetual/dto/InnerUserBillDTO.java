package cc.newex.dax.perpetual.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class InnerUserBillDTO {
    /**
     * 委托时间
     */
    private Long createdDate;
    /**
     * 币种
     */
    private String currencyCode;

    /**
     * 交易类型 1.开多open_long 2.开空open_short 3.平多close_long 4.平空close_short
     */
    private String detailSide;
    /**
     * 类型（11.充值 12.提现 13.转入 14.转出 15.多/买 16.空/卖 17.系统收取手续费 18.保险金 19.结算 20.穿仓对敲）
     */
    private Integer type;
    /**
     * 成交数量
     */
    private String amount;
    /**
     * 成交金额
     */
    private String size;

    /**
     * 该笔交易对应的盈亏: 正表示盈利,负表示亏损
     */
    private String profit;

    /**
     * 余额
     */
    private String balance;

    /**
     * 手续费对应的币种，可能是币种，可能是点卡
     */
    private String feeCurrencyCode;

    /**
     * 手续费: 正表示付手续费,负表示得手续费
     */
    private String fee;

    /**
     * 主键
     */
    private Long id;

    /**
     * 用户id
     */
    private Long userId;
}
