package cc.newex.dax.perpetual.dto.enums;

import lombok.Getter;

@Getter
public enum OrderLeverEnum {

    SMALL(0, "小额订单"),
    BIG(1, "大额订单"),;

    private int code;
    private String desc;

    OrderLeverEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static OrderLeverEnum fromInteger(Integer code) {
        if (code == null) {
            return null;
        }
        for (OrderLeverEnum o : values()) {
            if (code.equals(o.getCode())) {
                return o;
            }
        }
        return null;
    }

}
