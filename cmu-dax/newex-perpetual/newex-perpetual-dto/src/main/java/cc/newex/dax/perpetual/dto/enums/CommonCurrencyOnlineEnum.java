package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/5/30 17:03
 * @Description:
 */
public enum CommonCurrencyOnlineEnum {
    NO_USRING    (0, "未上线不可用状态"),
    ONLINE_USING (1, "线上可用状态"),
    BETA         (2, "内部测试状态");

    private String  name;
    private Integer code;

    CommonCurrencyOnlineEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Integer getCode() {
        return code;
    }

}






