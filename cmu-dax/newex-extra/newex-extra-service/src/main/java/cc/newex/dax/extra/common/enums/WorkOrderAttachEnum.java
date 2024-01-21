package cc.newex.dax.extra.common.enums;

/**
 * @author newex-team
 * @date 2018/6/21
 */
public enum WorkOrderAttachEnum {
    //用户回复
    WORK_ORDER_ATTACH_USER(0),
    //客服回复
    WORK_ORDER_ATTACH_CUSTOMER(1);

    private final int code;

    WorkOrderAttachEnum(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
