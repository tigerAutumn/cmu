package cc.newex.commons.openapi.specs.consts;

import java.util.Arrays;
import java.util.List;

/**
 * Open Api权限类型
 *
 * @author newex-team
 * @date 2018/08/08
 */
public final class PermissionType {

    /**
     * 提现
     */
    public static final String WITHDRAW = "withdraw";

    /**
     * 交易
     */
    public static final String TRADE = "trade";

    /**
     * 只读
     */
    public static final String READ_ONLY = "readonly";

    /**
     * 私有接口权限
     */
    public static final String PRIVATE = "private";

    /**
     * 公共权限
     */
    public static final List<String> PUBLIC_PERMISSIONS = Arrays.asList(WITHDRAW, TRADE, READ_ONLY);
}
