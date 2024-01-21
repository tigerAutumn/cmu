package cc.newex.dax.perpetual.dto.enums;

/**
 * Created by fang on 2017/10/24.
 */
public enum TransferTypeEnum {
    IN(1, "转入"),
    OUT(2, "转出");
    private int code;
    private String describe;

    TransferTypeEnum(int code, String describe) {
        this.code = code;
        this.describe = describe;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }
}
