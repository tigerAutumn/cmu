package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/6/19 17:04
 * @Description:
 */
public enum OrderConditionEnum {


    WAINING_FOR_TRADE(0, "等待成交"),
    HAD_PLACE(1, "已经委托"),
    HAD_CANCEL(-1, "已经撤销"),
    CANCEL_FAILED(2, "撤销失败");
    private int code;
    private String name;

    OrderConditionEnum(int code, String name) {
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
