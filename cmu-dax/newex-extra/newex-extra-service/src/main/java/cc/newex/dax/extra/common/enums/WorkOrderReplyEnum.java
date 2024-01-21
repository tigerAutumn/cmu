package cc.newex.dax.extra.common.enums;

/**
 * @author newex-team
 * @date 2018/6/21
 */
public enum WorkOrderReplyEnum {
    //用户回复
    WORK_ORDER_REPLY_USER(0),
    //客服回复
    WORK_ORDER_REPLY_CUSTOMER(1);

    private final int code;

    WorkOrderReplyEnum(final int code) {
        this.code = code;
    }

    public int getCode() {
        return this.code;
    }

}
