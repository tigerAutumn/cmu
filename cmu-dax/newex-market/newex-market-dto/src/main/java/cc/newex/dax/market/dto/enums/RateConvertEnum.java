package cc.newex.dax.market.dto.enums;


/**
 * @author newex-team
 * @date 2018/03/18
 * 钱币兑换枚举 1表示可用 ，0表示不可用
 */
public enum RateConvertEnum {
    EUR_CNY("eur_cny", "1", "欧元对人民币"),
    USD_CNY("usd_cny", "1", "美元对人民币"),
    USD_JPY("usd_jpy", "1", "美元对日元"),
    USD_EUR("usd_eur", "1", "美元对欧元"),
    USD_RUB("usd_rub", "1", "美元对卢布"),
    USD_KRW("usd_krw", "1", "美元对韩元");

    private final String code;
    private final String name;
    private final String status;

    RateConvertEnum(final String code, final String status, final String name) {
        this.code = code;
        this.status = status;
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getCode() {
        return this.code;
    }

    public String getStatus() {
        return this.status;
    }

}
