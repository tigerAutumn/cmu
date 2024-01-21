package cc.newex.dax.asset.common.enums;

/**
 * @author newex-team
 * @data 06/04/2018
 */

public enum TransferStatus {
    /**
     * 等待处理
     */
    WAITING(0),
    /**
     * 准备发送交易
     */
    PREPARED(1),

    /**
     * 交易发送中
     */
    SENDING(2),
    /**
     * 已经生成tx_id
     */
    CONFIRMED(3),

    /**
     * 由于充值数量不够最小充值数，充值不到账
     */
    DEPOSIT_NOT_BIZ(4),

    /**
     * 超出提币额度，需要人工审核
     */
    AUDIT(5);

    private final int code;

    TransferStatus(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
