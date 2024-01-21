package cc.newex.dax.users.common.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * Face++ API相关配置
 *
 * @author liutiejun
 * @date 2018-10-30
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "newex.faceid")
public class FaceidProperties {

    private String apiKey;

    private String apiSecret;

    /**
     * 身份证OCR
     */
    private String ocridcardUrl;

    /**
     * 网页端人脸活体检测及比对服务
     */
    private String tokenUrl;

    /**
     * 活体结果反查功能
     */
    private String resultUrl;

    /**
     * 基于人脸比对的身份核实功能
     */
    private String verifyUrl;

    private Double precision;

    private Double edited;

    private String returnUrl;

    private String notifyUrl;

}
