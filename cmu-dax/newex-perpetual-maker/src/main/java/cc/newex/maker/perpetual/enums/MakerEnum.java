package cc.newex.maker.perpetual.enums;

/**
 * maker枚举
 *
 * @author newex-team
 * @date 2018-12-17
 */
public enum MakerEnum {

    FBTCUSD("XBTUSD", PlatformEnum.BITMEX, TradeConfEnum.FBTCUSD, DepthConfEnum.FBTCUSD, AccountGroupEnum.GROUP_1),;

    /**
     * 在其他平台的币对名称
     */
    private final String alias;

    /**
     * 平台
     */
    private final PlatformEnum platform;

    /**
     * 做量配置信息
     */
    private final TradeConfEnum tradeConfEnum;

    /**
     * 做深度配置信息
     */
    private final DepthConfEnum depthConfEnum;

    private final AccountGroupEnum accountGroupEnum;

    MakerEnum(final String alias, final PlatformEnum platform, final TradeConfEnum tradeConfEnum, final DepthConfEnum depthConfEnum, final AccountGroupEnum accountGroupEnum) {
        this.alias = alias;
        this.platform = platform;
        this.tradeConfEnum = tradeConfEnum;
        this.depthConfEnum = depthConfEnum;
        this.accountGroupEnum = accountGroupEnum;
    }

    public String getAlias() {
        return this.alias;
    }

    public PlatformEnum getPlatform() {
        return this.platform;
    }

    public TradeConfEnum getTradeConfEnum() {
        return this.tradeConfEnum;
    }

    public DepthConfEnum getDepthConfEnum() {
        return this.depthConfEnum;
    }

    public AccountGroupEnum getAccountGroupEnum() {
        return this.accountGroupEnum;
    }
}
