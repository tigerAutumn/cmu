package cc.newex.dax.perpetual.openapi.support.common;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @author allen
 * @date 2018/4/25
 * @des
 */
public class CheckPermission {

    public static boolean checkPermission(final List<String> list, final String type) {
        if (CollectionUtils.isEmpty(list)) {
            return false;
        }
        for (final String s : list) {
            if (StringUtils.equalsIgnoreCase(s, type)) {
                return true;
            }
        }
        return false;
    }
}
