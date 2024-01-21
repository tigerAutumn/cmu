package cc.newex.dax.users.service.cache;

import cc.newex.commons.ucenter.model.SessionInfo;
import cc.newex.dax.users.domain.UserBehavior;
import cc.newex.dax.users.domain.UserIpRateLimit;
import cc.newex.dax.users.dto.kyc.KycChinaCacheDTO;
import cc.newex.dax.users.dto.kyc.KycForeignCacheDTO;
import cc.newex.dax.users.dto.membership.UserFiatResDTO;

import java.util.List;

/**
 * 应用所有操作存调用都集中封装到该类
 *
 * @author newex-team
 * @date 2018-06-18
 */
public interface AppCacheService {

    void setUserBehaviorConf(String name, UserBehavior object);

    UserBehavior getUserBehaviorConf(String name);

    void setImageVerificationCode(String serialNO, String code);

    String getImageVerificationCode(String serialNO);

    void deleteImageVerificationCode(String serialNO);

    void setResetPwdLoginName(final String serialNO, String loginName);

    String getResetPwdLoginName(final String serialNO);

    void deleteResetPwdLoginName(final String serialNO);

    void setTwoFactorLoginUserId(String key, long userId);

    String getTwoFactorLoginUserId(String key);

    void setChinaUserSecondKyc(long userId, String token);

    void deleteChinaUserSecondKyc(long userId);

    String getChinaUserSecondKyc(long userId);

    void setForeignUserSecondKyc(long userId, String token);

    String getForeignUserSecondKyc(long userId);

    void setChinaUserKycInfo(long userId, KycChinaCacheDTO dto);

    KycChinaCacheDTO getChinaUserKycInfo(long userId);

    void deleteChinaUserKycInfo(long userId);

    void setForeignUserKycInfo(long userId, KycForeignCacheDTO dto);

    KycForeignCacheDTO getForeignUserKycInfo(long userId);

    void deleteForeignUserKycInfo(long userId);

    void setUserFiatInfo(final long userId, final UserFiatResDTO dto);

    UserFiatResDTO getUserFiatInfo(final long userId);

    void deleteUserFiatInfo(final long userId);

    void setGoogleSecret(final long userId, String code);

    String getGoogleSecret(final long userId);

    void deleteGoogleSecret(final long userId);

    Integer getGlobalFrozenStatus(String key);

    void setGlobalFrozenStatus(String key, Integer status);

    void deleteGlobalFrozenStatus(String key);

    SessionInfo getSession(final long userId);

    void loadAllIpRateLimit(List<UserIpRateLimit> list);

    void deleteOpenApiUserInfo(final long userId);
}
