package cc.newex.dax.market.common.util;

import cc.newex.commons.lang.util.MD5Util;
import org.apache.commons.lang3.StringUtils;

public class DataKeyUtils {
    public static boolean validateKey(final String key, final String dataKey) {
        if (StringUtils.isEmpty(key)) {
            return false;
        }
        return MD5Util.getMD5String(dataKey).equals(key);
    }
}
