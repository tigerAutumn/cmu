package cc.newex.dax.asset.common.enums;

public enum CurrencyBizTypeEnum {

    CURRENCY_WITHDRAW_TRANSFER_FROZEN(1),
    CURRENCY_WITHDRAW_TRANSFER_UNFREEZE(0),

    CURRENCY_WITHDRAW_OPEN(1),
    CURRENCY_WITHDRAW_CLOSE(0),
    CURRENCY_TRANSFER_OPEN(1),
    CURRENCY_TRANSFER_CLOSE(0),
    ;
    private final int type;


    CurrencyBizTypeEnum(final int type) {
        this.type = type;
    }

    public int getType() {
        return this.type;
    }
}
