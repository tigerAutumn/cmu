package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/5/30 17:03
 * @Description:
 */
public enum TransferStatusEnum {
    NOT_YET(0, "待处理"),
    PROCESSING(1, "处理中"),
    SUCCESS(2, "已处理");

    private int code;
    private String describe;

    TransferStatusEnum(final int code, final String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(final int code) {
        this.code = code;
    }

    public String getDescribe() {
        return this.describe;
    }

    public void setDescribe(final String describe) {
        this.describe = describe;
    }
}
