package cc.newex.dax.asset.common.enums;

/**
 * @author newex-team
 * @data 2018/06/05
 */

public enum AddressType {
    /**
     * 充值
     */
    DEPOSIT(1),
    /**
     * 提现
     */
    WITHDRAW(2);

    private final int code;

    AddressType(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
