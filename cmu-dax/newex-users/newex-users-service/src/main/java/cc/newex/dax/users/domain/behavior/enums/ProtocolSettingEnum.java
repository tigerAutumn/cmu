package cc.newex.dax.users.domain.behavior.enums;

/**
 * 用户协议类型枚举
 *
 * @author newex-team
 * @date 2018/9/4
 */
public enum ProtocolSettingEnum {
    /**
     * 现货
     */
    SPOT(1000, "spot"),

    /**
     * c2c
     */
    C2C(2000, "c2c"),

    /**
     * 组合操作
     */
    PORTFOLIO(3000, "portfolio"),

    /**
     * 永续合约
     */
    PERPETUAL(4000, "perpetual");

    /**
     * 操作类别标识
     */
    private final int id;

    /**
     * 操作类别名称
     */
    private final String name;

    ProtocolSettingEnum(final int id, final String name) {
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