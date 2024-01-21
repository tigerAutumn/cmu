package cc.newex.commons.openapi.specs.enums;

/**
 * @author newex-team
 * @date 2018-06-15
 */
public enum HmacAlgorithmEnum {

    /**
     * Signature Algorithm name for {@code No digital signature or MAC performed}
     */
    NONE("None"),

    /**
     * The HmacMD5 Message Authentication Code (MAC) algorithm specified in RFC 2104 and RFC 1321.
     * <p>
     * Every implementation of the Java platform is required to support this standard Mac algorithm.
     * </p>
     */
    HMAC_MD5("HmacMD5"),

    /**
     * The HmacSHA1 Message Authentication Code (MAC) algorithm specified in RFC 2104 and FIPS PUB 180-2.
     * <p>
     * Every implementation of the Java platform is required to support this standard Mac algorithm.
     * </p>
     */
    HMAC_SHA_1("HmacSHA1"),

    /**
     * The HmacSHA224 Message Authentication Code (MAC) algorithm specified in RFC 2104 and FIPS PUB 180-2.
     * <p>
     * Every implementation of the Java 8+ platform is required to support this standard MAC algorithm.
     * </p>
     */
    HMAC_SHA_224("HmacSHA224"),

    /**
     * The HmacSHA256 Message Authentication Code (MAC) algorithm specified in RFC 2104 and FIPS PUB 180-2.
     * <p>
     * Every implementation of the Java platform is required to support this standard Mac algorithm.
     * </p>
     */
    HMAC_SHA_256("HmacSHA256"),

    /**
     * The HmacSHA384 Message Authentication Code (MAC) algorithm specified in RFC 2104 and FIPS PUB 180-2.
     * <p>
     * Every implementation of the Java platform is <em>not</em> required to support this Mac algorithm.
     * </p>
     */
    HMAC_SHA_384("HmacSHA384"),

    /**
     * The HmacSHA512 Message Authentication Code (MAC) algorithm specified in RFC 2104 and FIPS PUB 180-2.
     * <p>
     * Every implementation of the Java platform is <em>not</em> required to support this Mac algorithm.
     * </p>
     */
    HMAC_SHA_512("HmacSHA512");

    private final String name;

    HmacAlgorithmEnum(final String name) {
        this.name = name;
    }

    /**
     * Returns the Signature Algorithm algorithm name constant.
     *
     * @return the Signature Algorithm algorithm name constant.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Looks up and returns the corresponding {@code SignatureAlgorithm} enum instance based on a
     * case-<em>insensitive</em> name comparison.
     *
     * @param name The case-insensitive name of the {@code SignatureAlgorithm} instance to return
     * @return the corresponding {@code SignatureAlgorithm} enum instance based on a
     * case-<em>insensitive</em> name comparison.
     */
    public static HmacAlgorithmEnum forName(final String name) {
        for (final HmacAlgorithmEnum alg : values()) {
            if (alg.getName().equalsIgnoreCase(name)) {
                return alg;
            }
        }
        return HmacAlgorithmEnum.HMAC_SHA_256;
    }
}
