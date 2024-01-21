package cc.newex.dax.users.rest.controller.outer.v1.common.membership;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.lang.util.PwdStrengthUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.ratelimiter.annotation.IpRequestRateLimit;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.behavior.enums.BehaviorItemEnum;
import cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum;
import cc.newex.dax.users.domain.behavior.model.CheckBehaviorItem;
import cc.newex.dax.users.domain.behavior.model.UserBehaviorResult;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.common.service.EventFacadeService;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.rest.model.ConfirmAccountReqVO;
import cc.newex.dax.users.rest.model.ConfirmAccountResVO;
import cc.newex.dax.users.rest.model.ResetPwdReqVO;
import cc.newex.dax.users.service.behavior.UserBehaviorService;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import cc.newex.dax.users.service.verification.CheckCodeService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * 用户找回密码
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/membership/forget-pwd")
public class ForgetPwdController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserBehaviorService behaviorService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private CheckCodeService checkCodeService;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private UserNoticeEventService userNoticeEventService;

    @Autowired
    private EventFacadeService operatorFacadeService;

    @Autowired
    private AppCacheService appCacheService;

    /**
     * 确认账户， 找回密码第一步确认账户
     *
     * @param request
     * @param form
     * @return
     */
    @IpRequestRateLimit(value = "1/1")
    @PostMapping(value = "/confirm-account")
    public ResponseResult resetConfirmAccount(@RequestBody @Valid final ConfirmAccountReqVO form,
                                              final HttpServletRequest request) {
        final String redisVerificationCode = this.appCacheService.getImageVerificationCode(form.getSerialNO());
        this.appCacheService.deleteImageVerificationCode(form.getSerialNO());

        if (StringUtils.isBlank(redisVerificationCode)) {
            log.error("verification code is expired! params={}", JSON.toJSONString(form));
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_VERIFICATION_CODE_EXPIRED);
        }

        if (StringUtil.notEqualsIgnoreCaseWithTrim(redisVerificationCode, form.getVerificationCode())) {
            log.error("verification code is error! params={}", JSON.toJSONString(form));
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_VERIFICATION_CODE_ERROR);
        }

        final boolean exist = this.userInfoService.checkLoginName(form.getLoginName().trim());
        if (!exist) {
            log.error("username is not exist! params={}", JSON.toJSONString(form));
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final UserInfo userInfo = this.userInfoService.getUserInfo(form.getLoginName());
        if (userInfo == null) {
            log.error("user is not exist! params={}", JSON.toJSONString(form));
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final ConfirmAccountResVO resForm = ConfirmAccountResVO.builder().loginName(form.getLoginName()).behaviors(
                this.behaviorService.getUserCheckRuleBehavior(BehaviorNameEnum.LOGIN_PWD_RESET.getName(), userInfo.getId())).build();

        final Long userId = userInfo.getId();
        final UserBehaviorResult userBehaviorResult = this.behaviorService.getUserCheckRuleBehavior(BehaviorNameEnum.LOGIN_PWD_RESET.getName(), userId);
        final List<String> behaviorList = userBehaviorResult.getCheckItems().stream().map(CheckBehaviorItem::getName).collect(Collectors.toList());

        // 发送邮箱验证码
        if (behaviorList.contains(BehaviorItemEnum.EMAIL.getName())) {
            try {
                final BehaviorTheme behaviorTheme = BehaviorTheme.getBehavior(BehaviorTheme.USERS2_LOGIN_RESET_PASSWORD_4.getBehavior());

                final boolean result = this.userNoticeEventService.sendEmail(LocaleUtils.getLocale(request),
                        behaviorTheme, NoticeSendLogConsts.BUSINESS_CODE, userInfo.getEmail(), behaviorTheme.getEmailCode(),
                        userId, Maps.newHashMap(), this.getBrokerId(request));

                log.info("resetConfirmAccount send email code success, userId: {}, email: {}, result: {}", userId, userInfo.getEmail(), result);

                resForm.setShadeEmail(StringUtil.getStarEmail(userInfo.getEmail()));
                resForm.setEmail(userInfo.getEmail());
            } catch (final Exception e) {
                log.error("resetConfirmAccount send email code error, userId: " + userId + ", email: " + userInfo.getEmail(), e);
            }
        }

        // 发送手机验证码
        if (behaviorList.contains(BehaviorItemEnum.MOBILE.getName())) {
            try {
                final BehaviorTheme behaviorTheme = BehaviorTheme.getBehavior(BehaviorTheme.USERS2_LOGIN_RESET_PASSWORD_4.getBehavior());

                final boolean result = this.userNoticeEventService.sendSMS(LocaleUtils.getLocale(request),
                        behaviorTheme, NoticeSendLogConsts.BUSINESS_CODE, userInfo.getMobile(), userInfo.getAreaCode(), behaviorTheme.getMobileCode(),
                        userId, Maps.newHashMap(), this.getBrokerId(request));

                log.info("resetConfirmAccount send mobile code success, userId: {}, mobile: {}, result: {}", userId, userInfo.getMobile(), result);

                resForm.setShadeMobile(StringUtil.getStarMobile(userInfo.getMobile()));
                resForm.setAreaCode(userInfo.getAreaCode());
                resForm.setMobile(userInfo.getMobile());
            } catch (final Exception e) {
                log.error("resetConfirmAccount send mobile code error, userId: " + userId + ", mobile: " + userInfo.getMobile(), e);
            }
        }

        final String serialNO = UUID.randomUUID().toString();
        resForm.setLoginName(serialNO);
        this.appCacheService.setResetPwdLoginName(serialNO, form.getLoginName());

        return ResultUtils.success(resForm);
    }

    /**
     * 找回(重置)登录密码
     *
     * @param request
     * @param form
     * @return
     */
    @RetryLimit(type = RetryLimitTypeEnum.FORGET_PASSWORD)
    @IpRequestRateLimit(value = "1/1")
    @PutMapping(value = "/pwd-reset")
    public ResponseResult resetLoginPassword(@RequestBody @Valid final ResetPwdReqVO form,
                                             final HttpServletRequest request) {
        final String loginName = this.appCacheService.getResetPwdLoginName(form.getLoginName());

        if (StringUtils.isEmpty(loginName)) {
            log.error("resetLoginPassword password loginName is empty! loginName={}", form.getPassword(),
                    form.getConfirmPassword());
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        form.setLoginName(loginName);
        /**
         * 密码强度校验
         */
        final Integer level = PwdStrengthUtil.getStrengthLevel(form.getPassword());
        if (level < 2) {
            log.error("resetLoginPassword level={},loginName={}", level, loginName);
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_PASSWORD_SIMPLE);
        }

        if (StringUtil.notEquals(form.getPassword(), form.getConfirmPassword())) {
            log.error("resetLoginPassword password not eq confirmPassword! password={},confirmPassword={}", form.getPassword(),
                    form.getConfirmPassword());
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_PASSWORD_NOT_EQUAL);
        }

        final String ip = IpUtil.getRealIPAddress(request);

        final boolean exist = this.userInfoService.checkLoginName(form.getLoginName());
        if (!exist) {
            log.error("resetLoginPassword is not exist! params={}", JSON.toJSONString(form));
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        if (CollectionUtils.isEmpty(form.getCheckItems())) {
            log.error("resetLoginPassword checkItems is not empty! params={}", form.getCheckItems());
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        final UserInfo userInfo = this.userInfoService.getUserInfo(form.getLoginName());
        final long userId = userInfo.getId();

        final UserBehaviorResult userBehaviorResult = this.behaviorService.getUserCheckRuleBehavior(BehaviorNameEnum.LOGIN_PWD_RESET.getName(), userId);
        final List<String> behaviorList = userBehaviorResult.getCheckItems().stream().map(CheckBehaviorItem::getName).collect(Collectors.toList());

        /**
         * 验证谷歌
         */
        if (behaviorList.contains(BehaviorItemEnum.GOOGLE.getName())) {
            final ResponseResult googleOldResult = this.checkCodeService.checkGoogleCode(form.getGoogleCode(), userInfo);

            if (googleOldResult != null && googleOldResult.getCode() > 0) {
                log.error("resetLoginPassword, googleCode check error! userid={},googleKey={},googleCode={}", userId,
                        userInfo.getGoogleCode(), form.getGoogleCode());
                return googleOldResult;
            }
        }
        /**
         *   邮箱验证
         */
        if (behaviorList.contains(BehaviorItemEnum.EMAIL.getName())) {
            final ResponseResult emailResult = this.checkCodeService.checkEmailCode(userId,
                    BehaviorTheme.USERS2_LOGIN_RESET_PASSWORD_4, form.getEmailCode(),
                    this.userSettingsService.getById(userId).getEmailAuthFlag(), ip);
            if (emailResult != null && emailResult.getCode() > 0) {
                log.error("resetLoginPassword, emailCode Check error! userid={}", userId);
                return emailResult;
            }
        }
        /**
         * 验证手机是否正确
         */
        if (behaviorList.contains(BehaviorItemEnum.MOBILE.getName())) {
            final ResponseResult phoneResult = this.checkCodeService.checkMobileCode(userId,
                    BehaviorTheme.USERS2_LOGIN_RESET_PASSWORD_4, form.getMobileCode(), ip);
            if (phoneResult != null && phoneResult.getCode() > 0) {
                log.error("gcodeAuthBind, mobileCode check error! userid={}", userId);
                return phoneResult;
            }
        }
        final int pwdStrongFlag = PwdStrengthUtil.getStrengthLevel(form.getPassword());

        final long returnValue = this.userInfoService.resetPassword(userId, this.passwordEncoder.encode(form.getPassword()), pwdStrongFlag);

        log.info("resetLoginPassword. Username:{},ip:{},returnValue:{}", form.getLoginName(), ip, returnValue);

        if (returnValue <= 0) {
            log.error("resetLoginPassword failure!username={}", form.getLoginName());
            return ResultUtils.failure(BizErrorCodeEnum.PWD_SETTING_ERROR);
        }
        final Map<String, Object> map = Maps.newHashMap();
        map.put("antiphishing", userInfo.getAntiPhishingCode());

        if (behaviorList.contains(BehaviorItemEnum.EMAIL.getName())) {
            this.userNoticeEventService.sendEmail(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS2_LOGIN_RESET_PASSWORD_4,
                    NoticeSendLogConsts.BUSINESS_NOTIFICATION,
                    userInfo.getEmail(),
                    MessageTemplateConsts.MAIL_USERS_LOGIN_PWD_MODIFY_SUCCESS, userId, map, this.getBrokerId(request));
        }
        if (behaviorList.contains(BehaviorItemEnum.MOBILE.getName())) {
            this.userNoticeEventService.sendSMS(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS2_LOGIN_RESET_PASSWORD_4,
                    NoticeSendLogConsts.BUSINESS_NOTIFICATION,
                    userInfo.getMobile(),
                    userInfo.getAreaCode(),
                    MessageTemplateConsts.SMS_USERS_LOGIN_PWD_MODIFY_SUCCESS, userId, map, this.getBrokerId(request));
        }
        try {
            JwtTokenUtils.removeSessionUser(userId);
        } catch (final Exception e) {
            log.error("resetLoginPassword userId={}", userId);
        }

        // 添加一条登录记录
        final UserDetailInfo userDetailInfo = ObjectCopyUtils.map(userInfo, UserDetailInfo.class);
        final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.LOGIN_PWD_RESET);
        this.appCacheService.deleteResetPwdLoginName(form.getLoginName());
        return ResultUtils.success();
    }
}