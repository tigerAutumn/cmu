package cc.newex.wallet.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author newex-team
 * @data 29/03/2018
 */
@Slf4j
public class PropertyUtil {
    private static Properties props;

    static {
        PropertyUtil.loadProps();
    }

    static private void loadProps() {
        PropertyUtil.log.info("loading properties");
        PropertyUtil.props = new Properties();
        InputStream in = null;
        try {
            in = PropertyUtil.class.getClassLoader().getResourceAsStream("application.properties");

            PropertyUtil.props.load(in);
        } catch (final FileNotFoundException e) {
            PropertyUtil.log.error("application.properties not found");
        } catch (final IOException e) {
            PropertyUtil.log.error("IOException");
        } finally {
            try {
                if (null != in) {
                    in.close();
                }
            } catch (final IOException e) {
                PropertyUtil.log.error("application.properties IOException");
            }
        }
        PropertyUtil.log.info("load application.properties complete");
    }


    public static String getProperty(final String key) {
        if (null == PropertyUtil.props) {
            PropertyUtil.loadProps();
        }
        return PropertyUtil.props.getProperty(key);
    }

    public static String getProperty(final String key, final String defaultValue) {
        if (null == PropertyUtil.props) {
            PropertyUtil.loadProps();
        }
        return PropertyUtil.props.getProperty(key, defaultValue);
    }
}
