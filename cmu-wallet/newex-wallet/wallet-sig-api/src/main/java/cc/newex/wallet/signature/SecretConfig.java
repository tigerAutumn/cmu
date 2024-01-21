package cc.newex.wallet.signature;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.net.URL;

/**
 * @author newex-team
 * @create 2018-11-27 下午2:13
 **/
@Slf4j
public class SecretConfig {

    private static Config conf;

    private static String aes = null;

    public static void init(final URL url) {
        SecretConfig.conf = ConfigFactory.load(ConfigFactory.parseURL(url));
    }

    public static void init(final String key) {
        if (StringUtils.isEmpty(key)) {
            return;
        }
        SecretConfig.aes = key;
    }

    private static String getValue(final String key) {
        if (SecretConfig.aes != null) {
            return SecretConfig.aes;
        }

        if (SecretConfig.conf != null && SecretConfig.conf.hasPath(key)) {
            return SecretConfig.conf.getString(key);
        }
        return null;
    }

    public static String decryptKey(final String data) {
        final String aesKey = SecretConfig.getValue("secret");
        if (StringUtils.isEmpty(aesKey)) {
            return null;
        }
        return AES.decrypt(data, aesKey);
    }
}
