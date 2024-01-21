package cc.newex.dax.users.domain.behavior.enums;

/**
 * 用户行为分类
 *
 * @author newex-team
 * @date 2018/04/12
 */
public enum BehaviorCategoryEnum {
    /**
     * 现货操作
     */
    SPOT(1000, "spot"),

    /**
     * 期货(合约)操作
     */
    FUTURES(2000, "futures"),

    /**
     * 用户中心操作
     */
    USERS(3000, "users"),

    /**
     * 资金管理(充值提现)操作
     */
    ASSET(4000, "asset"),

    /**
     * 行情图表(指数等)操作
     */
    MARKET(5000, "market");

    /**
     * 操作类别标识
     */
    private final int id;

    /**
     * 操作类别名称
     */
    private final String name;

    BehaviorCategoryEnum(final int id, final String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

}
