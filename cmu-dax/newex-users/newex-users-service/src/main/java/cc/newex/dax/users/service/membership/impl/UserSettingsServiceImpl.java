package cc.newex.dax.users.service.membership.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.common.consts.UserFiatConsts;
import cc.newex.dax.users.criteria.UserSettingsExample;
import cc.newex.dax.users.data.UserSettingsRepository;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.service.membership.UserSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 用户开关设置表 服务实现
 *
 * @author newex-team
 * @date 2018-04-06
 */
@Slf4j
@Service
public class UserSettingsServiceImpl
        extends AbstractCrudService<UserSettingsRepository, UserSettings, UserSettingsExample, Long>
        implements UserSettingsService {

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Override
    protected UserSettingsExample getPageExample(final String fieldName, final String keyword) {
        final UserSettingsExample example = new UserSettingsExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public boolean enableEmailAuthFlag(final long userId, final int status) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .emailAuthFlag(status)
                .build();

        return this.userSettingsRepository.updateById(userSettings) > 0;
    }

    @Override
    public boolean enableGoogleAuthFlag(final long userId, final int status) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .googleAuthFlag(status)
                .build();

        return this.userSettingsRepository.updateById(userSettings) > 0;
    }

    @Override
    public boolean enabbeMobileAuthFlag(final long userId, final int status) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .mobileAuthFlag(status)
                .build();

        return this.userSettingsRepository.updateById(userSettings) > 0;
    }

    @Override
    public boolean enableTradeAuthFlag(final long userId, final int status) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .tradeAuthFlag(status)
                .build();

        return this.userSettingsRepository.updateById(userSettings) > 0;
    }

    @Override
    public boolean enableLoginAuthFlag(final long userId, final int status) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .loginAuthFlag(status)
                .build();

        return this.userSettingsRepository.updateById(userSettings) > 0;
    }

    @Override
    public int updatePerpetualProtocolFlag(final Long userId, final Integer perpetualProtocolFlag) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .perpetualProtocolFlag(perpetualProtocolFlag)
                .build();

        return this.userSettingsRepository.updateById(userSettings);
    }

    @Override
    public boolean enablePayAuthFlag(final long userId, final int payment, final int status) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .build();
        switch (payment) {
            case UserFiatConsts.USER_FIAT_ALIPAY:
                userSettings.setAlipayAuthFlag(status);
                break;
            case UserFiatConsts.USER_FIAT_WECHATPAY:
                userSettings.setWechatPayAuthFlag(status);
                break;
            case UserFiatConsts.USER_FIAT_BANKPAY:
                userSettings.setBankPayAuthFlag(status);
                break;
            default:
                return false;
        }
        return this.dao.updateById(userSettings) > 0;
    }

    @Override
    public UserSettings getByUserId(final long userId) {
        final UserSettingsExample example = new UserSettingsExample();
        example.createCriteria().andUserIdEqualTo(userId);
        final UserSettings userSettings = this.userSettingsRepository.selectOneByExample(example);
        return ObjectUtils.isEmpty(userSettings) ? null : userSettings;
    }

    @Override
    public boolean limitOnePay(final UserSettings userSettings, final int payType) {
        boolean result = true;
        switch (payType) {
            case UserFiatConsts.USER_FIAT_ALIPAY:
                if (userSettings.getAlipayAuthFlag() == 1 && userSettings.getBankPayAuthFlag() == 0 && userSettings.getWechatPayAuthFlag() == 0) {
                    result = false;
                }
                break;
            case UserFiatConsts.USER_FIAT_WECHATPAY:
                if (userSettings.getWechatPayAuthFlag() == 1 && userSettings.getBankPayAuthFlag() == 0 && userSettings.getAlipayAuthFlag() == 0) {
                    result = false;
                }
                break;
            case UserFiatConsts.USER_FIAT_BANKPAY:
                if (userSettings.getBankPayAuthFlag() == 1 && userSettings.getWechatPayAuthFlag() == 0 && userSettings.getAlipayAuthFlag() == 0) {
                    result = false;
                }
                break;
            default:

        }
        return result;
    }
}