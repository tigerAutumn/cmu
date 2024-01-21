package cc.newex.dax.perpetual.dto.enums;

/**
 * @author harry
 * @Date: 2018/5/30 17:03
 * @Description:
 */
public enum OrderFromEnum {

    //网页
    WEB_PAGE_ORDER(0, "WEB_PAGE_ORDER"),
    //手机网页
    WEB_PAGE_PHONE_ORDER(1, "WEB_PAGE_PHONE_ORDER"),
    //API
    API_ORDER(2, "API_ORDER"),
    //IOS客户端
    CLIENT_IOS_ORDER(3, "CLIENT_IOS_ORDER"),
    //ANDROID客户端
    CLIENT_ANDROID_ORDER(4, "CLIENT_ANDROID_ORDER"),
    //PC客户端
    CLIENT_PC_ORDER(5, "CLIENT_PC_ORDER"),
    //计划委托
    PLAN_ORDER(6, "PLAN_ORDER"),
    //跟踪委托
    STRATEGY_TRACK_ORDER(7, "STRATEGY_TRACK_ORDER"),
    //冰山委托
    STRATEGY_CONTINUOUS_ORDER(8, "STRATEGY_CONTINUOUS_ORDER"),
    //时间加权委托
    STRATEGY_INITIATIVE_ORDER(9, "STRATEGY_INITIATIVE_ORDER"),
    ;

    private int code;

    private String desc;

    OrderFromEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return this.desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static OrderFromEnum valueOf(int code) {
        for (OrderFromEnum fromEnum : values()) {
            if (fromEnum.getCode() == code) {
                return fromEnum;
            }
        }
        return WEB_PAGE_ORDER;
    }
}
