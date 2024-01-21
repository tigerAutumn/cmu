package cc.newex.springcloud.config.encryption;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.encryption.EnvironmentEncryptor;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 由于{@link org.springframework.cloud.config.server.config.EncryptionAutoConfiguration 的配置项不生效，所有才自定义此类}
 */
@Slf4j
public class NotRemoveCipherEnvironmentEncryptor implements EnvironmentEncryptor {
    public NotRemoveCipherEnvironmentEncryptor() {
    }

    @Override
    public Environment decrypt(final Environment environment) {
        final Environment result = new Environment(environment);
        for (final PropertySource source : environment.getPropertySources()) {
            final Map<Object, Object> map = new LinkedHashMap<>(source.getSource());
            result.add(new PropertySource(source.getName(), map));
        }
        return result;
    }
}
