package cc.newex.dax.users.domain.behavior.enums;

/**
 * 用户行为项枚举
 *
 * @author newex-team
 * @date 2018/04/12
 */
public enum BehaviorItemEnum {
    /**
     * google code相关项
     */
    GOOGLE("google"),
    /**
     * 手机验证相关项
     */
    MOBILE("mobile"),
    /**
     * 邮箱验证相关项
     */
    EMAIL("email");

    private final String name;

    BehaviorItemEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
