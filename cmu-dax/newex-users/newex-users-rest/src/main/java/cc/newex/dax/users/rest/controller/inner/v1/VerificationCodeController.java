package cc.newex.dax.users.rest.controller.inner.v1;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.UserDetailConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserKycInfo;
import cc.newex.dax.users.domain.UserKycLevel;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum;
import cc.newex.dax.users.domain.behavior.model.UserBehaviorResult;
import cc.newex.dax.users.dto.security.WithdrawCheckCodeDTO;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.service.behavior.UserBehaviorService;
import cc.newex.dax.users.service.kyc.UserKycInfoService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserSecureEventService;
import cc.newex.dax.users.service.verification.CheckCodeService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author liutiejun
 * @date 2018-11-27
 */
@Slf4j
@RestController
@RequestMapping(value = "/inner/v1/users/verification")
public class VerificationCodeController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserKycInfoService userKycInfoService;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private CheckCodeService checkCodeService;

    @Autowired
    private UserBehaviorService behaviorService;

    @Autowired
    private UserSecureEventService userSecureEventService;

    /**
     * 提币时验证码校验
     *
     * @param verificationCode
     * @param behavior
     * @param userId
     * @return
     */
    @PostMapping("/single-code/{userId}")
    public ResponseResult checkVerificationCode(@RequestParam("verificationCode") final String verificationCode,
                                                @RequestParam("behavior") final Integer behavior,
                                                @PathVariable("userId") final long userId) {
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("UsersInnerController checkVerificationCode userInfo is not exits,userId={},verificationCode={}", userId, verificationCode);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        final UserBehaviorResult userBehaviorResult = this.behaviorService.getUserCheckRuleBehavior(
                BehaviorNameEnum.WITHDRAW_ASSET.getName(), userInfo.getId());

        if (CollectionUtils.isEmpty(userBehaviorResult.getCheckItems())) {
            log.error("UsersInnerController checkVerificationCode getCheckItems is empty,userId={},verificationCode={}", userId, verificationCode);
            return ResultUtils.failure(BizErrorCodeEnum.NOT_FOUND_BEHAVIOR_NAME);
        }
        final BehaviorTheme behaviorTheme = BehaviorTheme.getBehavior(behavior);
        final ResponseResult responseResult = this.checkCodeService.checkCodeByBehavior(userId, behaviorTheme, verificationCode);
        if (responseResult != null && responseResult.getCode() > 0) {
            log.error("UsersInnerController checkVerificationCode check error! userid={},verificationCode=()", userId, verificationCode);
            return responseResult;
        }
        return ResultUtils.success();
    }

    /**
     * 提币时验证码校验
     *
     * @param verificationCode
     * @param userId
     * @return
     */
    @PostMapping("/asset/withdraw/{userId}")
    public ResponseResult checkCode(@RequestParam("verificationCode") final String verificationCode, @PathVariable("userId") final long userId) {
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("UsersInnerController checkCode userInfo is not exits,userId={},verificationCode={}", userId, verificationCode);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        final UserBehaviorResult userBehaviorResult = this.behaviorService.getUserCheckRuleBehavior(
                BehaviorNameEnum.WITHDRAW_ASSET.getName(), userInfo.getId());

        if (CollectionUtils.isEmpty(userBehaviorResult.getCheckItems())) {
            log.error("UsersInnerController checkCode getCheckItems is empty,userId={},verificationCode={}", userId, verificationCode);
            return ResultUtils.failure(BizErrorCodeEnum.NOT_FOUND_BEHAVIOR_NAME);
        }

        final ResponseResult responseResult = this.checkCodeService.checkCodeByBehavior(userId, BehaviorTheme.USERS8_COMMON_WITHDRAW, verificationCode);
        if (responseResult != null && responseResult.getCode() > 0) {
            log.error("UsersInnerController checkCodeByBehavior check error! userid={},verificationCode=()", userId, verificationCode);
            return responseResult;
        }
        return ResultUtils.success();
    }

    @GetMapping("/asset/get-card-number/{userId}")
    public ResponseResult<String> getCardNumber(@PathVariable("userId") final long userId) {
        /**
         * 获取证件号码
         */
        final UserKycInfo userKycInfo = this.userKycInfoService.getByUserId(userId);
        if (ObjectUtils.isEmpty(userKycInfo)) {
            log.error("UsersInnerController getCardNumber kyc is not exist,userId={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_LIMIT_KYC1_ERROR);
        }
        //默认用空字符串
        String shortCardNumber = "";
        final String cardNumber = userKycInfo.getCardNumber();
        if (cardNumber != null && cardNumber.length() > 6) {
            shortCardNumber = cardNumber.substring(0, cardNumber.length() - 6);
        }
        return ResultUtils.success(shortCardNumber);
    }

    /**
     * 提现验证码、兑换验证码、发红包验证码的校验都用这个方法
     *
     * @param form
     * @param userId
     * @return
     */
    @PostMapping("/asset/withdraw-limit/{userId}")
    public ResponseResult withdrawLimit(@RequestBody @Valid final WithdrawCheckCodeDTO form, @PathVariable("userId") final long userId) {
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            log.error("checkCode userInfo is not exits, userId={}, form={}", userId, JSON.toJSONString(form));

            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final UserKycInfo userKycInfo = this.userKycInfoService.getByUserId(userId);
        if (userKycInfo == null) {
            log.error("kyc is not exist, userId={}, form={}", userId, JSON.toJSONString(form));

            return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_LIMIT_KYC1_ERROR);
        }

        // 验证身份证号码
        if (StringUtils.isNotBlank(form.getCardNumber())) {
            if (!StringUtils.equalsIgnoreCase(userKycInfo.getCardNumber(), form.getCardNumber())) {
                log.error("checkCardNumber error, userId={}, form={}", userId, JSON.toJSONString(form));

                return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_CHECK_CARDNUMBER_ERROR);
            }
        }

        final UserSettings userSettings = this.userSettingsService.getById(userId);

        // 判断是否绑定邮箱、短信、Google验证码
        if (userSettings.getEmailAuthFlag() != UserDetailConsts.ENABLE_EMAIL_AUTH) {
            log.error("checkCode error, userId={}, form={}", userId, JSON.toJSONString(form));

            return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_LIMIT_EMAIL_ERROR);
        }

        if (userSettings.getMobileAuthFlag() != UserDetailConsts.ENABLE_PHONE_AUTH && userSettings.getGoogleAuthFlag() != UserDetailConsts.ENABLE_GOOGLE_AUTH) {
            log.error("checkCode error, userId={}, form={}", userId, JSON.toJSONString(form));

            return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_LIMIT_MOBILE_GOOGLE_ERROR);
        }

        // 判断是完成基本信息认证
        final UserKycLevel userKycLevel = this.userKycInfoService.getMaxKycLevelByUserId(userId);
        if (userKycLevel == null) {
            log.error("checkCode error, userId={}, form={}", userId, JSON.toJSONString(form));

            return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_LIMIT_KYC1_ERROR);
        }

        if (userKycLevel.getLevel() == null || userKycLevel.getLevel() < 1) {
            log.error("checkCode error,userId={},form={}", userId, JSON.toJSONString(form));

            return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_LIMIT_KYC1_ERROR);
        }

        // 判断是否24小时内修改过安全设置
        if (this.userSecureEventService.withdraw24HoursLimit(userId)) {
            log.error("checkCode error,userId={},form={}", userId, JSON.toJSONString(form));

            return ResultUtils.failure(BizErrorCodeEnum.WITHDRAW_LIMIT_TODAY_ERROR);
        }

        final UserBehaviorResult userBehaviorResult = this.behaviorService.getUserCheckRuleBehavior(
                BehaviorNameEnum.WITHDRAW_ASSET.getName(), userInfo.getId());
        if (CollectionUtils.isEmpty(userBehaviorResult.getCheckItems())) {
            log.error("UsersInnerController checkCode error,userId={},form={}", userId, JSON.toJSONString(form));

            return ResultUtils.failure(BizErrorCodeEnum.NOT_FOUND_BEHAVIOR_NAME);
        }

        BehaviorTheme behaviorTheme = BehaviorTheme.USERS8_COMMON_WITHDRAW;

        final Integer behavior = form.getBehavior();
        if (behavior > 0) {
            // 兑换验证码、发红包验证码的校验也用这个方法
            behaviorTheme = BehaviorTheme.getBehavior(behavior);
        }

        // 校验验证码是否正确
        final ResponseResult responseResult = this.checkCodeService.checkWithdrawCode(userId, behaviorTheme, form);
        if (responseResult != null && responseResult.getCode() > 0) {
            log.error("UsersInnerController checkCode error,userId={},form={}", userId, JSON.toJSONString(form));
            return responseResult;
        }

        return ResultUtils.success();
    }

}
