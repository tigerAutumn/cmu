package cc.newex.dax.perpetual.dto.enums;

public enum DeliverySwitchEnum {

    CLOSE(0, "合约交割关闭"),
    OPEN(1, "合约交割打开"),;

    private final int code;
    private final String desc;

    DeliverySwitchEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static DeliverySwitchEnum fromInteger(Integer code) {
        if (code == null) {
            return null;
        }

        for (DeliverySwitchEnum u : DeliverySwitchEnum.values()) {
            if (u.code == code) {
                return u;
            }
        }
        return null;
    }

    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
