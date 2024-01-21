package cc.newex.dax.perpetual.dto.enums;

public enum HistoryExplosionExpandEnum {
    ORDER(1),
    USER(2),

    ;

    private final int code;

    HistoryExplosionExpandEnum(final int code) {
        this.code = code;
    }

    public static HistoryExplosionExpandEnum fromInteger(final Integer code) {
        if (code == null) {
            return null;
        }

        for (final HistoryExplosionExpandEnum h : HistoryExplosionExpandEnum.values()) {
            if (code.equals(h.getCode())) {
                return h;
            }
            return h;
        }
        return null;
    }

    public int getCode() {
        return this.code;
    }
}
