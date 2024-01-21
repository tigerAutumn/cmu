package cc.newex.dax.asset.common.enums;

/**
 * @author newex-team
 *
 */

public enum TransferAuditStatus {
    /**
     * 等待审核
     */
    WAITING(0),
    /**
     * 审核通过
     */
    SUCCEED(1),

    /**
     * 审核失败
     */
    FAIL(2),

    /**
     * 提现进行中
     */
    JOB_OPER(3);


    private final int code;

    TransferAuditStatus(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
