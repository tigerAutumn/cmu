package cc.newex.maker.perpetual.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 账号信息配置
 *
 * @author cmx-sdk-team
 * @date 2019-04-08
 */
public enum AccountEnum {

    CMX_TRADE_1(-1L, "XXX", "XXX", "XXX", PlatformEnum.CMX),
    CMX_DEPTH_1(1L, "XXX", "XXX", "XXX", PlatformEnum.CMX),
    BITMEX_1(1L, "XXX", "XXX", "", PlatformEnum.BITMEX),;

    private final Long userId;

    private final String apiKey;

    private final String secretKey;

    private final String passphrase;

    private final PlatformEnum platformEnum;

    AccountEnum(final Long userId, final String apiKey, final String secretKey, final String passphrase, final PlatformEnum platformEnum) {
        this.userId = userId;
        this.apiKey = apiKey;
        this.secretKey = secretKey;
        this.passphrase = passphrase;
        this.platformEnum = platformEnum;
    }

    public static List<AccountEnum> getByPlatform(final PlatformEnum platformEnum) {
        if (platformEnum == null) {
            return null;
        }

        return Arrays.stream(values()).filter(e -> e.getPlatformEnum().equals(platformEnum)).collect(Collectors.toList());
    }

    public Long getUserId() {
        return this.userId;
    }

    public String getApiKey() {
        return this.apiKey;
    }

    public String getSecretKey() {
        return this.secretKey;
    }

    public String getPassphrase() {
        return this.passphrase;
    }

    public PlatformEnum getPlatformEnum() {
        return this.platformEnum;
    }

}
