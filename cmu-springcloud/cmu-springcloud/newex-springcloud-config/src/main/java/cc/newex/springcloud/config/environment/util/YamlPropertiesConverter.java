package cc.newex.springcloud.config.environment.util;

import cc.newex.springcloud.config.environment.Constants;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.CollectionFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * @author newex-team
 * @date 2018-07-19
 */
public class YamlPropertiesConverter extends AbstractYamlProcessor {
    /**
     * @param in
     * @return
     */
    public Properties getProperties(final InputStream in) {
        final Properties result = CollectionFactory.createStringAdaptingProperties();
        this.process((properties, map) -> result.putAll(properties), in);
        return result;
    }

    /**
     * @param suffix
     * @param in
     * @return
     */
    public Properties getProperties(final String suffix, final InputStream in) {
        try {
            if (StringUtils.equalsIgnoreCase(suffix, Constants.PROPERTIES_FILE_SUFFIX)) {
                final Properties properties = new Properties();
                properties.load(in);
                return properties;
            }
            return this.getProperties(in);
        } catch (final Exception ex) {
            throw new RuntimeException("load properties error", ex);
        }
    }
}
