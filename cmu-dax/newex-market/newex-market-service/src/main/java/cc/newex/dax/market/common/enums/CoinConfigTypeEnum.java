package cc.newex.dax.market.common.enums;

/**
 * @author newex-team
 */
public enum CoinConfigTypeEnum {
    MARKET(0),
    PORTFOLIO(1);

    private final int code;

    CoinConfigTypeEnum(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
