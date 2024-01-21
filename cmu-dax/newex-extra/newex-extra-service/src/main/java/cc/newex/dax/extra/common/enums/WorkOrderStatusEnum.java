package cc.newex.dax.extra.common.enums;

/**
 * @author newex-team
 * @date 2018/6/16
 */
public enum WorkOrderStatusEnum {


    WORK_ORDER_UN_ALLOT(0, "未分配"),
    WORK_ORDER_ALLOT(1, "已受理"),
    WORK_ORDER_DEALING(2, "处理中"),
    WORK_ORDER_UN_CONFIRMED(3, "待确认"),
    WORK_ORDER_COMPLETE(4, "已完成"),
    WORK_ORDER_PENDING(5, "等待中");
    private final int code;
    private final String msg;

    WorkOrderStatusEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static WorkOrderStatusEnum setOpEnum(int index) {
        for (WorkOrderStatusEnum eu : WorkOrderStatusEnum.values()) {
            if (index == eu.getCode()) {
                return eu;
            }
        }
        return null;
    }

    public int getCode() {
        return this.code;
    }

    public String getMsg() {
        return this.msg;
    }

}
