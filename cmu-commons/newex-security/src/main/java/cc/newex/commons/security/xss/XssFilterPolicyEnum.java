package cc.newex.commons.security.xss;

/**
 * XssFilter过滤策略枚举
 *
 * @author newex-team
 * @date 2017/12/09
 */
public enum XssFilterPolicyEnum {
    /**
     * Cal Xss Filter
     */
    CAL("cal"),

    /**
     * Owasp Xss Filter
     */
    OWASP("owasp"),;

    private final String name;

    XssFilterPolicyEnum(final String name) {
        this.name = name;
    }

    /**
     * 枚举名称
     *
     * @return 枚举名称
     */
    public String getName() {
        return this.name;
    }
}
