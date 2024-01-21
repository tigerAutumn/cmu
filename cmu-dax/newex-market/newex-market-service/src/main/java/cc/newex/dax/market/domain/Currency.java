package cc.newex.dax.market.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 币种表
 *
 * @author newex-team
 * @date 2018/03/18
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
     *
     */
    private String sign;
    /**
     * 0:不能提现 1:可提现
     */
    private Integer withdrawable;
    /**
     * 0:不能充值 1:可充值
     */
    private Integer rechargeable;
    /**
     * 提现确认次数
     */
    private Integer confirmSize;
    /**
     * 排序
     */
    private Integer sort;
    /**
     * 是否上线
     */
    private Byte online;

    public boolean online() {
        return online != null && ONLINE.equals(this.online);
    }

    public boolean offline() {
        return online == null || OFFLINE.equals(this.online);
    }
}