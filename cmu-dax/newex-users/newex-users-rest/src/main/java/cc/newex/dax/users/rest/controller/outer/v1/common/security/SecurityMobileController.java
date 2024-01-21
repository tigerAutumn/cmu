package cc.newex.dax.users.rest.controller.outer.v1.common.security;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.consts.UserDetailConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.enums.security.UserVerifyTypeEnum;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.common.util.MobileUtils;
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
import cc.newex.dax.users.rest.model.AddMobileVO;
import cc.newex.dax.users.rest.model.ModifyMobileVO;
import cc.newex.dax.users.rest.model.SwitchMobileVO;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import cc.newex.dax.users.service.verification.CheckCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/v1/users/security/mobile")
public class SecurityMobileController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private UserNoticeEventService noticeSendLogService;
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private EventFacadeService operatorFacadeService;

    /**
     * GO添加手机号
     *
     * @param request
     * @return
     */
    @GetMapping(value = "add-go")
    public ResponseResult goAddMobile(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("SecurityMobileController goAddMobile, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        /**
         * 获取用户信息
         */
        final UserInfo uBean = this.userInfoService.getUserInfo(userId);
        if (uBean == null) {
            log.error("SecurityMobileController goAddMobile, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        final Map<String, Integer> params = new HashMap<>();

        params.put("behavior", BehaviorTheme.USERS3_PHONE_BIND_1.getBehavior());
        params.put("type", Integer.valueOf(UserVerifyTypeEnum.TYPE_USE_SMS_ONLY.getType()));

        return ResultUtils.success(params);
    }

    /**
     * 添加手机号
     */
    @RetryLimit(type = RetryLimitTypeEnum.MOBILE_BIND)
    @PostMapping(value = "/bind")
    public ResponseResult addMobile(@RequestBody @Valid final AddMobileVO vo, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("SecurityMobileController addMobile, user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("SecurityMobileController addMobile, fetch fail! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final boolean isExist = this.userInfoService.checkLoginName(vo.getMobile());
        if (isExist) {
            log.error("SecurityMobileController addMobile, the mobile has been used!  userid={}, mobile={}", userId,
                    vo.getVerificationCode() + "-" + vo.getMobile());
            return ResultUtils.failure(BizErrorCodeEnum.PHONE_HAS_USED);
        }

        final String ip = IpUtil.getRealIPAddress(request);
        final ResponseResult checkMobileResult = this.checkCodeService.checkMobileCode(userId,
                vo.getAreaCode() + vo.getMobile(), BehaviorTheme.USERS3_PHONE_BIND_1, vo.getVerificationCode(), 30, ip);
        if (checkMobileResult.getCode() != 0) {
            log.error("SecurityMobileController addMobile, check MobileCode fail! userid={}, mobile={}, mobileCode={}.",
                    userId, vo.getMobile(), vo.getVerificationCode());
            return checkMobileResult;
        }
        // 绑定手机号
        final long resultMobile = this.userInfoService.updateMobile(
                userInfo.getId(),
                vo.getMobile(), vo.getAreaCode(),
                IpUtil.toLong(ip), false);
        if (resultMobile <= 0) {
            log.error("SecurityMobileController addMobile, add user mobile fail! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_UPDATE_FAIL);
        }

        try {
            final Map<String, Object> params = new HashMap<>();
            params.put("phoneSuffix", MobileUtils.getMobileSuffix(vo.getMobile(), 4));
            /**
             * 发送短信和邮箱提醒
             */
            this.noticeSendLogService.sendSMS(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS3_PHONE_BIND_1, NoticeSendLogConsts.BUSINESS_NOTIFICATION,
                    vo.getMobile(), vo.getAreaCode(),
                    MessageTemplateConsts.SMS_USERS_MOBILE_BIND_SUCCESS, userId, params, this.getBrokerId(request));
            params.put("antiphishing", userInfo.getAntiPhishingCode());
            this.noticeSendLogService.sendEmail(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS3_PHONE_BIND_1,
                    NoticeSendLogConsts.BUSINESS_NOTIFICATION, userInfo.getEmail(),
                    MessageTemplateConsts.MAIL_USERS_MOBILE_BIND_SUCCESS, userId, params, this.getBrokerId(request));
        } catch (final Exception e) {
            log.error("SecurityMobileController addMobile, send notice msg failed! userid={}", userId);
        }

        // 添加一条登录记录
        final UserDetailInfo userDetailInfo = ObjectCopyUtils.map(userInfo, UserDetailInfo.class);
        final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.MOBILE_BIND);


        return ResultUtils.success();
    }

    /**
     * 修改手机号
     */
    @GetMapping(value = "update-go")
    public ResponseResult goUpdateMobile(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("SecurityMobileController goUpdateMobile, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        // 获取用户信息
        final UserInfo uBean = this.userInfoService.getUserInfo(userId);
        if (uBean == null) {
            log.error("SecurityMobileController goUpdateMobile, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        if (StringUtils.isBlank(uBean.getMobile())) {
            log.error("SecurityMobileController goUpdateMobile, user nerver set mobile! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.PHONE_EMPTY);
        }

        final Map<String, Integer> params = new HashMap<>();
        params.put("behavior", BehaviorTheme.USERS3_PHONE_MODIFY_2.getBehavior());
        params.put("type", UserVerifyTypeEnum.TYPE_USE_SMS_ONLY.getType());
        return ResultUtils.success(params);
    }

    /**
     * 修改绑定手机号
     */
    @RetryLimit(type = RetryLimitTypeEnum.MOBILE_UPDATE)
    @PostMapping(value = "/update")
    public ResponseResult updateMobile(@RequestBody @Valid final ModifyMobileVO form, final HttpServletRequest request) {

        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("SecurityMobileController updateMobile, user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        // 获取用户信息
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("SecurityMobileController updateMobile, UserUniform info fetch fail! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        if (StringUtils.isBlank(userInfo.getMobile())) {
            log.error("SecurityMobileController updateMobile, nobind mobile cannot update! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.PHONE_EMPTY);
        }

        final boolean isExist = this.userInfoService.checkLoginName(form.getMobile());
        // 手机号已存在
        if (isExist) {
            log.error("SecurityMobileController updateMobile, the mobile has been used!  userid={}, mobile={}", userId,
                    form.getAreaCode() + "-" + form.getMobile());
            return ResultUtils.failure(BizErrorCodeEnum.PHONE_HAS_USED);
        }

        final String ip = IpUtil.getRealIPAddress(request);
        // 老的短信验证码是否过期
        ResponseResult checkCode = this.checkCodeService.checkMobileCode(userId,
                userInfo.getAreaCode() + userInfo.getMobile(),
                BehaviorTheme.USERS3_PHONE_MODIFY_2, form.getOldVerificationCode(), -1, ip);
        if (checkCode.getCode() != 0) {
            checkCode = ResultUtils.failure(BizErrorCodeEnum.SMS_OLD_CODE_VERIFY_ERROR);
            log.error("SecurityMobileController updateMobile, old mobile`s code has been overdue! userid={}", userId);
            return checkCode;
        }

        // 新的短信验证码是否过期
        checkCode = this.checkCodeService.checkMobileCode(userId, form.getAreaCode() + form.getMobile(),
                BehaviorTheme.USERS3_PHONE_MODIFY_2, form.getNewVerificationCode(), -1, ip);
        if (checkCode.getCode() != 0) {
            checkCode = ResultUtils.failure(BizErrorCodeEnum.SMS_NEW_CODE_VERIFY_ERROR);
            log.error("SecurityMobileController updateMobile, new mobile`s code has been overdue! userid={}", userId);
            return checkCode;
        }

        // 更新手机号
        final long resultMobile = this.userInfoService.updateMobile(userInfo.getId(), form.getMobile(), form.getAreaCode(),
                IpUtil.toLong(ip), true);
        if (resultMobile <= 0) {
            log.error("SecurityMobileController updateMobile, update user mobile fail! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_UPDATE_FAIL);
        }

        try {
            final Map<String, Object> params = new HashMap<>();
            params.put("phoneSuffix", MobileUtils.getMobileSuffix(form.getMobile(), 4));
            this.noticeSendLogService.sendSMS(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS3_PHONE_MODIFY_2, NoticeSendLogConsts.BUSINESS_NOTIFICATION,
                    form.getMobile(), form.getAreaCode(),
                    MessageTemplateConsts.SMS_USERS_MOBILE_MODIFY_SUCCESS, userId, params, this.getBrokerId(request));

            params.put("antiphishing", userInfo.getAntiPhishingCode());
            this.noticeSendLogService.sendEmail(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS3_PHONE_MODIFY_2,
                    NoticeSendLogConsts.BUSINESS_NOTIFICATION, userInfo.getEmail(),
                    MessageTemplateConsts.MAIL_USERS_MOBILE_MODIFY_SUCCESS, userId, params, this.getBrokerId(request));
        } catch (final Exception e) {
            log.error("SecurityMobileController updateMobile, send notice msg failed! userid={}", userId);
        }
        // 添加一条登录记录
        final UserDetailInfo userDetailInfo = ObjectCopyUtils.map(userInfo, UserDetailInfo.class);
        final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.MOBILE_MODIFY);

        return ResultUtils.success();
    }

    /**
     * GO开启或关闭手机验证(validate-switch-go)
     */
    @GetMapping(value = "/validate/switch/go")
    public ResponseResult goValidateSwitch(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("SecurityMobileController goValidateSwitch, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        // 获取用户信息
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("SecurityMobileController goValidateSwitch, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        final Map<String, Integer> params = new HashMap<>();

        // 谷歌是关闭状态，禁止关闭手机
        final UserSettings userSettings = this.userSettingsService.getById(userId);
        if (userSettings.getMobileAuthFlag() == UserDetailConsts.DISABLE_AUTH) {
            params.put("behavior", BehaviorTheme.USERS3_PHONE_TRADE_SWITCH_3.getBehavior());
        } else {
            params.put("behavior", BehaviorTheme.USERS3_PHONE_TRADE_SWITCH_4.getBehavior());
        }


        final int googleAuthFlag = userSettings.getGoogleAuthFlag();
        if (googleAuthFlag == UserDetailConsts.DISABLE_AUTH) {
            return ResultUtils.failure(BizErrorCodeEnum.SWITCH_CANTNOT_CLOSED_GOOGLESMS);
        }

        params.put("type", UserVerifyTypeEnum.TYPE_USE_SMS_ONLY.getType());
        if (StringUtils.isNotBlank((userInfo.getGoogleCode()))) {
            params.put("type", UserVerifyTypeEnum.TYPE_USE_GOOGLE_SMS.getType());
        }

        return ResultUtils.success(params);
    }

    /**
     * 开启或关闭手机验证
     */
    @RetryLimit(type = RetryLimitTypeEnum.ENABLE_VALIDATE)
    @PostMapping(value = "/validate/enable")
    public ResponseResult validateSwitch(@RequestBody @Valid final SwitchMobileVO form, final HttpServletRequest request) {

        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("SecurityMobileController validateSwitch, user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("SecurityMobileController validateSwitch, UserUniform info fecth fail! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        // 未绑定手机 不允许开启手机
        if (StringUtils.isEmpty(userInfo.getMobile())) {
            log.error("SecurityMobileController validateSwitch, nobind mobile cant open mobile verify! userid={}",
                    userId);
            return ResultUtils.failure(BizErrorCodeEnum.PHONE_EMPTY);
        }

        final String ip = IpUtil.getRealIPAddress(request);

        // 谷歌是关闭状态，禁止关闭手机
        final UserSettings userSettings = this.userSettingsService.getById(userId);
        final int googleAuthFlag = userSettings.getGoogleAuthFlag();
        if (googleAuthFlag == UserDetailConsts.DISABLE_AUTH) {
            return ResultUtils.failure(BizErrorCodeEnum.SWITCH_CANTNOT_CLOSED_GOOGLESMS);
        }

        // 验证谷歌验证码
        if (userSettings.getGoogleAuthFlag() == UserDetailConsts.ENABLE_GOOGLE_AUTH) {
            final ResponseResult googleCodeResult = this.checkCodeService.checkGoogleCode(form.getGoogleCode(), userInfo);
            if (googleCodeResult.getCode() != 0) {
                log.error("SecurityMobileController validateSwitch,check mobileCode fail! userid={}", userId);
                return googleCodeResult;
            }
        }

        // 验证短信验证码
        final ResponseResult checkCode = this.checkCodeService.checkMobileCode(userId,
                userSettings.getMobileAuthFlag() == UserDetailConsts.DISABLE_AUTH ? BehaviorTheme.USERS3_PHONE_TRADE_SWITCH_3
                        : BehaviorTheme.USERS3_PHONE_TRADE_SWITCH_4, form.getMobileCode(), ip);
        if (checkCode.getCode() != 0) {
            log.error("SecurityMobileController validateSwitch,check mobileCode fail! userid={}", userId);
            return checkCode;
        }


        // 更新手机验证开关 0：关闭，1：打开
        final boolean result;
        if (userSettings.getMobileAuthFlag() > 0) {
            // 添加一条登录记录
            final UserDetailInfo userDetailInfo = ObjectCopyUtils.map(userInfo, UserDetailInfo.class);
            final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
            this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.MOBILE_VERFICATION_CLOSE);

            result = this.userSettingsService.enabbeMobileAuthFlag(userId, UserDetailConsts.DISABLE_AUTH);
        } else {
            result = this.userSettingsService.enabbeMobileAuthFlag(userId, UserDetailConsts.ENABLE_PHONE_AUTH);
        }
        if (!result) {
            log.error("SecurityMobileController validateSwitch, validateSwitch " + result, userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_UPDATE_FAIL);
        }
        return ResultUtils.success();
    }
}
