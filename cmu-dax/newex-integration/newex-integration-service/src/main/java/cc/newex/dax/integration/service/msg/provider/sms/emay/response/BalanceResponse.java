package cc.newex.dax.integration.service.msg.provider.sms.emay.response;

import java.io.Serializable;


/**
 * 余额数据
 *
 * @author Frank
 */
public class BalanceResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private long balance;// 余额


    public BalanceResponse() {

    }

    public BalanceResponse(final long balance) {
        this.balance = balance;
    }

    public long getBalance() {
        return this.balance;
    }

    public void setBalance(final long balance) {
        this.balance = balance;
    }

}
