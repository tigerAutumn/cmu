package cc.newex.wallet.currency;

/**
 * 币种的上线状态：0-下线，1-上线
 *
 * @author liutiejun
 * @date 2019-05-24
 */
public enum CurrencyOnlineEnum {
    /**
     * 0-下线，1-上线
     */
    OFFLINE(0, "offline"),
    /**
     * 0-下线，1-上线
     */
    ONLINE(1, "online"),;

    private final int code;
    private final String name;

    CurrencyOnlineEnum(final int code, final String name) {
        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }
}
