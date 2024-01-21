package cc.newex.dax.perpetual.domain.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class AssetsBalanceBean {
    /**
     * 合约
     */
    private String contractCode;
    /**
     * 币种
     */
    private String currencyCode;
    /**
     * 是否测试币 0:线上币,1:测试币
     */
    private Integer env;

    /**
     * 可用余额
     */
    private String availableBalance = "0";
    /**
     * 已实现盈余
     */
    private String realizedSurplus = "0";

    /**
     * 挂单保证金
     */
    private String orderMargin = "0";
    /**
     * 开仓保证金
     */
    private String positionMargin = "0";
    /**
     * BTC估值
     */
    private String valuation = "0";
    /**
     * 提现：false：不是提现货币，true:提现货币
     */
    private Boolean withdrawable;

    /**
     * 充值：false：不是充值货币，true：充值货币
     */
    private Boolean rechargeable;

    /**
     * 可兑换：false：不可兑换，true：可以兑换
     */
    private Boolean exchange;

    /**
     * 可划转 0:不可划转，1 可划转
     */
    private Integer transfer;
}
