package cc.newex.maker.utils;

import lombok.extern.slf4j.Slf4j;

/**
 * @author cmx-sdk-team
 * @date 2019-04-09
 */
@Slf4j
public class SimpleThreadUtils {

    public static void sleep(final long millis) {
        try {
            Thread.sleep(millis);
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
