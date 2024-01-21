package cc.newex.dax.users.service.verification.impl;

import cc.newex.commons.lang.crypto.AESUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.CommonConsts;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.consts.UserDetailConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.GoogleAuthenticator;
import cc.newex.dax.users.criteria.UserNoticeEventExample;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserNoticeEvent;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.domain.behavior.enums.BehaviorItemEnum;
import cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum;
import cc.newex.dax.users.domain.behavior.model.CheckBehaviorItem;
import cc.newex.dax.users.domain.behavior.model.UserBehaviorResult;
import cc.newex.dax.users.dto.security.WithdrawCheckCodeDTO;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.service.behavior.UserBehaviorService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import cc.newex.dax.users.service.verification.CheckCodeService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CheckCodeServiceImpl implements CheckCodeService {

    @Autowired
    private UserNoticeEventService userNoticeEventService;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private UserBehaviorService userBehaviorService;

    @Autowired
    private UserInfoService userInfoService;

    /**
     * 验证码的有效时间10分钟
     */
    private static final int MAX_DURATION_MIN = 10;

    @Override
    public ResponseResult checkMobileCode(final Long userId, final BehaviorTheme behavior, final String phoneCode, final String ip) {
        return this.checkMobileCode(userId, null, behavior, phoneCode, MAX_DURATION_MIN, ip);
    }

    @Override
    public ResponseResult checkMobileCode(final Long userId, final BehaviorTheme behavior, final String checkCode, final int duration, final String ip) {
        return this.checkMobileCode(userId, null, behavior, checkCode, duration, ip);
    }

    @Override
    public ResponseResult checkMobileCode(final Long userId, final String loginName, final BehaviorTheme behavior, final String checkCode, final int duration, final String ip) {
        final int result = this.checkCode(userId, loginName, behavior, checkCode, duration, ip, NoticeSendLogConsts.CHANNEL_PHONE);

        if (result == -1) {
            return ResultUtils.failure(BizErrorCodeEnum.SMS_CODE_VERIFY_ERROR);
        } else if (result == -2) {
            return ResultUtils.failure(BizErrorCodeEnum.SMS_CODE_VERIFY_OVERDUE);
        }

        return ResultUtils.success();
    }

    @Override
    public ResponseResult checkEmailCode(final Long userId, final BehaviorTheme behavior, final String verificationCode, final int emailValidateFlag, final String ip) {
        return this.checkEmailCode(userId, null, behavior, verificationCode, MAX_DURATION_MIN, emailValidateFlag, ip);
    }

    private UserNoticeEvent getByBehavior(final Long userId, final String loginName, final BehaviorTheme behavior, final Integer channel) {
        final UserNoticeEventExample example = new UserNoticeEventExample();
        final UserNoticeEventExample.Criteria criteria = example.createCriteria()
                .andBehaviorIdEqualTo(behavior.getBehavior())
                .andChannelEqualTo(channel)
                .andStatusEqualTo(NoticeSendLogConsts.STATUS_NEW)
                .andUserIdEqualTo(userId)
                .andTypeEqualTo(NoticeSendLogConsts.BUSINESS_CODE);

        if (StringUtils.isNotBlank(loginName)) {
            criteria.andTargetEqualTo(loginName);
        }

        example.setOrderByClause(" id desc");

        return this.userNoticeEventService.getOneByExample(example);
    }

    @Override
    public ResponseResult checkEmailCode(final Long userId, final String loginName, final BehaviorTheme behavior, final String checkCode,
                                         final int duration, final int emailValidateFlag, final String ip) {

        final int result = this.checkCode(userId, loginName, behavior, checkCode, duration, ip, NoticeSendLogConsts.CHANNEL_EMAIL);

        if (result == -1) {
            return ResultUtils.failure(BizErrorCodeEnum.EMAIL_CODE_VERIFY_ERROR);
        } else if (result == -2) {
            return ResultUtils.failure(BizErrorCodeEnum.EMAIL_CODE_VERIFY_OVERDUE);
        }

        // 更新邮箱的绑定标识为已绑定
        if (userId != null && userId > 0) {
            final UserSettings userSettings = this.userSettingsService.getById(userId);

            if (userSettings.getEmailAuthFlag() == UserDetailConsts.DISABLE_AUTH) {
                this.userSettingsService.enableEmailAuthFlag(userId, UserDetailConsts.ENABLE_EMAIL_AUTH);
            }
        }

        return ResultUtils.success();
    }

    private int checkCode(final Long userId, final String loginName, final BehaviorTheme behavior, final String checkCode,
                          int duration, final String ip, final Integer channel) {
        final UserNoticeEvent userNoticeEvent = this.getByBehavior(userId, loginName, behavior, channel);

        if (userNoticeEvent == null) {
            log.info("check code error, userId: {}, loginName: {}, behavior: {}, checkCode: {}, ip: {}, channel: {}",
                    userId, loginName, behavior, checkCode, ip, channel);

            return -1;
        }

        if (StringUtil.notEquals(checkCode, JSON.parseObject(userNoticeEvent.getParams()).getString("code"))) {
            log.info("check code error, userId: {}, loginName: {}, behavior: {}, checkCode: {}, ip: {}, channel: {}, params: {}",
                    userId, loginName, behavior, checkCode, ip, channel, JSON.toJSONString(userNoticeEvent));

            return -1;
        }

        final UserNoticeEvent update = new UserNoticeEvent();

        // 默认有效时间 10分钟
        duration = duration <= 0 ? MAX_DURATION_MIN : duration;

        final Date endTime = DateUtils.addMinutes(userNoticeEvent.getUpdatedDate(), duration);

        // 验证码过期
        if (new Date().after(endTime)) {
            update.setId(userNoticeEvent.getId());
            update.setStatus(NoticeSendLogConsts.STATUS_OVERDUE);

            this.userNoticeEventService.editById(update);

            return -2;
        }

        update.setId(userNoticeEvent.getId());
        update.setStatus(NoticeSendLogConsts.STATUS_USED);

        final int effect = this.userNoticeEventService.editById(update);

        if (effect <= 0) {
            return -1;
        }

        return effect;
    }

    @Override
    public ResponseResult checkGoogleCode(final String googleCode, final UserInfo userInfo) {
        if (userInfo == null || StringUtils.isEmpty(userInfo.getGoogleCode())) {
            return ResultUtils.failure(BizErrorCodeEnum.GOOGLE_CODE_VERIFY_ERROR);
        }

        final String redisKey = String.format(CommonConsts.VALID_GOOGLE_CODE, userInfo.getId(), googleCode);

        final String redisCode = this.stringRedisTemplate.opsForValue().get(redisKey);

        // 如果存在该code，说明是一分钟内用过的code，直接返回false
        if (StringUtils.equals(googleCode, redisCode)) {
            return ResultUtils.failure(BizErrorCodeEnum.GOOGLE_CODE_VERIFY_GOOGLE_TWICE);
        }

        Boolean result = false;

        final String key = AESUtil.decrypt(userInfo.getGoogleCode(), CommonConsts.ASE_KEY);

        if (StringUtils.isNotEmpty(key) && StringUtils.isNotEmpty(googleCode)) {
            result = GoogleAuthenticator.checkCode(key, NumberUtils.toLong(googleCode), Calendar.getInstance().getTimeInMillis());
        }

        // google验证码校验通过，放入redis中，给上面的校验使用，一分钟内只能使用一次
        if (result) {
            this.stringRedisTemplate.opsForValue().set(redisKey, googleCode, 1, TimeUnit.MINUTES);

            return ResultUtils.success();
        }

        return ResultUtils.failure(BizErrorCodeEnum.GOOGLE_CODE_VERIFY_ERROR);
    }

    @Override
    public ResponseResult checkCodeByBehavior(final Long userId, final BehaviorTheme behavior, final String verificationCode) {
        final UserBehaviorResult userBehaviorResult = this.userBehaviorService.getUserCheckRuleBehavior(BehaviorNameEnum.LOGIN_STEP2_AUTH.getName(), userId);
        final List<String> behaviorList = userBehaviorResult.getCheckItems().stream().map(CheckBehaviorItem::getName).collect(Collectors.toList());

        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        final UserSettings userSettings = this.userSettingsService.getById(userId);

        // 验证谷歌验证码是否正确
        if (behaviorList.contains(BehaviorItemEnum.GOOGLE.getName())) {
            final ResponseResult googleResult = this.checkGoogleCode(verificationCode, userInfo);
            if (googleResult != null && googleResult.getCode() > 0) {
                log.error("check google code error, userId: {}, behavior: {}, code: {}",
                        userId, behavior.getBehavior(), verificationCode);
            }

            return googleResult;
        }

        // 验证手机验证码是否正确
        if (behaviorList.contains(BehaviorItemEnum.MOBILE.getName())) {
            final ResponseResult phoneResult = this.checkMobileCode(userId, behavior, verificationCode, null);
            if (phoneResult != null && phoneResult.getCode() > 0) {
                log.error("check mobile code error, userId: {}, behavior: {}, code: {}",
                        userId, behavior.getBehavior(), verificationCode);
            }

            return phoneResult;
        }

        // 验证邮箱验证码是否正确
        if (behaviorList.contains(BehaviorItemEnum.EMAIL.getName())) {
            final ResponseResult emailResult = this.checkEmailCode(userId, behavior, verificationCode, userSettings.getEmailAuthFlag(), null);
            if (emailResult != null && emailResult.getCode() > 0) {
                log.error("check email code error, userId: {}, behavior: {}, code: {}",
                        userId, behavior.getBehavior(), verificationCode);
            }

            return emailResult;
        }

        return null;
    }

    @Override
    public ResponseResult checkCodeByBehaviorNew(final Long userId, final BehaviorTheme behavior, final String verificationType, final String verificationCode) {
        final UserBehaviorResult userBehaviorResult = this.userBehaviorService.getUserCheckRuleBehavior(BehaviorNameEnum.LOGIN_STEP2_AUTH.getName(), userId);
        final List<String> behaviorList = userBehaviorResult.getTotalCheckItems().stream().map(CheckBehaviorItem::getName).collect(Collectors.toList());

        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        final UserSettings userSettings = this.userSettingsService.getById(userId);

        //匹配一个就ok
        if (BehaviorItemEnum.GOOGLE.getName().equals(verificationType)) {
            // 验证谷歌验证码是否正确
            if (behaviorList.contains(BehaviorItemEnum.GOOGLE.getName())) {
                final ResponseResult googleResult = this.checkGoogleCode(verificationCode, userInfo);
                if (googleResult != null && googleResult.getCode() > 0) {
                    log.error("check google code error, userId: {}, behavior: {}, type: {}, code: {}",
                            userId, behavior.getBehavior(), verificationType, verificationCode);
                }

                return googleResult;
            }
        }

        if (BehaviorItemEnum.MOBILE.getName().equals(verificationType)) {
            // 验证手机验证码是否正确
            if (behaviorList.contains(BehaviorItemEnum.MOBILE.getName())) {
                final ResponseResult phoneResult = this.checkMobileCode(userId, behavior, verificationCode, null);
                if (phoneResult != null && phoneResult.getCode() > 0) {
                    log.error("check mobile code error, userId: {}, behavior: {}, type: {}, code: {}",
                            userId, behavior.getBehavior(), verificationType, verificationCode);
                }

                return phoneResult;
            }
        }

        if (BehaviorItemEnum.EMAIL.getName().equals(verificationType)) {
            // 验证邮箱验证码是否正确
            if (behaviorList.contains(BehaviorItemEnum.EMAIL.getName())) {
                final ResponseResult emailResult = this.checkEmailCode(userId, behavior, verificationCode, userSettings.getEmailAuthFlag(), null);
                if (emailResult != null && emailResult.getCode() > 0) {
                    log.error("check email code error, userId: {}, behavior: {}, type: {}, code: {}",
                            userId, behavior.getBehavior(), verificationType, verificationCode);
                }

                return emailResult;
            }
        }

        return null;
    }

    @Override
    public ResponseResult checkWithdrawCode(final Long userId, final BehaviorTheme behavior, final WithdrawCheckCodeDTO withdrawCheckCodeDTO) {
        final UserBehaviorResult userBehaviorResult = this.userBehaviorService.getUserCheckRuleBehavior(BehaviorNameEnum.WITHDRAW_ASSET.getName(), userId);
        final List<String> behaviorList = userBehaviorResult.getCheckItems().stream().map(CheckBehaviorItem::getName).collect(Collectors.toList());

        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        final UserSettings userSettings = this.userSettingsService.getById(userId);

        // 邮箱验证码
        final String emailCode = withdrawCheckCodeDTO.getEmailCode();

        // 谷歌验证码
        final String googleCode = withdrawCheckCodeDTO.getGoogleCode();

        // 手机验证码
        final String mobileCode = withdrawCheckCodeDTO.getMobileCode();

        // 验证移动设备
        if (StringUtils.isBlank(googleCode) && StringUtils.isBlank(mobileCode)) {
            log.error("checkWithdrawCode emailCode Check error! userid={},withdrawCheckCodeDTO={}", userId, JSON.toJSONString(withdrawCheckCodeDTO));

            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        // 验证邮箱验证码是否正确
        if (behaviorList.contains(BehaviorItemEnum.EMAIL.getName())) {
            final ResponseResult checkResult = this.checkEmailCode(userId, behavior, emailCode, userSettings.getEmailAuthFlag(), null);

            if (checkResult != null && checkResult.getCode() > 0) {
                log.error("checkWithdrawCode emailCode check error! userid={}, checkResult={}", userId, JSON.toJSONString(checkResult));

                return checkResult;
            }
        }

        // 验证谷歌验证码是否正确
        if (behaviorList.contains(BehaviorItemEnum.GOOGLE.getName()) && StringUtils.isNotBlank(googleCode)) {
            final ResponseResult checkResult = this.checkGoogleCode(googleCode, userInfo);

            if (checkResult != null && checkResult.getCode() > 0) {
                log.error("checkCodeByBehavior googleCode check error! userid={}, checkResult={}", userId, JSON.toJSONString(checkResult));

                return checkResult;
            }

        }

        // 验证手机验证码是否正确
        if (behaviorList.contains(BehaviorItemEnum.MOBILE.getName()) && StringUtils.isNotBlank(mobileCode)) {
            final ResponseResult checkResult = this.checkMobileCode(userId, behavior, mobileCode, null);

            if (checkResult != null && checkResult.getCode() > 0) {
                log.error("checkCodeByBehavior mobileCode check error! userid={}, checkResult={}", userId, JSON.toJSONString(checkResult));

                return checkResult;
            }
        }

        return ResultUtils.success();
    }
}
