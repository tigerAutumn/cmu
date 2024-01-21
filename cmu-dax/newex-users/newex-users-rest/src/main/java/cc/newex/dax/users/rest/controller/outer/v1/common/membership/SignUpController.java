package cc.newex.dax.users.rest.controller.outer.v1.common.membership;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.lang.util.PwdStrengthUtil;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.consts.UserConsts;
import cc.newex.dax.users.common.consts.UserNoticeRecordConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.MobileUtils;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.common.service.EventFacadeService;
import cc.newex.dax.users.rest.common.util.RegFromUtils;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.rest.model.AccessTokenResVO;
import cc.newex.dax.users.rest.model.EmailRegReqVO;
import cc.newex.dax.users.rest.model.MobileRegReqVO;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserInviteRecordService;
import cc.newex.dax.users.service.security.UserFavoritesService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import cc.newex.dax.users.service.verification.CheckCodeService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户注册控制器
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/membership/sign-up")
public class SignUpController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private CheckCodeService checkCodeService;

    @Autowired
    private UserNoticeEventService userNoticeEventService;

    @Autowired
    private EventFacadeService operatorFacadeService;

    @Autowired
    private UserInviteRecordService userInviteRecordService;

    @Autowired
    private UserFavoritesService userFavoritesService;

    @RetryLimit(type = RetryLimitTypeEnum.REGISTER)
    @PostMapping(value = "/mobile")
    public ResponseResult<?> registerByMobile(@RequestBody @Valid final MobileRegReqVO form, final HttpServletRequest request) {
        log.info("registerByMobile, ip: {}, form: {}", IpUtil.getRealIPAddress(request), JSON.toJSONString(form));

        // 验证手机号
        final boolean isPhone = MobileUtils.checkPhoneNumber(
                StringUtils.trim(form.getMobile()), String.valueOf(form.getAreaCode())
        );

        if (!isPhone) {
            log.error("mobile number format error! mobile={},areaCode={}", form.getMobile(), form.getAreaCode());
            return ResultUtils.failure(BizErrorCodeEnum.PHONE_FORMAT_ERROR);
        }

        // 密码强度校验
        final Integer level = PwdStrengthUtil.getStrengthLevel(form.getPassword());
        if (level < 2) {
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_PASSWORD_SIMPLE);
        }

        // 不支持注册的国家, 暂不支持你所选择的国家
        if (UserConsts.LIMITED_REG_COUNTRIES.contains(form.getAreaCode())) {
            log.error("current country {} is forbiden", form.getAreaCode());
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_COUNTRY_UNSUPPORT);
        }

        // 验证手机号是否已被别人绑定
        final String mobile = StringUtils.trim(form.getMobile());
        final boolean isPhoneBinded = this.userInfoService.checkLoginName(mobile);
        if (isPhoneBinded) {
            log.error("current mobile :{} is binded", form.getMobile());
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_USER_EXIST);
        }

        // 验证手机验证码是否正确
        final String ipAddress = IpUtil.getRealIPAddress(request);
        final ResponseResult smsResult = this.checkCodeService.checkMobileCode(
                -1L,
                form.getAreaCode() + form.getMobile(),
                BehaviorTheme.USERS1_REGISTER_PHONE_2,
                form.getVerificationCode(),
                -1,
                ipAddress);

        if (smsResult.getCode() > 0) {
            return smsResult;
        }

        final Integer brokerId = this.getBrokerId(request);

        // 注册用户
        final long userId = this.userInfoService.register(
                form.getMobile(),
                form.getPassword(),
                form.getAreaCode(),
                ipAddress,
                RegFromUtils.getRegFrom(request),
                form.getUtm_source(),
                brokerId
        );

        if (userId <= 0) {
            log.error("mobile register failure! user id:{}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_SAVE_FAILED);
        }

        final UserDetailInfo userDetailInfo = this.userInfoService.getUserDetailInfo(userId);
        if (userDetailInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_SAVE_FAILED);
        }

        /**
         * 生成邀请记录
         */
        this.userInviteRecordService.inviteRegister(userId, form.getInviteCode(), form.getActivityCode(), brokerId);
        try {
            // 发送注册成功短信
            this.userNoticeEventService.sendSMS(LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS1_REGISTER_PHONE_2, NoticeSendLogConsts.BUSINESS_NOTIFICATION,
                    userDetailInfo.getMobile(), userDetailInfo.getAreaCode(),
                    MessageTemplateConsts.SMS_USERS_MOBILE_REGISTER_SUCCESS, userId, Maps.newHashMap(), brokerId);
        } catch (final Exception e) {
            log.error("send register sms notice error, data={}, e={}", form, e);
        }

        final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.REGISTER_MOBILE);
        // 颁发令牌
        return ResultUtils.success(AccessTokenResVO.builder()
                .accessToken(JwtTokenUtils.generateTokenAndCreateSession(jwtUserDetails, request))
                .refreshToken("")
                .scopes(Lists.newArrayList())
                .build());
    }

    @RetryLimit(type = RetryLimitTypeEnum.REGISTER)
    @PostMapping(value = "/email")
    public ResponseResult<?> registerByEmail(@RequestBody @Valid final EmailRegReqVO form, final HttpServletRequest request) {
        log.info("registerByEmail, ip: {}, form: {}", IpUtil.getRealIPAddress(request), JSON.toJSONString(form));

        // 验证登录名
        final String email = StringUtils.trim(form.getEmail());
        final boolean isEmailBinded = this.userInfoService.checkLoginName(email);
        if (isEmailBinded) {
            log.error("email is exists! email:{}", form.getEmail());
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_USER_EXIST);
        }
        /**
         * 密码强度校验
         */
        final Integer level = PwdStrengthUtil.getStrengthLevel(form.getPassword());
        if (level < 2) {
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_PASSWORD_SIMPLE);
        }
        final String ipAddress = IpUtil.getRealIPAddress(request);
        // 验证邮箱验证码是否正确
        final ResponseResult<?> smsResult = this.checkCodeService.checkEmailCode(
                -1L, form.getEmail(), BehaviorTheme.USERS1_REGISTER_EMAIL_1,
                form.getVerificationCode(), 30, 1, ipAddress);
        if (smsResult.getCode() > 0) {
            return smsResult;
        }
        final Integer brokerId = this.getBrokerId(request);
        // 注册用户
        final long userId = this.userInfoService.register(
                form.getEmail(),
                form.getPassword(),
                0,
                ipAddress,
                RegFromUtils.getRegFrom(request),
                form.getUtm_source(),
                brokerId
        );

        if (userId <= 0) {
            log.error("email register failure! user id:{}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_SAVE_FAILED);
        }

        //激活邮箱
        final UserDetailInfo userDetailInfo = this.userInfoService.getUserDetailInfo(userId);
        if (userDetailInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_SAVE_FAILED);
        }

        /**
         * 生成邀请记录
         */
        this.userInviteRecordService.inviteRegister(userId, form.getInviteCode(), form.getActivityCode(), brokerId);
        try {
            final Map<String, Object> params = new HashMap<>(16);
            params.put("antiphishing", AppEnvConsts.DOMAIN);
            // 发送注册成功邮件
            this.userNoticeEventService.sendEmail(
                    LocaleUtils.getLocale(request),
                    BehaviorTheme.USERS1_REGISTER_EMAIL_1,
                    UserNoticeRecordConsts.BUSINESS_NOTIFICATION,
                    form.getEmail(),
                    MessageTemplateConsts.MAIL_USERS_REGISTER_SUCCESS,
                    userId, params, this.getBrokerId(request));
        } catch (final Exception e) {
            log.error("send register sms notice error, data={}, e={}", form, e);
        }

        final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        this.operatorFacadeService.addSecureAndLoginEvent(request, userDetailInfo, jwtUserDetails, BehaviorNameEnum.REGISTER_EMAIL);

        // 颁发令牌
        return ResultUtils.success(AccessTokenResVO.builder()
                .accessToken(JwtTokenUtils.generateTokenAndCreateSession(jwtUserDetails, request))
                .refreshToken("")
                .scopes(Lists.newArrayList())
                .build());
    }
}
