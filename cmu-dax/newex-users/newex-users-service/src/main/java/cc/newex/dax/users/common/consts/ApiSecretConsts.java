package cc.newex.dax.users.common.consts;

import java.util.Arrays;
import java.util.List;

public class ApiSecretConsts {

    public static final List<String> SUB_ACCOUNT_NAME_FILTER_LIST = Arrays.asList("融资", "融币", "杠杆", "配资");

    /**
     * 权限列表（按权限值顺序从大到小）
     */
    public static final String[][] ALL_AUTHORITY = {
            {"4", "auth_otc", "大宗交易"},
            {"2", "auth_withdraw", "提币"},
            {"1", "auth_trade", "交易"}
    };

    /**
     * 每个用户最大能够申请APIKey的数量
     */
    public static final int API_KEY_MAX_NUMBER = 50;
}
