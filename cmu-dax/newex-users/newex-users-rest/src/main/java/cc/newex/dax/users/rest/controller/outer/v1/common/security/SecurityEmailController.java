package cc.newex.dax.users.rest.controller.outer.v1.common.security;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.enums.security.UserVerifyTypeEnum;
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
import cc.newex.dax.users.rest.model.BindEmailVO;
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
@RequestMapping(value = "/v1/users/security/email")
public class SecurityEmailController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserNoticeEventService noticeSendLogService;
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private EventFacadeService operatorFacadeService;


    @GetMapping(value = "/bind-go")
    public ResponseResult goSubmitBindEmail(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("SecurityEmailController goSubmitBindEmail, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        // 获取用户信息
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("SecurityEmailController goSubmitBindEmail, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        //邮箱已经绑定了
        if (StringUtil.isEmail(userInfo.getEmail())) {
            return ResultUtils.failure(BizErrorCodeEnum.EMAIL_HAS_USED);
        }

        final Map<String, Integer> params = new HashMap<>();
        params.put("behavior", BehaviorTheme.USERS4_EMAIL_BIND_1.getBehavior());
        params.put("type", UserVerifyTypeEnum.TYPE_USE_EMAIL_ONLY.getType());

        return ResultUtils.success(params);
    }

    @RetryLimit(type = RetryLimitTypeEnum.EMAIL_BIND)
    @PostMapping(value = "/bind")
    public ResponseResult submitBindEmail(@RequestBody @Valid final BindEmailVO form, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("SecurityController openTradePwd, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        // 校验邮箱是否已经被使用
        if (this.userInfoService.checkLoginName(form.getEmail())) {
            log.error("SecurityEmailController submitBindEmail, email has used! userid={}, email={}", userInfo.getId(),
                    form.getEmail());
            return ResultUtils.failure(BizErrorCodeEnum.EMAIL_HAS_USED);
        }

        //邮箱已经绑定了
        if (StringUtil.isEmail(userInfo.getEmail())) {
            return ResultUtils.failure(BizErrorCodeEnum.EMAIL_HAS_USED);
        }
        final String ip = IpUtil.getRealIPAddress(request);
        final UserSettings userSettings = this.userSettingsService.getById(userId);
        // 验证邮箱验证码是否正确
        final ResponseResult responseResult = this.checkCodeService.checkEmailCode(userId, form.getEmail(),
                BehaviorTheme.USERS4_EMAIL_BIND_1, form.getVerificationCode(), 30, userSettings.getEmailAuthFlag(), ip);
        if (responseResult != null && responseResult.getCode() != 0) {
            log.error("SecurityEmailController submitBindEmail, emailCode checked error! userId={} code={}",
                    userInfo.getId(), form.getVerificationCode());
            return responseResult;
        }

        final long resutlCode = this.userInfoService.updateEmail(userInfo.getId(), form.getEmail(),
                IpUtil.toLong(ip), true);
        if (resutlCode <= 0) {
            log.error("SecurityEmailController submitBindEmail, update user email fialed! userid={}, email={}",
                    userInfo.getId(), form.getVerificationCode());
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_BINDING_FAIL);
        }
        // 添加一条登录记录
        final UserDetailInfo userDetailInfo = ObjectCopyUtils.map(userInfo, UserDetailInfo.class);
        final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.EMAIL_BIND);

        try {
            // 发送邮件
            final Map<String, Object> params = new HashMap<>();
            params.put("shadeEmail", StringUtil.getStarEmail(form.getEmail()));
            this.noticeSendLogService.sendSMS(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS4_EMAIL_BIND_1, NoticeSendLogConsts.BUSINESS_NOTIFICATION,
                    userInfo.getMobile(), userInfo.getAreaCode(),
                    MessageTemplateConsts.SMS_USERS_MAIL_BIND_SUCCESS, userId, params, this.getBrokerId(request));
            params.put("antiphishing", userInfo.getAntiPhishingCode());
            this.noticeSendLogService.sendEmail(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS4_EMAIL_BIND_1,
                    NoticeSendLogConsts.BUSINESS_NOTIFICATION, form.getEmail(),
                    MessageTemplateConsts.MAIL_USERS_BIND_SUCCESS, userId, params, this.getBrokerId(request));
        } catch (final Exception e) {
            log.error("SecurityEmailController submitBindEmail, send notice email Exception={}", e);
        }
        return ResultUtils.success();
    }

    /**
     * 设置/修改邮件防钓鱼码(set-email-verify)
     *
     * @param request
     * @param emailVerify
     * @return
     */
    @PostMapping(value = "/anti-phishing-code")
    public ResponseResult setEmailVerify(@RequestBody final String emailVerify, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        final UserInfo uBean = this.userInfoService.getUserInfo(userId);
        if (uBean == null) {
            log.error("SecurityEmailController setEmailVerify, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        // 钓鱼码是否符合规则
        if (StringUtils.isEmpty(emailVerify) || emailVerify.length() > 20 || emailVerify.length() < 1) {
            log.error("SecurityEmailController setEmailVerify, emailVerify format error! userid={}, emailVerify={}",
                    userId, emailVerify);
            return ResultUtils.failure(BizErrorCodeEnum.EMAIL_VERTIFY_BAD);
        }

        // 更新邮件防钓鱼码
        final long result = this.userInfoService.updateEmailVerify(userId, emailVerify);
        if (result <= 0) {
            log.error("SecurityEmailController setEmailVerify, change emailVerify failed! userid={}, emailVerify={}",
                    userId, emailVerify);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_UPDATE_FAIL);
        }

        try {
            // 发送邮件
            final Map<String, Object> emailParams = new HashMap<>();
            emailParams.put("firstAntiphishing", emailVerify);
            this.noticeSendLogService.sendEmail(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS4_EMAIL_SETVERIFY_4, NoticeSendLogConsts.BUSINESS_NOTIFICATION,
                    uBean.getEmail(), MessageTemplateConsts.MAIL_USERS_ANTIPHISHING_SET, userId, emailParams, this.getBrokerId(request));
        } catch (final Exception e) {
            log.error("SecurityEmailController activateEmail, send mail activation-code Exception={}", e);
        }
        return ResultUtils.success();
    }

}
