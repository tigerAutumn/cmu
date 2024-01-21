package cc.newex.dax.users.rest.controller.outer.v1.common.security;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.exception.UsersBizException;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.common.util.MobileUtils;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.rest.model.VerifyCodeReqVO;
import cc.newex.dax.users.rest.model.VoiceVerifyCodeReqVO;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import com.alibaba.fastjson.JSON;
import com.google.common.collect.Maps;
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
import java.util.Map;

@Slf4j
@RestController
@RequestMapping(value = "/v1/users/security/messages")
public class SecurityMessageController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserNoticeEventService userNoticeRecordService;

    /**
     * 邮件发送验证码
     *
     * @param form
     * @param request
     * @return
     */
    @RetryLimit(type = RetryLimitTypeEnum.SECURITY_VERIFY_CODE)
    @PostMapping(value = "/verification-code/email")
    public ResponseResult sendEmailCode(@RequestBody @Valid final VerifyCodeReqVO form, final HttpServletRequest request) {
        final Long userId = HttpSessionUtils.getUserId(request);
        if (userId == null || userId <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS);
        }

        log.info("sendEmailCode userId={},form={}", userId, JSON.toJSONString(form));

        // 用户不存在
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("SecurityMessageController sendEmailCode, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        // 非邮箱绑定/修改行为，使用用户邮箱
        if (form.getBehavior() != BehaviorTheme.USERS4_EMAIL_BIND_1.getBehavior()
                && form.getBehavior() != BehaviorTheme.USERS4_EMAIL_UPDATE_3.getBehavior()) {
            form.setEmail(userInfo.getEmail());
        }

        // 非邮箱绑定/修改行为，且邮箱不等于用户邮箱，操作不支持
        if (StringUtil.notEquals(form.getEmail(), userInfo.getEmail())
                && form.getBehavior() != BehaviorTheme.USERS4_EMAIL_BIND_1.getBehavior()
                && form.getBehavior() != BehaviorTheme.USERS4_EMAIL_UPDATE_3.getBehavior()) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS);
        }

        final Map<String, Object> params = Maps.newHashMap();

        this.updateParams(params, form, userInfo, NoticeSendLogConsts.CHANNEL_EMAIL);

        log.info("sendEmailCode params: {}", params);

        final BehaviorTheme behaviorTheme = BehaviorTheme.getBehavior(form.getBehavior());

        try {
            final boolean result = this.userNoticeRecordService.sendEmail(LocaleUtils.getLocale(request),
                    behaviorTheme, NoticeSendLogConsts.BUSINESS_CODE, form.getEmail(), behaviorTheme.getEmailCode(),
                    userId, params, this.getBrokerId(request));

            return result ? ResultUtils.success() : ResultUtils.failure(BizErrorCodeEnum.EMAIL_CODE_SEND_FAIL);
        } catch (final Exception e) {
            log.error("SecurityMessageController sendEmailCode error.", e);
            return ResultUtils.failure(BizErrorCodeEnum.EMAIL_CODE_SEND_FAIL);
        }

    }

    /**
     * 处理验证码参数
     *
     * @param params
     * @param form
     * @param userInfo
     * @param channel
     * @return
     */
    private void updateParams(final Map<String, Object> params, final VerifyCodeReqVO form, final UserInfo userInfo, final int channel) {
        final Integer behavior = form.getBehavior();

        if (StringUtils.isBlank(form.getToAddress())) {
            log.error("sendEmailCode error userId={},form={}", userInfo.getId(), JSON.toJSONString(form));

            if (behavior == BehaviorTheme.USERS8_COMMON_WITHDRAW.getBehavior()) {
                throw new UsersBizException(BizErrorCodeEnum.WITHDRAW_LIMIT_TOADDRESS_EMPTY_ERROR);
            }

            if (behavior == BehaviorTheme.USERS8_ASSET_EXCHANGE_CODE.getBehavior()) {
                throw new UsersBizException(BizErrorCodeEnum.EXCHANGE_TOADDRESS_EMPTY_ERROR);
            }

        }

        if (StringUtils.isNotBlank(form.getCoin())) {
            params.put("coin", form.getCoin());
        }

        if (StringUtils.isNotBlank(form.getAmount())) {
            params.put("amount", form.getAmount());
        }

        if (StringUtils.isNotBlank(form.getToAddress())) {
            params.put("toAddress", form.getToAddress());
        }

        if (channel == NoticeSendLogConsts.CHANNEL_EMAIL) {
            params.put("antiphishing", StringUtil.getStarEmail(userInfo.getEmail()));
        }

    }

    /**
     * 手机发送验证码
     *
     * @param form
     * @param request
     * @return
     */
    @RetryLimit(type = RetryLimitTypeEnum.SECURITY_VERIFY_CODE)
    @PostMapping(value = "/verification-code/mobile")
    public ResponseResult sendMobileCode(@RequestBody @Valid final VerifyCodeReqVO form, final HttpServletRequest request) {
        final Long userId = HttpSessionUtils.getUserId(request);
        if (userId == null || userId <= 0) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS);
        }

        log.info("sendMobileCode userId={},form={}", userId, JSON.toJSONString(form));

        // 用户不存在
        final UserInfo uBean = this.userInfoService.getUserInfo(userId);
        if (uBean == null) {
            log.error("SecurityMessageController sendMobileCode, user not login! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        //防止攻击者恶意群发短信
        form.setMobile(StringUtils.left(form.getMobile(), 16));
        // 未传参数，默认使用用户已经绑定的手机
        if (StringUtils.isBlank(form.getMobile()) && MobileUtils.checkPhoneNumber(uBean.getMobile(), uBean.getAreaCode() + "")) {
            form.setMobile(uBean.getMobile());
            form.setAreaCode(uBean.getAreaCode());
        }

        // 特殊情况:修改手机号，默认使用用户提交的手机号
        if (form.getBehavior() != BehaviorTheme.USERS3_PHONE_MODIFY_2.getBehavior()
                && MobileUtils.checkPhoneNumber(uBean.getMobile(), uBean.getAreaCode() + "")) {
            form.setMobile(uBean.getMobile());
            form.setAreaCode(uBean.getAreaCode());
        }

        // 用户未绑定手机，且行为不是手机绑定的行为，不支持
        if (StringUtils.isEmpty(form.getMobile())) {
            log.error("SecurityMessageController sendMobileCode, user notbind phone! userid={}, param={}", userId,
                    JSON.toJSONString(form));
            return ResultUtils.failure(BizErrorCodeEnum.PHONE_EMPTY);
        }

        final Map params = Maps.newHashMap();

        this.updateParams(params, form, uBean, NoticeSendLogConsts.CHANNEL_PHONE);

        log.info("sendMobileCode params: {}", params);

        final BehaviorTheme behaviorTheme = BehaviorTheme.getBehavior(form.getBehavior());

        try {
            // 发送短信
            final boolean result = this.userNoticeRecordService.sendSMS(LocaleUtils.getLocale(request),
                    behaviorTheme, NoticeSendLogConsts.BUSINESS_CODE, form.getMobile(), form.getAreaCode(),
                    behaviorTheme.getMobileCode(), userId, params, this.getBrokerId(request));

            return result ? ResultUtils.success() : ResultUtils.failure(BizErrorCodeEnum.SMS_CODE_SEND_FAIL);
        } catch (final Exception e) {
            log.error("SecurityMessageController sendMobileCode error.", e);
            return ResultUtils.failure(BizErrorCodeEnum.SMS_CODE_SEND_FAIL);
        }

    }

    /**
     * 发送语音验证码
     *
     * @param request
     * @param form
     * @return
     */
    @GetMapping(value = "/verification-code/voice")
    public ResponseResult sendVoiceMsg(final VoiceVerifyCodeReqVO form, final HttpServletRequest request) {
        return ResultUtils.success();
    }
}