package cc.newex.commons.ucenter.consts;

/**
 * @author newex-team
 * @date 2018-06-27
 */
public class SessionConsts {
    /**
     * Default 10 minutes expired
     */
    public static final int DEFAULT_MAX_INACTIVE_INTERVAL_SECONDS = 600;

    /**
     * Session default namespace in container(redis,memcached or map)
     */
    public static final String DEFAULT_NAMESPACE = "newex:session:";

    /**
     * Session expired max inactive interval (default 30 seconds)
     */
    public static final String SESSION_MAX_INACTIVE_INTERVAL = DEFAULT_NAMESPACE + "max_inactive_interval";

    /**
     * Prefix of Session Id("newex:session:id:")
     */
    public static final String SESSION_ID_PREFIX = DEFAULT_NAMESPACE + "id:";

    /**
     * Prefix of Session User Id ("newex:session:user_id:")
     */
    public static final String SESSION_USER_ID_PREFIX = DEFAULT_NAMESPACE + "user_id:";
}
