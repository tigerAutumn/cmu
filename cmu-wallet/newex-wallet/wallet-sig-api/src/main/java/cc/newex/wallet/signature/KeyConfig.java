package cc.newex.wallet.signature;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

/**
 * @author newex-team
 * @create 2018-11-27 下午2:13
 **/
@Slf4j
public class KeyConfig {

    private static String masterNode;
    private SecretConfig secretConfig;

    public static void init(final String key) {
        KeyConfig.masterNode = key;
    }

    public static String getValue(final String key) {
        if (StringUtils.isNotBlank(key)) {
            return KeyConfig.masterNode;
        }
        return null;
    }
}
