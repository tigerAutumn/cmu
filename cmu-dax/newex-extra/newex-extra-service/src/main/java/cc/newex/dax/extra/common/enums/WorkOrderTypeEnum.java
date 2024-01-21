package cc.newex.dax.extra.common.enums;

public enum WorkOrderTypeEnum {
    //用户
    WORK_ORDER_BY_USER(0),
    //客服
    WORK_ORDER_BY_CUSTOMER(1);

    private final int code;

    WorkOrderTypeEnum(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
