package cc.newex.dax.asset.common.enums;

/**
 * @author newex-team
 * @data 06/04/2018
 */

public enum LockPositionStatus {
    /**
     * 等待处理
     */
    WAITING(0),
    /**
     * 更新成功
     */
    PREPARED(1),

    /**
     * 解锁完成
     */
    SENDING(2);

    private final int code;

    LockPositionStatus(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
