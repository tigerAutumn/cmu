package cc.newex.commons.openapi.support.utils;

import cc.newex.commons.openapi.support.constant.RegExp;
import org.apache.commons.lang3.StringUtils;

import java.util.Optional;

/**
 * Regular Utils.
 * Created by newex-team on 2017/7/4 10:36. <br/>
 */
public class RegularUtils {

    /**
     * is SecretKey?
     *
     * @param secretKey secretKey
     * @return true  check pass
     */
    public static boolean isSecretKey(final String secretKey) {
        return Optional.ofNullable(secretKey)
                .map(sk -> sk.matches(RegExp.SECRET_KEY))
                .orElse(false);
    }

    /**
     * is ApiKey
     *
     * @param apiKey apiKey
     * @return true   check pass
     */
    public static boolean isApiKey(final String apiKey) {
        return StringUtils.isNoneBlank(apiKey);
    }

    /**
     * is UNIX time timestamp ISO 8601 norm
     *
     * @param unixTime eg: 2018-02-03T05:34:14.110Z
     * @return true  check pass
     */
    public static boolean isUnixISOTime(final String unixTime) {
        return Optional.ofNullable(unixTime)
                .map(ut -> ut.matches(RegExp.UNIX_ISO_TIME))
                .orElse(false);
    }

    /**
     * is Epoch_Second
     *
     * @param epochSecond Unix Epoch in UTC
     * @return true  check pass
     */
    public static boolean isEpochSecond(final String epochSecond) {
        return Optional.ofNullable(epochSecond)
                .map(es -> es.matches(RegExp.EPOCH_SECOND))
                .orElse(false);
    }

    /**
     * is  epoch second decimal
     *
     * @param epochSecondDecimal Unix Epoch in UTC decimal values
     * @return true  check pass
     */
    public static boolean isEpochSecondDecimal(final String epochSecondDecimal) {
        return Optional.ofNullable(epochSecondDecimal)
                .map(esd -> esd.matches(RegExp.EPOCH_TIME_DECIMAL))
                .orElse(false);
    }

    /**
     * is  epoch Micro-Seconds
     *
     * @param epochMicroSeconds Unix Epoch in UTC Micro-Seconds values
     * @return true  check pass
     */
    public static boolean isEpochMicroSeconds(final String epochMicroSeconds) {
        return Optional.ofNullable(epochMicroSeconds)
                .map(esd -> esd.matches(RegExp.EPOCH_MICRO_SECONDS))
                .orElse(false);
    }
}
