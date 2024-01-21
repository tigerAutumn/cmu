package cc.newex.commons.openapi.specs.auth;

import cc.newex.commons.openapi.specs.enums.HmacAlgorithmEnum;

/**
 * @author newex-team
 * @date 2018-04-28
 */
public interface OpenApiAuthToken {
    /**
     * get api key from current request header [ACCESS-KEY]
     *
     * @return api key
     */
    String getAccessKey();

    /**
     * get apiKey Passphrase from current request header [ACCESS-PASSPHRASE]
     *
     * @return api key Passphrase
     */
    String getAccessPassphrase();

    /**
     * get validateSign from current request header [ACCESS-SIGN]
     *
     * @return validateSign of current request
     */
    String getAccessSign();

    /**
     * get timestamp from current request header [ACCESS-TIMESTAMP]
     *
     * @return current timestamp
     */
    String getAccessTimestamp();

    /**
     * get api key prefix
     *
     * @return api key prefix
     */
    String getPrefix();

    /**
     * set api key  secret by signature
     *
     * @param secret api key secret
     */
    void setApiSecret(String secret);

    /**
     * if api key is freeze then it whether available. default true.
     *
     * @return true|false
     */
    boolean isAvailableAtFrozen();

    /**
     * validate message sign
     *
     * @param algorithm Signature algorithm {@link HmacAlgorithmEnum}
     */
    boolean validateSign(HmacAlgorithmEnum algorithm);

    /**
     * check this auth token
     */
    void valid();

    /**
     * get current sign string
     *
     * @return sign
     */
    String getSign();
}
