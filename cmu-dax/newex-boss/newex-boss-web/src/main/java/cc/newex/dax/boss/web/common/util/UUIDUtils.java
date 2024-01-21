package cc.newex.dax.boss.web.common.util;

import java.util.UUID;

/**
 * @author liutiejun
 * @date 2018-06-04
 */
public class UUIDUtils {

    public static String getUUID32() {
        final String uuid = UUID.randomUUID().toString().replace("-", "").toLowerCase();

        return uuid;
    }

}
