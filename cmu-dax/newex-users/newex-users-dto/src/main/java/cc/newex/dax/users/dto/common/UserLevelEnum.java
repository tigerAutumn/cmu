package cc.newex.dax.users.dto.common;

/**
 * 用户等级枚举
 *
 * @author newex-team
 * @date 2018/7/7
 */
public enum UserLevelEnum {
    /**
     * 会员
     */
    VIP("vip", "会员", 10),
    /**
     * 天使
     */
    ANGEL("angel", "天使", 20),
    /**
     * 合伙人
     */
    PARTNER("partner", "合伙人", 50);

    private final String code;
    private final String name;
    private final Integer weight;

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public Integer getWeight() {
        return this.weight;
    }

    UserLevelEnum(final String code, final String name, final Integer weight) {
        this.code = code;
        this.name = name;
        this.weight = weight;
    }

    public static UserLevelEnum getInstance(final String code) {
        for (final UserLevelEnum userLevelEnum : UserLevelEnum.values()) {
            if (userLevelEnum.getCode().equalsIgnoreCase(code)) {
                return userLevelEnum;
            }
        }
        return null;
    }
}
