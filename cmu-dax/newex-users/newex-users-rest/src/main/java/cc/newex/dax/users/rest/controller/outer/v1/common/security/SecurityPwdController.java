package cc.newex.dax.users.rest.controller.outer.v1.common.security;

import cc.newex.commons.lang.util.PwdStrengthUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.security.jwt.model.JwtConsts;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenProvider;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.consts.UserDetailConsts;
import cc.newex.dax.users.common.consts.UserNoticeRecordConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.enums.security.UserVerifyTypeEnum;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserLoginInfo;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.common.service.EventFacadeService;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.rest.model.AccessTokenResVO;
import cc.newex.dax.users.rest.model.ModifyPwdVO;
import cc.newex.dax.users.rest.model.SwitchLoginVO;
import cc.newex.dax.users.rest.model.VerifyLoginNewReqVO;
import cc.newex.dax.users.rest.model.VerifyLoginReqVO;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import cc.newex.dax.users.service.verification.CheckCodeService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * 当前控制器(APP)，包含四个业务:
 * 	1. 登录密码修改
 *  2. 交易密码修改
 *  3. 交易密码添加
 *  4. 交易密码忘记
 * </pre>
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/security/pwd")
public class SecurityPwdController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserNoticeEventService noticeSendLogService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private UserNoticeEventService userNoticeEventService;
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EventFacadeService operatorFacadeService;
    @Autowired
    private AppCacheService appCacheService;

    /**
     * GO修改登录密码
     *
     * @param request
     * @return
     */
    @GetMapping(value = "modify-login-pwd-go")
    public ResponseResult<?> goModifyLoginPwd(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("modifyLoginPwd, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        // 获取用户信息
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("goModifyLoginPwd, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        final Map<String, Integer> params = new HashMap<>();
        params.put("behavior", BehaviorTheme.USERS2_LOGIN_MODIFY_PASSWORD_3.getBehavior());
        params.put("type", UserVerifyTypeEnum.TYPE_USE_NULL.getType());
        return ResultUtils.success(params);
    }

    /**
     * 用户中心修改登录密码，修改成功，前端需要退出(modify-login-pwd)
     */
    @PostMapping(value = "/login-pwd")
    public ResponseResult<?> modifyLoginPwd(final HttpServletRequest request, @RequestBody @Valid final ModifyPwdVO form) {

        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("modifyLoginPwd, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        // 获取用户信息
        final UserLoginInfo userInfo = this.userInfoService.getUserLoginInfoById(userId);
        if (userInfo == null) {
            log.error("modifyLoginPwd, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        /**
         * 密码强度校验
         */
        final Integer level = PwdStrengthUtil.getStrengthLevel(form.getNewPwd());
        if (level < 2) {
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_PASSWORD_SIMPLE);
        }
        /**
         *验证两个新密码是否一致
         */
        if (StringUtil.notEquals(form.getNewPwd(), form.getReNewPwd())) {
            log.error("modifyLoginPwd, newpassword and renewpwd  error! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.PWD_CONFIRM_NOT_EQUAL);
        }
        /**
         *验证旧密码是否正确
         */
        if (!this.passwordEncoder.matches(form.getOriginPwd(), userInfo.getPassword())) {
            log.error("modifyLoginPwd, old password error! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.LOGIN_PWD_ERROR);
        }

        // 添加一条登录记录
        final UserDetailInfo userDetailInfo = ObjectCopyUtils.map(userInfo, UserDetailInfo.class);
        final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.LOGIN_PWD_MODIFY);

        /**
         * 实际业务发生逻辑
         * 密码强度检查
         */
        final int pwdStrongFlag = PwdStrengthUtil.getStrengthLevel(form.getNewPwd());
        final String newPassword = this.passwordEncoder.encode(form.getNewPwd());
        final long returnValue = this.userInfoService.resetPassword(userInfo.getId(), newPassword, pwdStrongFlag);
        if (returnValue > 0) {
            if (StringUtil.isEmail(userInfo.getEmail())) {
                final Map params = Maps.newHashMap();
                params.put("antiphishing", userInfo.getAntiPhishingCode());
                // 发送邮件
                this.noticeSendLogService.sendEmail(LocaleUtils.getLocale(request),
                        BehaviorTheme.USERS2_LOGIN_MODIFY_PASSWORD_3,
                        NoticeSendLogConsts.BUSINESS_NOTIFICATION, userInfo.getEmail(),
                        MessageTemplateConsts.MAIL_USERS_LOGIN_PWD_RESET, userId, params, this.getBrokerId(request));
            }
            //退出
            JwtTokenUtils.removeSessionUser(userId);
            JwtTokenUtils.clearSession(request);

            return ResultUtils.success();
        }

        return ResultUtils.failure(BizErrorCodeEnum.COMMON_UPDATE_FAIL);
    }

    /**
     * 二次登录验证开关
     */
    @PostMapping(value = "/switch-login")
    public ResponseResult<?> switchLogin(final HttpServletRequest request, @RequestBody @Valid final SwitchLoginVO form) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("switchLogin, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        // 获取用户信息
        final UserLoginInfo userLoginInfo = this.userInfoService.getUserLoginInfoById(userId);
        if (userLoginInfo == null) {
            log.error("switchLogin, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        /**
         * 校验用户登录二次验证开关验证码
         */
        final ResponseResult checkResult = this.checkCodeService.checkCodeByBehavior(userId, BehaviorTheme.USERS2_LOGIN_STEP2AUTH_SWITCH,
                form.getVerificationCode());
        if (ObjectUtils.isEmpty(checkResult) || checkResult.getCode() > 0) {
            log.error("switchLogin,check switch code is error.userId={},behavior={},verificationCode={}", userId,
                    BehaviorTheme.USERS2_LOGIN_STEP2AUTH_SWITCH.getBehavior(), form.getVerificationCode());
            return checkResult;
        }
        final UserSettings userSettings = this.userSettingsService.getById(userId);
        final int loginFlag = userSettings.getLoginAuthFlag() == UserDetailConsts.DISABLE_LOGIN_AUTH ? UserDetailConsts.ENABLE_LOGIN_AUTH :
                UserDetailConsts.DISABLE_LOGIN_AUTH;

        final boolean returnValue = this.userSettingsService.enableLoginAuthFlag(userId, loginFlag);
        if (BooleanUtils.isTrue(returnValue)) {
            log.info("switchLogin, enableLoginAuthFlag success,userId={},loginFlag={}", userId, loginFlag);
            return ResultUtils.success();
        }

        return ResultUtils.failure(BizErrorCodeEnum.COMMON_UPDATE_FAIL);
    }

    /**
     * @param form
     * @param request
     * @description 二次登录验证
     * @date 2018/4/26 下午4:30
     */
    @RetryLimit(type = RetryLimitTypeEnum.VERIFY_LOGIN)
    @PostMapping(value = "/verify-login")
    public ResponseResult verifyLogin(@RequestBody @Valid final VerifyLoginReqVO form, final HttpServletRequest request) {
        // 判断令牌的状态
        final int status = HttpSessionUtils.getUserTokenStatus(request);
        if (status != JwtConsts.STATUS_TWO_FACTOR) {
            log.error("verifyLogin, user login failure!");
            return ResultUtils.failure(BizErrorCodeEnum.LOGIN_USER_AUTHORISE_FAIL);
        }

        // 获取当前二次登录验证用户的JWT
        final JwtUserDetails jwtUserDetails = JwtTokenUtils.getCurrentLoginUser(request);
        if (jwtUserDetails == null || jwtUserDetails.getStatus() != JwtConsts.STATUS_TWO_FACTOR) {
            log.error("LoginController loginStep2, user login failure");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final String key = StringUtils.join(jwtUserDetails.getUserId());
        final String loginUserId = this.appCacheService.getTwoFactorLoginUserId(key);
        if (StringUtils.isEmpty(loginUserId)) {
            log.error("verifyLogin, request params illage! params={}", JSON.toJSONString(form));
            return ResultUtils.failure(BizErrorCodeEnum.LOGIN_LOGINSTEP2_TOKEN_ERROR);
        }

        final ResponseResult checkResult = this.checkCodeService.checkCodeByBehavior(
                jwtUserDetails.getUserId(),
                BehaviorTheme.USERS2_LOGIN_STEP2AUTH_2,
                form.getVerificationCode()
        );
        if (ObjectUtils.isEmpty(checkResult) || checkResult.getCode() > 0) {
            log.error("verifyLogin,check step2 code is error.userId={},behavior={},verificationCode={}",
                    jwtUserDetails.getUserId(),
                    BehaviorTheme.USERS2_LOGIN_STEP2AUTH_2.getBehavior(),
                    form.getVerificationCode()
            );
            return checkResult;
        }

        final UserDetailInfo userDetailInfo = this.userInfoService.getUserDetailInfo(Long.valueOf(loginUserId));

        //将来用异步处理
        this.sendLoginSuccessEmail(form, userDetailInfo, request);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.LOGIN_STEP2_AUTH);

        final JwtUserDetails userDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        return ResultUtils.success(AccessTokenResVO.builder()
                .accessToken(JwtTokenUtils.generateTokenAndCreateSession(userDetails, request))
                .refreshToken("")
                .scopes(Lists.newArrayList())
                .build()
        );
    }

    /**
     * @param form
     * @param request
     * @description 二次登录验证
     */
    @RetryLimit(type = RetryLimitTypeEnum.VERIFY_LOGIN)
    @PostMapping(value = "/verify-login-new")
    public ResponseResult verifyLoginNew(@RequestBody @Valid final VerifyLoginNewReqVO form, final HttpServletRequest request) {
        // 判断令牌的状态
        final int status = HttpSessionUtils.getUserTokenStatus(request);
        if (status != JwtConsts.STATUS_TWO_FACTOR) {
            log.error("verifyLogin, user login failure!");
            return ResultUtils.failure(BizErrorCodeEnum.LOGIN_USER_AUTHORISE_FAIL);
        }

        // 获取当前二次登录验证用户的JWT
        final JwtUserDetails jwtUserDetails = JwtTokenUtils.getCurrentLoginUser(request);
        if (jwtUserDetails == null || jwtUserDetails.getStatus() != JwtConsts.STATUS_TWO_FACTOR) {
            log.error("LoginController loginStep2, user login failure");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final String key = StringUtils.join(jwtUserDetails.getUserId());
        final String loginUserId = this.appCacheService.getTwoFactorLoginUserId(key);
        if (StringUtils.isEmpty(loginUserId)) {
            log.error("verifyLogin, request params illage! params={}", JSON.toJSONString(form));
            return ResultUtils.failure(BizErrorCodeEnum.LOGIN_LOGINSTEP2_TOKEN_ERROR);
        }

        final ResponseResult checkResult = this.checkCodeService.checkCodeByBehaviorNew(
                jwtUserDetails.getUserId(),
                BehaviorTheme.USERS2_LOGIN_STEP2AUTH_2,
                form.getVerificationType(),
                form.getVerificationCode()
        );
        if (ObjectUtils.isEmpty(checkResult) || checkResult.getCode() > 0) {
            log.error("verifyLogin,check step2 code is error.userId={},behavior={},verificationType={},verificationCode={}",
                    jwtUserDetails.getUserId(),
                    BehaviorTheme.USERS2_LOGIN_STEP2AUTH_2.getBehavior(),
                    form.getVerificationType(),
                    form.getVerificationCode()
            );
            return checkResult;
        }

        final UserDetailInfo userDetailInfo = this.userInfoService.getUserDetailInfo(Long.valueOf(loginUserId));

        //将来用异步处理
        this.sendLoginSuccessEmail(form, userDetailInfo, request);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.LOGIN_STEP2_AUTH);

        final JwtUserDetails userDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        return ResultUtils.success(AccessTokenResVO.builder()
                .accessToken(JwtTokenUtils.generateTokenAndCreateSession(userDetails, request))
                .refreshToken("")
                .scopes(Lists.newArrayList())
                .build()
        );
    }

    private void sendLoginSuccessEmail(final Object form, final UserDetailInfo userDetailInfo, final HttpServletRequest request) {
        if (!StringUtil.isEmail(userDetailInfo.getEmail())) {
            return;
        }

        final Map<String, Object> params = Maps.newHashMap();
        params.put("antiphishing", userDetailInfo.getAntiPhishingCode());
        if (userDetailInfo.getEmailAuthFlag() == UserDetailConsts.ENABLE_EMAIL_AUTH) {
            try {
                this.userNoticeEventService.sendEmail(
                        LocaleUtils.getLocale(request),
                        BehaviorTheme.USERS2_LOGIN_STEP2AUTH_2,
                        UserNoticeRecordConsts.BUSINESS_NOTIFICATION,
                        userDetailInfo.getEmail(),
                        MessageTemplateConsts.MAIL_USERS_LOGIN_STEP2AUTH_SUCCESS,
                        userDetailInfo.getId(),
                        params, this.getBrokerId(request)
                );
            } catch (final Exception e) {
                log.error("verifyLogin, send NOTICE email exception, data={}, e={}", form, e);
            }
        }
    }
}
