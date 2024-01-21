package cc.newex.dax.users.service.verification;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.dto.security.WithdrawCheckCodeDTO;
import cc.newex.dax.users.limited.BehaviorTheme;

/**
 * 涉及到验证码的种类
 * GOOGLE/ EMAIL/ PHONE
 */
public interface CheckCodeService {
    ResponseResult checkMobileCode(Long userId, BehaviorTheme behavior, String phoneCode, String ip);

    ResponseResult checkMobileCode(Long userId, BehaviorTheme behavior, String checkCode, int duration, String ip);

    /**
     * 验证手机发送验证码
     *
     * @param userId
     * @param behavior
     * @return
     */
    ResponseResult checkMobileCode(Long userId, String loginName, BehaviorTheme behavior, String verificationCode,
                                   int duration, String ip);

    /**
     * 验证邮箱发送验证码
     *
     * @param userId
     * @param behavior
     * @param emailValidateFlag 邮件激活状态(未激活时,校验完成要激活)
     * @return
     */
    ResponseResult checkEmailCode(Long userId, BehaviorTheme behavior, String verificationCode, int emailValidateFlag, String ip);

    ResponseResult checkEmailCode(Long userId, String loginName, BehaviorTheme behavior, String checkCode, int duration, int emailValidateFlag, String ip);

    ResponseResult checkGoogleCode(String googleCode, UserInfo userInfo);

    /**
     * 验证single类型的验证码
     *
     * @param userId
     * @param behavior
     * @param verificationCode
     * @return
     */
    ResponseResult checkCodeByBehavior(Long userId, BehaviorTheme behavior, String verificationCode);

    /**
     * 验证single类型的验证码
     *
     * @param userId
     * @param behavior
     * @param verificationCode
     * @return
     */
    ResponseResult checkCodeByBehaviorNew(Long userId, BehaviorTheme behavior, String verificationType, String verificationCode);

    /**
     * 提现风控校验验证码
     *
     * @param userId
     * @param behavior
     * @param withdrawCheckCodeDTO
     * @return
     */
    ResponseResult checkWithdrawCode(Long userId, BehaviorTheme behavior, WithdrawCheckCodeDTO withdrawCheckCodeDTO);
}
