package cc.newex.commons.kms.client;

import cc.newex.commons.kms.client.config.KmsClientConfig;

/**
 * @author newex-team
 * @date 2018-07-23
 */
public class KmsClientFactory {

    public static AliyunKmsClient createAliyunKmsClient(final KmsClientConfig config) {
        return new AliyunKmsClient(
                config.getVpcEndpoint(),
                config.getRegion(),
                config.getAccessKeyId(),
                config.getAccessKeySecret(),
                config.getKeyId()
        );
    }

    public static AwsKmsClient createAwsKmsClient(final KmsClientConfig config) {
        return new AwsKmsClient(
                config.getEndpoint(),
                config.getRegion(),
                config.getAccessKeyId(),
                config.getAccessKeySecret(),
                config.getKeyId()
        );
    }
}
