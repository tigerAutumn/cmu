package cc.newex.dax.boss.web.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author allen
 * @date 2018/6/6
 * @des
 */
@Data
@Component
@ConfigurationProperties(prefix = "newex.fileupload.s3")
public class FillUploadConfig {

    /**
     * extra file path
     */
    private String extraPath;
}
