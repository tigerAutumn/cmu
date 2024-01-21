package cc.newex.commons.spring.cloud.config.kms;

import cc.newex.commons.kms.client.AliyunKmsClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.util.Assert;

/**
 * This {@link TextEncryptor} uses Aliyun KMS (Key Management Service) to encrypt / decrypt strings. Encoded cipher strings
 * are represented in Base64 format, to have a nicer string representation (only alpha-numeric chars), that can be
 * easily used as values in property files.
 */
@Slf4j
public class AliyunKmsTextEncryptor implements TextEncryptor {

    private final AliyunKmsClient kmsClient;

    /**
     * @param kms The Aliyun KMS client
     */
    public AliyunKmsTextEncryptor(final AliyunKmsClient kms) {
        Assert.notNull(kms, "KMS client must not be null");
        this.kmsClient = kms;
    }

    @Override
    public String encrypt(final String text) {
        return this.kmsClient.encryptAsBase64(text);
    }

    @Override
    public String decrypt(final String encryptedText) {
        return this.kmsClient.decryptAsBase64(encryptedText);
    }
}
