package cc.newex.dax.extra.common.enums;

public enum OpLogTypeEnum {
    /**
     * 客服操作日志
     */
    WORK_ORDER_TRANSFER(0, "订单转发"),
    WORK_ORDER_WAIT(1, "置为等待"),
    WORK_ORDER_COMPLETE(2, "订单已解决");
    private final String msg;
    private final int code;

    OpLogTypeEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static OpLogTypeEnum setOpEnum(int index) {
        for (OpLogTypeEnum eu : OpLogTypeEnum.values()) {
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
