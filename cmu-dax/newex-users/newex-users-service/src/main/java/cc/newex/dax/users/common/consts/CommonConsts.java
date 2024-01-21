package cc.newex.dax.users.common.consts;

import org.springframework.stereotype.Component;

/**
 * @author newex-team
 * @date 2018/03/18
 */
@Component
public class CommonConsts {
    /**
     * AES加密密钥
     */
    public static final String ASE_KEY = "ZwwXGHRDUy!wUWgv";
    /**
     * 一分钟内用过的googleCode前缀
     */
    public static String VALID_GOOGLE_CODE = "GOOGLE_CODE_%s_%s";
    /**
     * httpRequest中存储的当前登录用户对应的brokerId
     */
    public static final String CURRENT_USER_BROKER_ID = "current_user_broker_id";
}
