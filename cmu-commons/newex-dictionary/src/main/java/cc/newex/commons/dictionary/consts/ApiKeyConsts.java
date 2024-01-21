package cc.newex.commons.dictionary.consts;

public class ApiKeyConsts {
    /**
     * OPEN API Key Cache Key 前缀
     */
    public static final String OPEN_API_KEY_PREFIX = "newex:users:api_key:";

    /**
     * OPEN API Key's UserId Cache Key 前缀
     */
    public static final String OPEN_API_KEY_USER_ID_PREFIX = OPEN_API_KEY_PREFIX + "user_id:";

    /**
     * OPEN API ip 限流 Key
     */
    public static final String OPEN_API_KEY_IP_RATE_LIMIT_KEY = OPEN_API_KEY_PREFIX + "ip_rate_limit_key";

    /**
     * OPEN API ip 限流 前缀
     */
    public static final String OPEN_API_KEY_IP_RATE_LIMIT_PREFIX = OPEN_API_KEY_PREFIX + "ip_rate_limit:";

    /**
     * OPEN API ip 限流 前缀
     */
    public static final String OPEN_API_KEY_IP_RATE_LIMIT_INFO = OPEN_API_KEY_PREFIX + "ip_rate_limit_info:";
}
