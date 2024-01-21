package cc.newex.dax.asset.common.enums;

public enum LockPositionConfType {
    /**
     * 具体时间
     */
    DETAIL_TIME_TO_RELEASE(1),
    /**
     * 周期
     */
    CYCLE_RELEASE_TIME(2);

    private final int code;

    LockPositionConfType(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
