package cc.newex.dax.perpetual.common.enums;

public enum PositionClearEnum {
    INIT(0),
    DEAL_BILL(1),;
    private final int code;

    PositionClearEnum(final int code) {
        this.code = code;
    }

    public static PositionClearEnum fromInteger(final Integer code) {
        if (code == null) {
            return null;
        }
        for (final PositionClearEnum p : PositionClearEnum.values()) {
            if (code.equals(p.getCode())) {
                return p;
            }
        }
        return null;
    }

    public int getCode() {
        return this.code;
    }
}
