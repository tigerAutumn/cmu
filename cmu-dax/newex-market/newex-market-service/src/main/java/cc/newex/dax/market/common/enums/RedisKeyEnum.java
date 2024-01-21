package cc.newex.dax.market.common.enums;


/**
 * @author newex-team
 * @date 2018/03/18
 * Redis key 1表示用，0表示弃用
 */
public enum RedisKeyEnum {

    REDIS_KEY_NEWRATEALL("newRateAll", "汇率详情"),

    REDIS_KEY_TICKETS("redis_key_tickets_rate", "指数，交易所详情"),

    REDIS_KEY_WHITE_IP("market_alert_white_ip", "ip白名单"),

    REDIS_KEY_MARKET_ALERT_PHONE("market_alert_phone", "手机号码白名单"),

    REDIS_KEY_COIN_CONFIG("redis_key_coin_config", "币对");

    private String key;
    private String name;

    RedisKeyEnum(String key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

}
