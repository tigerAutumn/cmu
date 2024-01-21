package cc.newex.dax.perpetual.domain.bean;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 币种表
 *
 * @author newex-team
 * @date 2018/11/01
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Currency {

    private static final Byte ONLINE = 1;

    private static final Byte OFFLINE = 0;

    /**
     * 币种id
     */
    private Integer id;

    /**
     * 币种代号 例:CNY BTC LTC
     */
    private String symbol;

    /**
     * 币的符号
     */
    private String sign;

    /**
     * 提现：false：不是提现货币，true:提现货币
     */
    private Boolean withdrawable;

    /**
     * 充值：false：不是充值货币，true：充值货币
     */
    private Boolean rechargeable;

    /**
     * 可兑换
     */
    private Boolean exchange;

    /**
     * 可领取
     */
    private Boolean receive;

    /**
     * 可划转 0:不可划转，1 可划转
     */
    private Integer transfer;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 是否上线
     */
    private Byte online;

    /**
     * 冗余币种id，currencyId，方便前端命名
     */
    private Integer currencyId;

    /**
     * 杠杆利息配置
     */
    private Double marginInterestRate;

    /**
     * 币 全名
     */
    private String fullName;

    /**
     * 过期时间
     */
    private Long expireDate;

    /**
     * 券商ID
     */
    private Integer brokerId;

    public boolean online() {
        return this.online != null && ONLINE.equals(this.online);
    }

    public boolean offline() {
        return this.online == null || OFFLINE.equals(this.online);
    }

}