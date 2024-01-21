package cc.newex.commons.openapi.specs.enums;

/**
 * @author newex-team
 * @date 2018-04-28
 */
public enum RateLimitStrategyEnum {
    /**
     * 按apiKey进行限流
     */
    API_KEY("apiKey"),

    /**
     * 按IP地址进行限流
     */
    IP("ip"),;

    private final String name;

    RateLimitStrategyEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}