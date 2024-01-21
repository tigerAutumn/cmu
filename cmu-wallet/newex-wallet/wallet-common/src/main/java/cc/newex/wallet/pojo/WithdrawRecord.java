package cc.newex.wallet.pojo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author newex-team
 * @date 2018-04-02
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class WithdrawRecord implements Serializable {
    /**
     *
     */
    private Integer id;
    /**
     *
     */
    private String txId;
    /**
     *
     */
    private String address;
    /**
     *
     */
    private Long userId;
    /**
     *
     */
    private BigDecimal balance;
    /**
     *
     */
    private Integer currency;
    /**
     *
     */
    private Integer biz;
    /**
     * 系统间交互的唯一标识，防止发送重复交易
     */
    private String withdrawId;
    /**
     * 0:提现中;1:签名中;2:已发送; 3:已确认
     */
    private Byte status;
    /**
     *
     */
    private Date createDate;
    /**
     *
     */
    private Date updateDate;
    /**
     *
     */
    private BigDecimal fee;

    @Override
    public boolean equals(final Object o) {
        if (o == this) {
            return true;
        } else if (!(o instanceof WithdrawRecord)) {
            return false;
        } else {
            final WithdrawRecord other = (WithdrawRecord) o;
            if (!other.canEqual(this)) {
                return false;
            } else {
                if (this.getWithdrawId().equals(other.getWithdrawId())) {
                    return true;
                }
                return false;
            }
        }
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = result * 59 + (this.withdrawId == null ? 43 : this.withdrawId.hashCode());
        return result;
    }

    protected boolean canEqual(final Object other) {
        return other instanceof WithdrawRecord;
    }
}