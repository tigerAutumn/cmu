package cc.newex.commons.openapi.support.constant;

/**
 * Regular Expression.
 * Created by newex-team on 2017/7/4 10:32. <br/>
 */
public final class RegExp {
    /**
     * 加密secretKey
     */
    public static final String SECRET_KEY = "^[A-Z0-9]{32}$";
    /**
     * UUID
     */
    public static final String UUID = "[0-9a-z]{8}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{4}-[0-9a-z]{12}";

    /**
     * Epoch Second Unix Epoch in UTC
     */
    public static final String EPOCH_SECOND = "^\\d{10}$";

    /***
     * UNIX time timestamp ISO 8601 norm 2018-02-03T05:34:14.110Z
     */
    public static final String UNIX_ISO_TIME = "\\d{4}-\\d{2}-\\d{2}T\\d{2}:\\d{2}:\\d{2}.\\d{3}Z";
    /**
     * epoch time Decimal   1517662142.557
     */
    public static final String EPOCH_TIME_DECIMAL = "\\d{10}\\.\\d{1,3}";
    /**
     * epoch time micro-seconds 1533640104676
     */
    public static final String EPOCH_MICRO_SECONDS = "\\d{13}";


}
