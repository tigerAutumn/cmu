package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/6/16 14:03
 * @Description:
 */
public enum DeliveryTypeEnum {
    DELIVERY(1, "交割"),
    SETTLMETN(2, "清算");
    private int code;
    private String name;

    DeliveryTypeEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
