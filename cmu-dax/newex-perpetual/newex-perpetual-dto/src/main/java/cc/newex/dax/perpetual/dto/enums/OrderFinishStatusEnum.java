package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/6/22 14:19
 * @Description:
 */
public enum OrderFinishStatusEnum {
    CANCEL(1, "撤单"),
    COMPLETE(2, "完全成交"),
    ;

    private int code;
    private String name;

    OrderFinishStatusEnum(final int code, final String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(final int code) {
        this.code = code;
    }
}
