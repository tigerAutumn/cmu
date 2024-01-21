package cc.newex.dax.market.dto.enums;

/**
 * Created by wj on 2018/7/26.
 */
public enum CoinConfigStatusEnum {
    ON_LINE(0, "上线"),

    PRE_TEST(1, "预发"),

    OFF_LINE(2, "下线");

    private int code;
    private String name;

    CoinConfigStatusEnum(int code, String name) {
        this.code = code;
        this.name = name;
    }

    public static CoinConfigStatusEnum getByCode(int statusCode) {
        for (CoinConfigStatusEnum coinConfigStatusEnum : CoinConfigStatusEnum.values()) {
            if (coinConfigStatusEnum.getCode() == statusCode) {
                return coinConfigStatusEnum;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
