package cc.newex.dax.users.rest.controller.outer.v1.common.security;

import cc.newex.commons.lang.crypto.AESUtil;
import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.common.consts.CommonConsts;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.consts.UserDetailConsts;
import cc.newex.dax.users.common.consts.UserNoticeRecordConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.enums.security.UserVerifyTypeEnum;
import cc.newex.dax.users.common.util.GoogleAuthenticator;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.common.service.EventFacadeService;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.rest.model.GCodeSwitchVO;
import cc.newex.dax.users.rest.model.GoogleAuthChangeVO;
import cc.newex.dax.users.rest.model.GoogleAuthResetVO;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import cc.newex.dax.users.service.verification.CheckCodeService;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/v1/users/security/google-code")
public class SecurityGcodeController extends BaseController {
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private UserNoticeEventService noticeSendLogService;
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private EventFacadeService operatorFacadeService;
    @Autowired
    private AppCacheService appCacheService;

    /**
     * @param form
     * @param request
     * @description 开关谷歌验证
     * @date 2018/4/17 下午5:54
     */
    @PutMapping(value = "/validate/enable")
    public ResponseResult googleValidateSwitch(@RequestBody final GCodeSwitchVO form, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("googleValidateSwitch, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        // 获取用户信息
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("googleValidateSwitch, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        // 如果用户没有绑定谷歌验证码
        if (StringUtils.isEmpty(userInfo.getGoogleCode())) {
            log.error("googleValidateSwitch, nobind google! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.GOOGLE_KEY_EMPTY);
        }
        final UserSettings userSettings = this.userSettingsService.getById(userId);
        final int mobileAuthFlag = userSettings.getMobileAuthFlag();
        if (mobileAuthFlag == UserDetailConsts.DISABLE_AUTH) {
            return ResultUtils.failure(BizErrorCodeEnum.SWITCH_CANTNOT_CLOSED_GOOGLESMS);
        }
        /**
         * 获取用户开/关行为，更新用户设置
         */
        final BehaviorTheme behaviorTheme = BehaviorTheme.getBehavior(form.getBehavior());
        /**
         * 校验谷歌验证码
         */
        final ResponseResult googleResult = this.checkCodeService.checkGoogleCode(form.getGoogleCode(), userInfo);
        if (googleResult != null && googleResult.getCode() > 0) {
            log.error("googleValidateSwitch, googleCode check error! userid={}, googleCode={}",
                    userId, form.getGoogleCode());
            return googleResult;
        }
        /**
         * 验证手机验证码
         */
        if (userSettings.getMobileAuthFlag() == UserDetailConsts.ENABLE_PHONE_AUTH) {
            final ResponseResult mobileResult = this.checkCodeService.checkMobileCode(userId,
                    behaviorTheme, form.getMobileCode(), null);
            if (mobileResult != null && mobileResult.getCode() > 0) {
                log.error("SecurityMobileController validateSwitch,check mobileCode fail! userid={}", userId);
                return mobileResult;
            }
        }
        /**
         * 验证邮箱验证码是否正确
         */
        if (userSettings.getEmailAuthFlag() == UserDetailConsts.ENABLE_EMAIL_AUTH) {
            final ResponseResult emailResult = this.checkCodeService.checkEmailCode(userId,
                    behaviorTheme, form.getEmailCode(), userSettings.getEmailAuthFlag(), null);
            if (emailResult != null && emailResult.getCode() > 0) {
                log.error("UsersInnerController checkCode, emailCode Check error! userid={}", userId);
                return emailResult;
            }
        }


        final boolean result;
        if (userSettings.getGoogleAuthFlag() == UserDetailConsts.DISABLE_LOGIN_AUTH) {
            result = this.userSettingsService.enableGoogleAuthFlag(userId, UserDetailConsts.ENABLE_LOGIN_AUTH);
        } else {
            result = this.userSettingsService.enableGoogleAuthFlag(userId, UserDetailConsts.DISABLE_LOGIN_AUTH);
            // 添加一条登录记录
            final UserDetailInfo userDetailInfo = ObjectCopyUtils.map(userInfo, UserDetailInfo.class);
            final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
            this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.GOOGLE_VERFICATION_CLOSE);
        }
        if (!result) {
            log.error("googleValidateSwitch,login google verify switch failed!userid={},result={}", userId, result);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_UPDATE_FAIL);
        }
        try {
            /**
             * 发送打开/关闭成功邮件
             */
            final Map params = Maps.newHashMap();
            params.put("antiphishing", userInfo.getAntiPhishingCode());

            this.noticeSendLogService.sendEmail(LocaleUtils.getLocale(request), behaviorTheme, UserNoticeRecordConsts.BUSINESS_NOTIFICATION,
                    userInfo.getEmail(), MessageTemplateConsts.MAIL_USERS_LOGIN_STEP2AUTH_SUCCESS, userId, params, this.getBrokerId(request));
        } catch (final Exception e) {
            log.error("loginValidateSwitch, send NOTICE email exception! userid={}", userId);
        }

        return ResultUtils.success();
    }

    /**
     * 申请谷歌验证码， 30分钟有效
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/apply")
    public ResponseResult googleKeyApply(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("loginValidateSwitch, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        // 获取用户信息
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("loginValidateSwitch, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        final UserSettings userSettings = this.userSettingsService.getById(userInfo.getId());

        final Map<String, Object> params = new HashMap<>();

        // 首次绑定google，本地生成密钥
        final String googleScretKey = GoogleAuthenticator.generateSecretKey();
        params.put("googleScretKey", googleScretKey);

        final String loginName = StringUtils.isNotBlank(userInfo.getMobile()) ? userInfo.getMobile() : userInfo.getEmail();
        final String otpauth = GoogleAuthenticator.getQRBarcode(loginName, AppEnvConsts.DOMAIN + '-', googleScretKey);
        params.put("otpauth", otpauth);

        // 10分钟之内有效
        this.appCacheService.setGoogleSecret(userInfo.getId(), googleScretKey);

        // 如果没有谷歌密钥。则说明是第一次绑定
        if (StringUtils.isEmpty(userInfo.getGoogleCode())) {
            params.put("behavior", BehaviorTheme.USERS5_GOOGLE_BIND_1.getBehavior());
        } else {
            params.put("behavior", BehaviorTheme.USERS5_GOOGLE_RESET_2.getBehavior());
        }
        // 开启操作 & 关闭操作 都需要google+sms
        if (userSettings.getMobileAuthFlag() == UserDetailConsts.ENABLE_PHONE_AUTH &&
                userSettings.getEmailAuthFlag() == UserDetailConsts.ENABLE_EMAIL_AUTH) {
            params.put("type", UserVerifyTypeEnum.TYPE_USE_GOOGLE_SMS_EMAIL.getType());
            return ResultUtils.success(params);
        }
        //如果绑定邮箱，需要校验谷歌和邮箱
        if (userSettings.getEmailAuthFlag() == UserDetailConsts.ENABLE_EMAIL_AUTH) {
            params.put("type", UserVerifyTypeEnum.TYPE_USE_GOOGLE_EMAIL.getType());
            return ResultUtils.success(params);
        }
        //如果绑定手机，需要校验谷歌和手机号码
        if (userSettings.getMobileAuthFlag() == UserDetailConsts.ENABLE_PHONE_AUTH) {
            params.put("type", UserVerifyTypeEnum.TYPE_USE_GOOGLE_SMS.getType());
            return ResultUtils.success(params);
        }
        params.put("type", UserVerifyTypeEnum.TYPE_USE_GOOGLE_ONLY.getType());

        return ResultUtils.success(params);
    }

    /**
     * 绑定google身份验证器
     *
     * @param form
     * @param request
     * @return
     */
    @RetryLimit(type = RetryLimitTypeEnum.GOOGLE_CODE_BIND)
    @PostMapping(value = "/bind")
    public ResponseResult gcodeAuthBind(@RequestBody @Valid final GoogleAuthChangeVO form, final HttpServletRequest request) {

        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("gcodeAuthBind, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        // 获取用户信息
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("gcodeAuthBind, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        final UserSettings userSettings = this.userSettingsService.getById(userInfo.getId());

        // 用户必须绑定过验证码
        if (StringUtils.isNotBlank(userInfo.getGoogleCode())) {
            log.error("googleAuthReset, user already bind google!", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS);
        }

        // 获取缓存中的谷歌验证码
        final String googleKey = this.appCacheService.getGoogleSecret(userId);
        if (StringUtils.isBlank(googleKey)) {
            log.error("gcodeAuthBind, google key empty! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.GOOGLE_KEY_EMPTY);
        }

        // 本地谷歌验证码校验
        final boolean googleResult = GoogleAuthenticator.checkCode(googleKey, Long.parseLong(form.getGoogleCode()),
                System.currentTimeMillis());
        if (BooleanUtils.isFalse(googleResult)) {
            log.error("gcodeAuthBind, googleCode check error! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.GOOGLE_CODE_VERIFY_ERROR);
        }

        final String ip = IpUtil.getRealIPAddress(request);
        // 优先 绑了手机使用手机流程(验证手机+谷歌验证码)
        if (userSettings.getEmailAuthFlag() == UserDetailConsts.ENABLE_EMAIL_AUTH) {
            // 邮箱验证
            final ResponseResult emailResult = this.checkCodeService.checkEmailCode(userId,
                    BehaviorTheme.USERS5_GOOGLE_BIND_1, form.getEmailCode(), userSettings.getEmailAuthFlag(), ip);
            if (emailResult != null && emailResult.getCode() > 0) {
                log.error("gcodeAuthBind, emailCode Check error! userid={}", userId);
                return emailResult;
            }
        }
        if (userSettings.getMobileAuthFlag() == UserDetailConsts.ENABLE_PHONE_AUTH) {
            // 验证手机是否正确
            final ResponseResult phoneResult = this.checkCodeService.checkMobileCode(userId,
                    BehaviorTheme.USERS5_GOOGLE_BIND_1, form.getMobileCode(), ip);
            if (phoneResult != null && phoneResult.getCode() > 0) {
                log.error("gcodeAuthBind, mobileCode check error! userid={}", userId);
                return phoneResult;
            }
        }

        // 谷歌密钥插入中央库
        final long resultAuthenticator = this.userInfoService.generateGoogelKey(userId, AESUtil.encrypt(googleKey, CommonConsts.ASE_KEY));
        if (resultAuthenticator < 0) {
            log.error("gcodeAuthBind, generate googlekey failed! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_CREATE_FAIL);
        }
        this.appCacheService.deleteGoogleSecret(userId);

        try {
            final Map params = Maps.newHashMap();
            params.put("antiphishing", userInfo.getAntiPhishingCode());
            this.noticeSendLogService.sendEmail(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS5_GOOGLE_BIND_1,
                    NoticeSendLogConsts.BUSINESS_NOTIFICATION, userInfo.getEmail(),
                    MessageTemplateConsts.MAIL_USERS_GOOGLE_BIND_SUCCESS, userId, params, this.getBrokerId(request));
        } catch (final Exception e) {
            log.error("gcodeAuthBind, send NOTICE email exception! userid={}, e={}", userId, e);
        }
        // 添加一条登录记录
        final UserDetailInfo userDetailInfo = ObjectCopyUtils.map(userInfo, UserDetailInfo.class);
        final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.GOOGLE_CODE_BIND);


        return ResultUtils.success();
    }

    /**
     * 解绑google身份验证器
     *
     * @param form
     * @param request
     * @return
     */
    @RetryLimit(type = RetryLimitTypeEnum.GOOGLE_CODE_UNBIND)
    @PutMapping(value = "/unbind")
    public ResponseResult unbindGoogle(@RequestBody @Valid final GoogleAuthResetVO form, final HttpServletRequest request) {

        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("unbindGoogle, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("unbindGoogle, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final String ip = IpUtil.getRealIPAddress(request);
        // 绑了手机使用手机流程(验证手机+新谷歌验证码)
        final UserSettings userSettings = this.userSettingsService.getById(userId);

        if (userSettings.getEmailAuthFlag() == UserDetailConsts.ENABLE_EMAIL_AUTH) {
            // 邮箱验证
            final ResponseResult emailResult = this.checkCodeService.checkEmailCode(userId,
                    BehaviorTheme.USERS5_GOOGLE_RESET_2, form.getEmailCode(), userSettings.getEmailAuthFlag(), ip);
            if (emailResult != null && emailResult.getCode() > 0) {
                log.error("unbindGoogle, emailCode Check error! userid={}", userId);
                return emailResult;
            }
        }

        if (userSettings.getMobileAuthFlag() == UserDetailConsts.ENABLE_PHONE_AUTH) {
            // 验证手机是否正确
            final ResponseResult phoneResult = this.checkCodeService.checkMobileCode(userId,
                    BehaviorTheme.USERS5_GOOGLE_RESET_2, form.getMobileCode(), ip);
            if (phoneResult != null && phoneResult.getCode() > 0) {
                log.error("unbindGoogle, mobileCode check error! userid={}", userId);
                return phoneResult;
            }
        }

        // 更新谷歌验证码
        final boolean flag = this.userInfoService.unbindGoogle(userInfo.getId());
        if (BooleanUtils.isFalse(flag)) {
            log.error("unbindGoogle is error! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.UNBIND_GOOGLE_ERROR);
        }
        this.appCacheService.deleteGoogleSecret(userId);

        try {
            final Map params = Maps.newHashMap();
            params.put("antiphishing", userInfo.getAntiPhishingCode());
            this.noticeSendLogService.sendEmail(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS5_GOOGLE_RESET_2,
                    NoticeSendLogConsts.BUSINESS_NOTIFICATION, userInfo.getEmail(),
                    MessageTemplateConsts.MAIL_USERS_GOOGLE_RESET_SUCCESS, userId, params, this.getBrokerId(request));
        } catch (final Exception e) {
            log.error("unbindGoogle, sendemail exception! userid={}, e={}", userId, e);
        }

        // 添加一条登录记录
        final UserDetailInfo userDetailInfo = ObjectCopyUtils.map(userInfo, UserDetailInfo.class);
        final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.GOOGLE_CODE_RESET);

        return ResultUtils.success();
    }
}
