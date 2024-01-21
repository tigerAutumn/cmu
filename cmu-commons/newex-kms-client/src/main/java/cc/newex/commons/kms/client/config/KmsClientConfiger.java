package cc.newex.commons.kms.client.config;

import org.apache.commons.lang3.StringUtils;

/**
 * @author newex-team
 * @date 2018-07-23
 */
public class KmsClientConfiger {

    public static KmsClientConfig createDefaultAliyunKmsClientConfig() {
        final KmsClientConfig config = new KmsClientConfig();
        config.setVpcEndpoint("");
        config.setEndpoint("");
        config.setRegion("");
        config.setAccessKeyId("");
        config.setAccessKeySecret("");
        config.setKeyId("");
        return config;
    }

    public static KmsClientConfig createAliyunKmsClientConfig(final String vpcEndpoint, final String endpoint, final String region,
                                                              final String accessKeyId, final String accessKeySecret, final String keyId) {
        final KmsClientConfig config = createDefaultAliyunKmsClientConfig();
        setKmsClientConfig(config, vpcEndpoint, endpoint, region, accessKeyId, accessKeySecret, keyId);
        return config;
    }

    public static KmsClientConfig createDefaultAwsKmsClientConfig() {
        final KmsClientConfig config = new KmsClientConfig();
        config.setVpcEndpoint("");
        config.setEndpoint("kms.ap-southeast-1.amazonaws.com");
        config.setRegion("ap-southeast-1");
        config.setAccessKeyId("AKIAIH32BAQJEX2MFC6Q");
        config.setAccessKeySecret("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");
        config.setKeyId("arn:aws:kms:ap-southeast-1:865802994419:key/1b0f980f-fa0f-48d6-b963-bfe68902ea28");
        return config;
    }

    public static KmsClientConfig createAwsKmsClientConfig(final String vpcEndpoint, final String endpoint, final String region,
                                                           final String accessKeyId, final String accessKeySecret, final String keyId) {
        final KmsClientConfig config = createDefaultAwsKmsClientConfig();
        setKmsClientConfig(config, vpcEndpoint, endpoint, region, accessKeyId, accessKeySecret, keyId);
        return config;
    }

    private static void setKmsClientConfig(final KmsClientConfig config, final String vpcEndpoint, final String endpoint,
                                           final String region, final String accessKeyId, final String accessKeySecret, final String keyId) {
        config.setVpcEndpoint(StringUtils.defaultIfBlank(vpcEndpoint, config.getVpcEndpoint()));
        config.setEndpoint(StringUtils.defaultIfBlank(endpoint, config.getEndpoint()));
        config.setRegion(StringUtils.defaultIfBlank(region, config.getRegion()));
        config.setAccessKeyId(StringUtils.defaultIfBlank(accessKeyId, config.getAccessKeyId()));
        config.setAccessKeySecret(StringUtils.defaultIfBlank(accessKeySecret, config.getAccessKeySecret()));
        config.setKeyId(StringUtils.defaultIfBlank(keyId, config.getKeyId()));
    }
}
