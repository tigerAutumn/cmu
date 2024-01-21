package cc.newex.dax.users.rest.controller.outer.v1.common.support;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.lang.util.PwdStrengthUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.consts.AppEnvConsts;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.consts.UserNoticeRecordConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.MobileUtils;
import cc.newex.dax.users.criteria.UserActivityConfigExample;
import cc.newex.dax.users.data.UserActivityConfigRepository;
import cc.newex.dax.users.domain.UserActivityConfig;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.common.service.EventFacadeService;
import cc.newex.dax.users.rest.common.util.RegFromUtils;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.rest.model.AccessTokenResVO;
import cc.newex.dax.users.rest.model.ChannelEmailReqVO;
import cc.newex.dax.users.rest.model.ChannelMobileRegReqVO;
import cc.newex.dax.users.rest.model.InviteRegisterVO;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserInviteRecordService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import cc.newex.dax.users.service.verification.CheckCodeService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/users/support/activity")
public class ActivityController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private UserActivityConfigRepository userActivityConfigRepository;
    @Autowired
    private CheckCodeService checkCodeService;
    @Autowired
    private UserNoticeEventService userNoticeEventService;
    @Autowired
    private EventFacadeService operatorFacadeService;
    @Autowired
    private UserInviteRecordService userInviteRecordService;

    /**
     * @description 活动邀请页面
     * @date 2018/5/4 下午8:30
     */
    @GetMapping("/{activityCode}/{inviteCode}")
    public ResponseResult inviteRegister(@PathVariable("activityCode") final String activityCode, @PathVariable("inviteCode") final String inviteCode, final HttpServletRequest request) {
        final Integer brokerId = this.getBrokerId(request);

        final UserInfo userInfo = this.userInfoService.getUserInfoByUid(inviteCode);
        final UserActivityConfigExample example = new UserActivityConfigExample();
        example.createCriteria().andBrokerIdEqualTo(brokerId);
        example.setOrderByClause(" online desc,id desc");
        final List<UserActivityConfig> userActivityConfigList = this.userActivityConfigRepository.selectByExample(example);

        if (CollectionUtils.isEmpty(userActivityConfigList)) {
            log.error("inviteRegister activityCode={}", activityCode);
            return ResultUtils.failure(BizErrorCodeEnum.ACTIVITY_TYPE_INVITE_ERROR);
        }
        final UserActivityConfig userActivityConfig = userActivityConfigList.get(0);

        final InviteRegisterVO registerVO = InviteRegisterVO.builder().currencyCode(userActivityConfig.getCurrencyCode())
                .currencyNum(userActivityConfig.getCurrencyNum()).activityCode(userActivityConfig.getActivityCode())
                .inviteCurrencyId(userActivityConfig.getInviteCurrencyId()).inviteCurrencyCode(userActivityConfig.getInviteCurrencyCode())
                .inviteCurrencyNum(userActivityConfig.getInviteCurrencyNum()).online(userActivityConfig.getOnline()).brokerId(userActivityConfig.getBrokerId()).build();
        if (!ObjectUtils.isEmpty(userInfo)) {
            registerVO.setInviteCode(userInfo.getUid());
            registerVO.setInviteName(StringUtil.getStarMobile(userInfo.getMobile()));
        }

        return ResultUtils.success(registerVO);
    }

    /**
     * @param form
     * @param request
     * @description 渠道手机注册
     * @date 2018/7/19 下午1:21
     */
    @RetryLimit(type = RetryLimitTypeEnum.REGISTER)
    @PostMapping(value = "/mobile")
    public ResponseResult registerByMobile(@RequestBody @Valid final ChannelMobileRegReqVO form, final HttpServletRequest request) {

        // 验证手机号
        final boolean isPhone = MobileUtils.checkPhoneNumber(
                StringUtils.trim(form.getMobile()), String.valueOf(form.getAreaCode())
        );
        if (!isPhone) {
            log.error("mobile number format error! mobile={}", form.getMobile());
            return ResultUtils.failure(BizErrorCodeEnum.PHONE_FORMAT_ERROR);
        }
        /**
         * 密码强度校验
         */
        final Integer level = PwdStrengthUtil.getStrengthLevel(form.getPassword());
        if (level < 2) {
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_PASSWORD_SIMPLE);
        }
        /**
         * 不支持注册的国家
         * 暂不支持你所选择的国家
         */
        final List<Integer> limitCountry = Arrays.asList(850, 963, 249, 880, 591, 593, 996);
        if (limitCountry.contains(form.getAreaCode())) {
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
        final ResponseResult<?> smsResult = this.checkCodeService.checkMobileCode(
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
                    MessageTemplateConsts.SMS_USERS_MOBILE_REGISTER_SUCCESS, userId, Maps.newHashMap(), this.getBrokerId(request));
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

    /**
     * @param form
     * @param request
     * @description 渠道邮箱注册
     * @date 2018/7/19 下午1:21
     */
    @RetryLimit(type = RetryLimitTypeEnum.REGISTER)
    @PostMapping(value = "/email")
    public ResponseResult<?> registerByEmail(@RequestBody @Valid final ChannelEmailReqVO form, final HttpServletRequest request) {
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
