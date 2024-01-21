package cc.newex.dax.extra.common.enums;

/**
 * @author newex-team
 * @date 2018/6/21
 */
public enum WorkOrderSourceEnum {

    //系统派发
    WORK_ORDER_SOURCE_SYSTEM(0),

    //客服转发
    WORK_ORDER_SOURCE_TRANSPOND(1);

    private final int code;

    WorkOrderSourceEnum(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }
}
