package cc.newex.dax.users.service.membership.impl;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.lang.util.PwdStrengthUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.security.jwt.model.JwtConsts;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.dax.users.common.consts.UserDetailConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.exception.UsersBizException;
import cc.newex.dax.users.common.util.VerificationCodeUtils;
import cc.newex.dax.users.criteria.UserInfoExample;
import cc.newex.dax.users.data.UserInfoRepository;
import cc.newex.dax.users.data.UserSettingsRepository;
import cc.newex.dax.users.domain.*;
import cc.newex.dax.users.domain.behavior.enums.BehaviorItemEnum;
import cc.newex.dax.users.service.kyc.UserKycInfoService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserLoginEventService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * 用户表 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class UserInfoServiceImpl
        extends AbstractCrudService<UserInfoRepository, UserInfo, UserInfoExample, Long>
        implements UserInfoService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserSettingsRepository userSettingsRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private UserKycInfoService userKycInfoService;

    @Autowired
    private UserLoginEventService userLoginEventService;

    @Override
    protected UserInfoExample getPageExample(final String fieldName, final String keyword) {
        final UserInfoExample example = new UserInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public boolean exists(final Long userId) {
        final UserInfoExample example = new UserInfoExample();
        example.createCriteria()
                .andIdEqualTo(userId);
        return this.dao.countByExample(example) > 0;
    }

    @Override
    public boolean checkLoginName(final String loginName) {
        final String currentLoginName = StringUtils.trim(loginName);
        final UserInfoExample example = new UserInfoExample();
        example.createCriteria().andMobileEqualTo(currentLoginName);
        example.or().andEmailEqualTo(currentLoginName);
        return this.dao.countByExample(example) > 0;
    }

    @Override
    public boolean checkLoginName(final String loginName, final String type) {
        final String currentLoginName = StringUtils.trim(loginName);
        final UserInfoExample example = new UserInfoExample();
        if (type.equals(BehaviorItemEnum.EMAIL.getName())) {
            example.createCriteria().andEmailEqualTo(currentLoginName);
        } else {
            example.createCriteria().andMobileEqualTo(currentLoginName);
        }
        return this.dao.countByExample(example) > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public long register(final String loginName, final String password,
                         final Integer areaCode, final String ip,
                         final Integer regFrom, final String channel,
                         final Integer brokerId) {
        // 生成唯一的邀请码
        final String uid = this.generateRandomCode();

        final UserInfo userInfo = UserInfo.getInstance();
        userInfo.setUid(uid);
        userInfo.setAreaCode(areaCode);
        userInfo.setMobile(loginName);
        userInfo.setEmail(loginName);
        userInfo.setNickname(loginName);
        userInfo.setPassword(this.passwordEncoder.encode(password));
        userInfo.setRegFrom(regFrom);
        userInfo.setRegIp(IpUtil.toLong(ip));
        userInfo.setCreatedDate(new Date());
        userInfo.setChannel(NumberUtils.toInt(channel, 100000));
        userInfo.setBrokerId(brokerId);

        final UserSettings settings = UserSettings.getInstance();
        settings.setMobileAuthFlag(StringUtil.isEmail(loginName) ? 0 : 1);
        settings.setEmailAuthFlag(StringUtil.isEmail(loginName) ? 1 : 0);
        settings.setLoginPwdStrength(PwdStrengthUtil.getStrengthLevel(password));

        try {
            this.userInfoRepository.insert(userInfo);

            settings.setUserId(userInfo.getId());

            this.userSettingsRepository.insert(settings);

            log.info("register user:{}, user id:{} success", loginName, userInfo.getId());

            return userInfo.getId();
        } catch (final Exception ex) {
            log.error("register user:" + loginName + " failure", ex);
            throw new UsersBizException(BizErrorCodeEnum.REGISTER_SAVE_FAILED, ex);
        }
    }

    @Override
    public JwtUserDetails createJwtUserDetails(final UserDetailInfo userDetailInfo) {
        return JwtUserDetails.builder()
                .sid(UUID.randomUUID().toString() + RandomUtils.nextLong(0, Integer.MAX_VALUE))
                .userId(userDetailInfo.getId())
                .username(userDetailInfo.getNickname())
                .status(userDetailInfo.getStatus())
                .frozen(userDetailInfo.getFrozen())
                .spotFrozen(userDetailInfo.getSpotFrozenFlag())
                .c2cFrozen(userDetailInfo.getC2cFrozenFlag())
                .contractsFrozen(userDetailInfo.getContractsFrozenFlag())
                .assetFrozen(userDetailInfo.getAssetFrozenFlag())
                .perpetualProtocolFlag(userDetailInfo.getPerpetualProtocolFlag())
                .created(new Date()).build();
    }

    @Override
    public JwtUserDetails createJwtStep2UserDetails(final UserDetailInfo userDetailInfo) {
        return JwtUserDetails.builder()
                .sid(UUID.randomUUID().toString() + RandomUtils.nextLong(0, Integer.MAX_VALUE))
                .userId(userDetailInfo.getId())
                .username(userDetailInfo.getNickname())
                .status(JwtConsts.STATUS_TWO_FACTOR)
                .ip(0L)
                .devId("two_factor")
                .created(new Date())
                .expired(DateUtils.addMinutes(new Date(), 5))
                .build();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateEmail(final long userId, final String newEmail, final long ip, final boolean update) {
        final UserInfo userInfo;
        if (update) {
            userInfo = this.userInfoRepository.selectUserInfoById(userId);
            userInfo.setEmail(newEmail);
            userInfo.setNickname(newEmail);

            if (StringUtil.isEmail(userInfo.getMobile())) {
                userInfo.setMobile(newEmail);
            }
        } else {
            userInfo = UserInfo.builder().id(userId)
                    .email(newEmail)
                    .nickname(newEmail)
                    .build();
        }

        final UserSettings settings = UserSettings.builder()
                .userId(userInfo.getId())
                .emailAuthFlag(UserDetailConsts.ENABLE_EMAIL_AUTH)
                .build();
        this.userInfoRepository.updateById(userInfo);
        this.userSettingsRepository.updateById(settings);
        return 1;
    }

    @Override
    public int updateEmailVerify(final long userId, final String antiPhishingCode) {
        final UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .antiPhishingCode(antiPhishingCode)
                .build();
        return this.dao.updateById(userInfo);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateMobile(final long userId, final String mobile, final int areaCode, final long ip, final boolean update) {
        final UserInfo userInfo;

        if (update) {
            userInfo = this.userInfoRepository.selectUserInfoById(userId);
            userInfo.setMobile(mobile);
            userInfo.setAreaCode(areaCode);
            userInfo.setNickname(mobile);

            if (StringUtil.isMobile(userInfo.getEmail())) {
                userInfo.setEmail(mobile);
            }
        } else {
            userInfo = UserInfo.builder().id(userId)
                    .mobile(mobile)
                    .areaCode(areaCode)
                    .nickname(mobile)
                    .build();
        }

        final UserSettings settings = UserSettings.builder()
                .userId(userInfo.getId())
                .mobileAuthFlag(UserDetailConsts.ENABLE_PHONE_AUTH)
                .build();
        this.userInfoRepository.updateById(userInfo);
        this.userSettingsRepository.updateById(settings);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int resetPassword(final long userId, final String newPassword, final int pwdStrengthLevel) {
        final UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .password(newPassword)
                .build();
        final UserSettings settings = UserSettings.builder()
                .userId(userInfo.getId())
                .loginPwdStrength(pwdStrengthLevel)
                .build();
        this.userInfoRepository.updateById(userInfo);
        this.userSettingsRepository.updateById(settings);
        return 1;
    }

    @Override
    public UserLoginInfo getUserLoginInfoByMobile(final String mobile) {
        return this.dao.selectUserLoginInfoByMobile(mobile);
    }

    @Override
    public UserLoginInfo getUserLoginInfoByEmail(final String email) {
        return this.dao.selectUserLoginInfoByEmail(email);
    }

    @Override
    public UserLoginInfo getUserLoginInfoById(final long userId) {
        return this.dao.selectUserLoginInfoById(userId);
    }

    @Override
    public UserDetailInfo getUserDetailInfo(final long userId) {
        return this.dao.selectUserDetailInfo(userId);
    }

    @Override
    public UserInfo getUserInfo(final long userId) {
        final UserInfo userInfo = this.dao.selectUserInfoById(userId);
        if (null != userInfo && StringUtils.isEmpty(userInfo.getRealName())) {
            final UserKycInfo userKycInfo = this.userKycInfoService.getByUserId(userId);
            if (null != userKycInfo) {
                final String realName = userKycInfo.getFirstName();
                userInfo.setRealName(realName);
            }
            this.dao.updateById(userInfo);
        }
        return userInfo;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int generateGoogelKey(final long userId, final String googleKey) {
        final UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .googleCode(googleKey)
                .build();
        final UserSettings settings = UserSettings.builder()
                .userId(userInfo.getId())
                .googleAuthFlag(UserDetailConsts.ENABLE_GOOGLE_AUTH)
                .build();
        this.userInfoRepository.updateById(userInfo);
        this.userSettingsRepository.updateById(settings);
        return 1;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int updateGoogelKey(final long userId, final String googleKey) {
        final UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .googleCode(googleKey)
                .build();
        this.userInfoRepository.updateById(userInfo);
        return 1;
    }

    @Override
    public boolean unbindGoogle(final long userId) {
        final UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .googleCode("")
                .build();
        this.userInfoRepository.updateById(userInfo);
        this.userSettingsService.enableGoogleAuthFlag(userId, UserDetailConsts.DISABLE_AUTH);
        return true;
    }

    @Override
    public UserInfo getUserInfoByMobile(final String mobile) {
        return this.dao.selectUserInfoByMobile(mobile);
    }

    @Override
    public UserInfo getUserInfoByEmail(final String email) {
        return this.dao.selectUserInfoByEmail(email);
    }

    @Override
    public UserDetailInfo getDetailUserInfoByMobile(final String mobile) {
        return this.dao.selectDetailUserInfoByMobile(mobile);
    }

    @Override
    public UserDetailInfo getDetailUserInfoByEmail(final String email) {
        return this.dao.selectDetailUserInfoByEmail(email);
    }

    @Override
    public UserInfo getUserInfo(final String username) {
        if (StringUtil.isEmail(username)) {
            return this.getUserInfoByEmail(username);
        }
        return this.getUserInfoByMobile(username);
    }

    @Override
    public UserLoginInfo getUserLoginInfo(final String username) {
        if (StringUtil.isEmail(username)) {
            return this.getUserLoginInfoByEmail(username);
        }
        return this.getUserLoginInfoByMobile(username);
    }

    @Override
    public List<UserInfo> selectByPager(final Long userId, final int pageIndex, final int pageSize) {

        final UserInfoExample example = new UserInfoExample();
        final UserInfoExample.Criteria criteria = example.createCriteria();
        if (userId > 0) {
            criteria.andIdGreaterThanOrEqualTo(userId);
        }
        example.setOrderByClause(" id asc");

        final PageInfo pageInfo = new PageInfo();
        pageInfo.setPageSize(pageSize);
        pageInfo.setStartIndex((pageIndex - 1) * pageSize);
        final List<UserInfo> userInfoList = this.userInfoRepository.selectByPager(pageInfo, example);

        return userInfoList;
    }

    @Override
    public boolean isExistUid(final String uid) {
        final UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUidEqualTo(uid);
        final boolean result = this.userInfoRepository.selectOneByExample(example) == null ? false : true;
        return result;
    }

    @Override
    public UserInfo getUserInfoByUid(final String uid) {
        final UserInfoExample example = new UserInfoExample();
        example.createCriteria().andUidEqualTo(uid);
        return this.userInfoRepository.selectOneByExample(example);
    }

    @Override
    public String generateRandomCode() {
        String uid = VerificationCodeUtils.generate8RandomCode();
        while (this.isExistUid(uid)) {
            uid = VerificationCodeUtils.generate8RandomCode();
        }
        return uid;
    }

    @Override
    public List<UserStatisticsInfo> getUserStatisticsInfoByTime(final Date beginDate, final Date endDate) {
        return this.userInfoRepository.selectUserStatisticsInfoByTime(beginDate, endDate);
    }

    @Override
    public List<String> getUid(final List<Long> userIdList) {
        if (CollectionUtils.isEmpty(userIdList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        final List<String> uidList = this.userInfoRepository.selectUid(userIdList);

        if (CollectionUtils.isEmpty(uidList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        return uidList;
    }

    @Override
    public Boolean isLoginWithTodayByUserId(Long userId) {

        return userLoginEventService.isLoginWithTodayByUserId(userId);
    }
}
