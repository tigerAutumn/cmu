package cc.newex.dax.market.spider.common.enums;

/**
 * 币行名
 *
 * @author newex-team
 * @date 2018/03/18
 **/
public enum CoinNameEnum {

    HUOBI("huobi"),
    BTCC("btcc"),
    BTCE("BTCE-e"),
    BTCCUSD("BTCC-USD"),
    BITSTAMP("Bitstamp"),
    BITCOIN("Bitcoin"),
    ANXBTC("Anxbtc"),
    BTCFINEX("BitFinex"),
    BTCTRADE("btcTrade"),
    COINBASE("GDAX"),
    KRAKEN("kraken"),
    OKCOINCN("OKCoinCN"),
    OKCOINCOM("OKCoin"),
    ITBIT("itBit"),
    HUOBI22HUORSVOL("huobi22HoursVol"),
    EURCNY("eur_cny"),
    USDCNY("usd_cny"),
    OREPOOL("orePool"),
    GEMINI("gemini"),
    BITTREX("Bittrex"),
    POLONIEX("Poloniex"),
    BITHUMB("Bithumb"),
    OKEX("OKEX"),
    HITBTC("Hitbtc"),
    BINANCE("binance"),
    UPBIT("upbit"),
    ZB("zb"),
    BIBOX("bibox"),
    GATEIO("gateio"),
    EXMO("exmo"),
    COINMEX("coinmex");

    public final String name;

    CoinNameEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
