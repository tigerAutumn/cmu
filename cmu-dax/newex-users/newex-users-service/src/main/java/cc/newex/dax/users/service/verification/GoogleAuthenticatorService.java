package cc.newex.dax.users.service.verification;

import cc.newex.commons.support.model.ResponseResult;

/**
 * @author newex-team
 */
public interface GoogleAuthenticatorService {

    String createSecretUrl(long userId, String username);

    void sendSmsVerifyCode(long userId, String phone, String ip);

    void bind(long userId, String username, String smsCode, int googleCode);

    void switcch(long userId, String smsCode, boolean open);

    /**
     * 验证谷歌验证码是否正确
     *
     * @param userId
     * @param messageType
     * @param code
     * @param minutes
     * @return
     */
    ResponseResult<?> validateGoogleCode(long userId, int messageType, String code, int minutes, String ip);
}
