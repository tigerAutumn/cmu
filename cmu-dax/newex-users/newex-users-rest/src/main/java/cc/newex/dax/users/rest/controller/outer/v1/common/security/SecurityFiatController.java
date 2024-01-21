package cc.newex.dax.users.rest.controller.outer.v1.common.security;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.UserFiatConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.exception.UsersKycException;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.domain.UserFiatSetting;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.dto.security.UserFiatSettingDTO;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.model.SwitchSettingVO;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserFiatSettingService;
import cc.newex.dax.users.service.verification.CheckCodeService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;

/**
 * (类描述：用户法币交易设置)
 *
 * @create 2018/5/14 上午11:32
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/security/fiat")
public class SecurityFiatController {

    @Autowired
    private UserFiatSettingService userFiatSettingService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private CheckCodeService checkCodeService;

    @GetMapping("")
    public ResponseResult list(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("UserFiatSettingController list user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        final Map result = this.userFiatSettingService.list(userId);
        return ResultUtils.success(result);
    }

    /**
     * @param form
     * @param request
     * @description 保存法币设置银行卡信息
     * @date 2018/5/18 上午11:37
     */
    @RetryLimit(type = RetryLimitTypeEnum.FIAT)
    @PostMapping("/bank")
    public ResponseResult createBank(@RequestBody @Valid final UserFiatSettingDTO form, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("createBank user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (StringUtils.isEmpty(form.getVerificationCode()) || StringUtils.isEmpty(form.getBankCard())) {
            log.error("createBank form:{}", form);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("createBank user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        /**
         * 验证
         */
        final ResponseResult responseResult = this.checkCodeService.checkCodeByBehavior(userId, BehaviorTheme.USERS9_FIAT_BANK_SETTING, form.getVerificationCode());
        if (ObjectUtils.isEmpty(responseResult) || responseResult.getCode() > 0) {
            log.error("UserFiatSettingController ,createBank.userId={},behavior={},verificationCode={}", userId,
                    BehaviorTheme.USERS9_FIAT_BANK_SETTING.getBehavior(), form.getVerificationCode());
            return responseResult;
        }
        final UserFiatSetting userFiatSetting = this.userFiatSettingService.getByUserId(userId, UserFiatConsts.USER_FIAT_PAYTYPE_1);
        if (!ObjectUtils.isEmpty(userFiatSetting)) {
            log.error("UserFiatSettingController createBank  userFiatSetting is exist: {}", userFiatSetting);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_EXISTS_FAILED);
        }

        form.setUserId(userId);

        final ResponseResult result = this.userFiatSettingService.savePayment(UserFiatConsts.USER_FIAT_BANKPAY, form);
        return result;
    }

    /**
     * @param form
     * @param id
     * @param request
     * @description 修改银行卡支付方式
     * @date 2018/5/19 下午1:29
     */
    @RetryLimit(type = RetryLimitTypeEnum.FIAT)
    @PutMapping("/bank/{id}")
    public ResponseResult editBank(@RequestBody @Valid final UserFiatSettingDTO form, @PathVariable("id") final Long id, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("editBank user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (StringUtils.isEmpty(form.getVerificationCode()) || StringUtils.isEmpty(form.getBankCard())) {
            log.error("editBank form:{}", form);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("editBank user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        /**
         * 防止登录用户串改别人信息
         */
        if (!id.equals(form.getId())) {
            log.error("editBank 修改银行卡收款信息错误,id:{},formId={}", id, form.getId());
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS);
        }
        /**
         * 验证
         */
        final ResponseResult responseResult = this.checkCodeService.checkCodeByBehavior(userId, BehaviorTheme.USERS9_FIAT_BANK_SETTING, form.getVerificationCode());
        if (ObjectUtils.isEmpty(responseResult) || responseResult.getCode() > 0) {
            log.error("UserFiatSettingController ,editBank.userId={},behavior={},verificationCode={}", userId,
                    BehaviorTheme.USERS9_FIAT_BANK_SETTING.getBehavior(), form.getVerificationCode());
            return responseResult;
        }
        final UserFiatSetting userFiatSetting = this.userFiatSettingService.getByUserId(userId, UserFiatConsts.USER_FIAT_PAYTYPE_1);
        if (ObjectUtils.isEmpty(userFiatSetting)) {
            log.error("UserFiatSettingController editBank  userFiatSetting is exist: {}", userFiatSetting);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_NOT_BIND_BANK_FAILED);
        }

        form.setUserId(userId);

        final ResponseResult result = this.userFiatSettingService.savePayment(UserFiatConsts.USER_FIAT_BANKPAY, form);
        return result;
    }

    /**
     * @param form
     * @description 设置支付宝支付
     * @date 2018/5/14 下午4:52
     */
    @RetryLimit(type = RetryLimitTypeEnum.FIAT)
    @PostMapping("/alipay")
    public ResponseResult createAlipay(@RequestBody @Valid final UserFiatSettingDTO form, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("createAlipay user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (StringUtils.isEmpty(form.getVerificationCode()) || StringUtils.isEmpty(form.getAlipayReceivingImg())) {
            log.error("createAlipay form:{}", form);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("createAlipay user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final ResponseResult responseResult = this.checkCodeService.checkCodeByBehavior(userId, BehaviorTheme.USERS9_FIAT_ALIPAY_SETTING, form.getVerificationCode());
        if (ObjectUtils.isEmpty(responseResult) || responseResult.getCode() > 0) {
            log.error("UserFiatSettingController ,createAlipay userId={},behavior={},verificationCode={}", userId,
                    BehaviorTheme.USERS9_FIAT_ALIPAY_SETTING.getBehavior(), form.getVerificationCode());
            return responseResult;
        }

        form.setUserId(userId);

        final ResponseResult result = this.userFiatSettingService.savePayment(UserFiatConsts.USER_FIAT_ALIPAY, form);

        return result;
    }

    /**
     * @param form
     * @description 更新支付宝设置
     * @date 2018/5/14 下午4:52
     */
    @RetryLimit(type = RetryLimitTypeEnum.FIAT)
    @PutMapping("/alipay/{id}")
    public ResponseResult editAlipay(@RequestBody @Valid final UserFiatSettingDTO form, @PathVariable("id") final Long id, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("editAlipay user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (StringUtils.isEmpty(form.getVerificationCode()) || StringUtils.isEmpty(form.getAlipayReceivingImg())) {
            log.error("editAlipay form:{}", form);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("editAlipay user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        /**
         * 防止登录用户串改别人信息
         */
        if (id == null || !id.equals(form.getId())) {
            log.error("editAlipay 修改支付宝收款信息错误,id:{},formId={}", id, form.getId());
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS);
        }

        final ResponseResult responseResult = this.checkCodeService.checkCodeByBehavior(userId, BehaviorTheme.USERS9_FIAT_ALIPAY_SETTING, form.getVerificationCode());
        if (ObjectUtils.isEmpty(responseResult) || responseResult.getCode() > 0) {
            log.error("editAlipay userId={},behavior={},verificationCode={}", userId,
                    BehaviorTheme.USERS9_FIAT_ALIPAY_SETTING.getBehavior(), form.getVerificationCode());
            return responseResult;
        }
        form.setId(id);
        form.setUserId(userId);

        final ResponseResult result = this.userFiatSettingService.editAlipay(form);

        return result;
    }

    /**
     * @param form
     * @description 设置微信支付
     * @date 2018/5/14 下午4:52
     */
    @RetryLimit(type = RetryLimitTypeEnum.FIAT)
    @PostMapping("/wechat-pay")
    public ResponseResult createWechatPay(@RequestBody @Valid final UserFiatSettingDTO form, final HttpServletRequest request) {

        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("createWechatPay user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (StringUtils.isEmpty(form.getVerificationCode()) || StringUtils.isEmpty(form.getWechatReceivingImg())) {
            log.error("createWechatPay form:{}", form);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("createWechatPay user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final ResponseResult responseResult = this.checkCodeService.checkCodeByBehavior(userId, BehaviorTheme.USERS9_FIAT_WEPAY_SETTING, form.getVerificationCode());
        if (ObjectUtils.isEmpty(responseResult) || responseResult.getCode() > 0) {
            log.error("createWechatPay.userId={},behavior={},verificationCode={}", userId,
                    BehaviorTheme.USERS9_FIAT_WEPAY_SETTING.getBehavior(), form.getVerificationCode());
            return responseResult;
        }

        form.setUserId(userId);

        final ResponseResult result = this.userFiatSettingService.savePayment(UserFiatConsts.USER_FIAT_WECHATPAY, form);

        return result;
    }

    /**
     * @param form
     * @description 修改微信支付
     * @date 2018/5/14 下午4:52
     */
    @RetryLimit(type = RetryLimitTypeEnum.FIAT)
    @PutMapping("/wechat-pay/{id}")
    public ResponseResult editWechatPay(@RequestBody @Valid final UserFiatSettingDTO form, @PathVariable("id") final Long id, final HttpServletRequest request) {

        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("editWechatPay user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (StringUtils.isEmpty(form.getVerificationCode()) || StringUtils.isEmpty(form.getWechatReceivingImg())) {
            log.error("editWechatPay form:{}", form);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("editWechatPay user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        /**
         * 防止登录用户串改别人信息
         */
        if (!id.equals(form.getId())) {
            log.error("editWechatPay 修改支付宝收款信息错误,id:{},formId={}", id, form.getId());
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS);
        }
        /**
         * 验证
         */
        final ResponseResult responseResult = this.checkCodeService.checkCodeByBehavior(userId, BehaviorTheme.USERS9_FIAT_WEPAY_SETTING, form.getVerificationCode());
        if (ObjectUtils.isEmpty(responseResult) || responseResult.getCode() > 0) {
            log.error("editWechatPay.userId={},behavior={},verificationCode={}", userId,
                    BehaviorTheme.USERS9_FIAT_WEPAY_SETTING.getBehavior(), form.getVerificationCode());
            return responseResult;
        }
        final UserFiatSetting userFiatSetting = this.userFiatSettingService.getByUserId(userId, UserFiatConsts.USER_FIAT_PAYTYPE_1);
        if (ObjectUtils.isEmpty(userFiatSetting) || StringUtils.isEmpty(userFiatSetting.getBankCard())) {
            log.error("editWechatPay userFiatSetting is not exist: {}", userFiatSetting);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_SAVEALIPAY_FAILED);
        }

        form.setId(id);
        form.setUserId(userId);

        final ResponseResult result = this.userFiatSettingService.editWechatPay(form);

        return result;
    }

    /**
     * @param file
     * @description kyc上传图片
     * @date 2018/5/4 下午4:09
     */
    @PostMapping("/upload")
    public ResponseResult uploadFile(@RequestParam("file") final MultipartFile file, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("upload file error, user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        // 控制图片上传不大于5M
        if (file.getSize() > 5242880) {
            throw new UsersKycException(BizErrorCodeEnum.ERROR_USER_KYC_PAYLOAD_TOO_LARGE);
        }

        log.info("upload file, userId: {}, file: {} - {}", userId, file.getOriginalFilename(), file.getContentType());

        try {
            return this.userFiatSettingService.uploadFile(file, userId);
        } catch (final Exception e) {
            log.error("upload file error, userId: {}, file: {} - {}", userId, file.getOriginalFilename(), file.getContentType(), e);
        }

        return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
    }

    /**
     * @param vo
     * @param request
     * @description 设置法币交易-银行卡,支付宝,微信支付开关
     * @date 2018/5/15 下午9:18
     */
    @PostMapping("/switch")
    public ResponseResult switchSetting(@RequestBody final SwitchSettingVO vo, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("switchSetting user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (null == vo.getType()) {
            log.error("switchSetting type:{}", vo.getType());
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        final UserInfo userInfo = this.userInfoService.getById(userId);
        if (ObjectUtils.isEmpty(userInfo)) {
            log.error("switchSetting user:{},not exists", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        final Integer status;
        final Boolean result;
        final UserSettings userSettings = this.userSettingsService.getByUserId(userId);
        //不能全部关闭支付方式
        if (!this.userSettingsService.limitOnePay(userSettings, vo.getType())) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_PAYMENT_LIMIT_ONE);
        }
        switch (vo.getType()) {
            case UserFiatConsts.USER_FIAT_ALIPAY:
                status = userSettings.getAlipayAuthFlag() == UserFiatConsts.USER_FIAT_ALIPAY_0 ? UserFiatConsts.USER_FIAT_ALIPAY_1 : UserFiatConsts.USER_FIAT_ALIPAY_0;
                result = this.userSettingsService.enablePayAuthFlag(userId, UserFiatConsts.USER_FIAT_ALIPAY, status);
                break;
            case UserFiatConsts.USER_FIAT_WECHATPAY:
                status = userSettings.getWechatPayAuthFlag() == UserFiatConsts.USER_FIAT_WECHATPAY_0 ? UserFiatConsts.USER_FIAT_WECHATPAY_1 : UserFiatConsts.USER_FIAT_WECHATPAY_0;
                result = this.userSettingsService.enablePayAuthFlag(userId, UserFiatConsts.USER_FIAT_WECHATPAY, status);
                break;
            case UserFiatConsts.USER_FIAT_BANKPAY:
                status = userSettings.getBankPayAuthFlag() == UserFiatConsts.USER_FIAT_BANKPAY_0 ? UserFiatConsts.USER_FIAT_BANKPAY_1 : UserFiatConsts.USER_FIAT_BANKPAY_0;
                result = this.userSettingsService.enablePayAuthFlag(userId, UserFiatConsts.USER_FIAT_BANKPAY, status);
                break;
            default:
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_SAVEALIPAY_FAILED);
        }
        if (BooleanUtils.isFalse(result)) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_SETTING_FAILED);
        }
        return ResultUtils.success();
    }

}
