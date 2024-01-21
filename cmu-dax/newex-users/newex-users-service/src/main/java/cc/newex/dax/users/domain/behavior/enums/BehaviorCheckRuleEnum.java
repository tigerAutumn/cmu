package cc.newex.dax.users.domain.behavior.enums;

/**
 * 用户行为审核规则分类
 *
 * @author newex-team
 * @date 2018/04/12
 */
public enum BehaviorCheckRuleEnum {
    /**
     * 全部参数都参与
     */
    ALL("all"),

    /**
     * 只选择单个参与
     */
    SINGLE("single");

    private final String name;

    BehaviorCheckRuleEnum(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
