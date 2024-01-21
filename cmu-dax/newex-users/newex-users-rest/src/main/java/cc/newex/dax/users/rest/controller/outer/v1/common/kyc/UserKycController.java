package cc.newex.dax.users.rest.controller.outer.v1.common.kyc;

import cc.newex.commons.lang.util.MD5Util;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.support.i18n.LocaleUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.UserKycConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.common.util.WebUtils;
import cc.newex.dax.users.domain.DictCountry;
import cc.newex.dax.users.domain.UserKycInfo;
import cc.newex.dax.users.domain.UserKycLevel;
import cc.newex.dax.users.domain.faceid.FaceToken;
import cc.newex.dax.users.dto.kyc.KycFirstForeignDTO;
import cc.newex.dax.users.dto.kyc.KycSecondDTO;
import cc.newex.dax.users.dto.kyc.KycSecondForeignDTO;
import cc.newex.dax.users.dto.kyc.KycUploadReqDTO;
import cc.newex.dax.users.rest.model.DictLimitCountryVO;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.kyc.UserKycInfoService;
import cc.newex.dax.users.service.membership.DictCountryService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户kyc认证控制器类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/kyc")
public class UserKycController {

    @Autowired
    private UserKycInfoService userKycInfoService;

    @Autowired
    private AppCacheService appCacheService;

    @Autowired
    private DictCountryService dictCountryService;

    /**
     * @param file
     * @description kyc上传图片
     * @date 2018/5/4 下午4:09
     */
    @PostMapping("/upload")
    public ResponseResult uploadFile(@RequestParam("file") final MultipartFile file, final String type,
                                     final Integer country, final HttpServletRequest request) {

        if (type == null || country == null) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        log.info("upload file, type: {}, country: {}, userId: {}", type, country, userId);

        /**
         * 控制图片上传不大于2M
         */
        if (file.getSize() > 2097152) {
            log.error("上传照片大于2M!");
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_KYC_PAYLOAD_TOO_LARGE);
        }

        if (ObjectUtils.isEmpty(type)) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        try {
            final ResponseResult responseResult = this.userKycInfoService.uploadFile(file, type, country, userId);

            return responseResult;
        } catch (final Exception e) {
            log.error("UserKycController uploadFile fail", e);
        }

        return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
    }

    /**
     * @param form
     * @param request
     * @description 保存kyc一级认证信息
     * @date 2018/5/4 下午6:58
     */
    @PostMapping("/kyc-first")
    public ResponseResult kycFirst(@RequestBody final KycUploadReqDTO form, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (StringUtils.isAnyEmpty(form.getCardNumber(), form.getName(), form.getGender())) {
            log.error("请上传正面照信息!userId={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_FACEID_FRONT_FAILED);
        }
        if (StringUtils.isEmpty(form.getValidDate())) {
            log.error("请上传背面照信息!userId={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_FACEID_BACK_FAILED);
        }
        try {
            form.setUserId(userId);
            return this.userKycInfoService.saveKycFirstInfo(form);
        } catch (final Exception e) {
            log.error("UserKycController kycFirst fail:{}", e);
        }
        return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC1_SAVE_FAILED);
    }

    /**
     * @param request
     * @description 国外用户kyc1认证提交
     * @date 2018/5/8 下午2:54
     */
    @PostMapping("/foreign/kyc-first")
    public ResponseResult foreignKycFirst(@RequestBody final KycFirstForeignDTO form, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        try {
            form.setUserId(userId);
            return this.userKycInfoService.saveForeignKycFirstInfo(form);
        } catch (final Exception e) {
            log.error("UserKycController kycFirst fail:{}", e);
        }
        return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC1_SAVE_FAILED);
    }

    /**
     * @param request
     * @description 获取当前用户的kyc1认证的用户名和身份证号码
     * @date 2018/5/4 下午6:57
     */
    @GetMapping("/kyc-first")
    public ResponseResult getKycFirst(final HttpServletRequest request) {
        final Long userId = HttpSessionUtils.getUserId(request);
        if (userId == null || userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserKycInfo userKycInfo = this.userKycInfoService.getByUserId(userId);
        final UserKycLevel userKycLevel = this.userKycInfoService.getUserKycLevelByUserId(userId, UserKycConsts.USER_KYC_LEVEL_1);
        try {
            if (ObjectUtils.isEmpty(userKycInfo) || ObjectUtils.isEmpty(userKycLevel)) {
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_INFO_NOT_FOUND);
            }

            final KycSecondDTO kycSecondDTO = KycSecondDTO.builder()
                    .userId(userId)
                    .name(userKycInfo.getFirstName() + userKycInfo.getMiddleName() + userKycInfo.getLastName())
                    .status(userKycLevel.getStatus())
                    .cardNumber(userKycInfo.getCardNumber())
                    .country(userKycInfo.getCountry())
                    .build();

            final UserKycLevel level2 = this.userKycInfoService.getUserKycLevelByUserId(userId, UserKycConsts.USER_KYC_LEVEL_2);
            if (!ObjectUtils.isEmpty(level2) && level2.getStatus() == UserKycConsts.USER_KYC_STATUS_1) {
                log.error("getKycFirst 返回kyc认证信息,userId={}", userId);

                return ResultUtils.success(kycSecondDTO);
            }

            // 获取国内用户的kyc1认证信息
            if (userKycInfo.getCountry() == 156) {
                try {
                    final FaceToken faceToken = this.userKycInfoService.getFaceToken(userId);
                    if (ObjectUtils.isEmpty(faceToken)) {
                        log.error("getKycFirstxxx1 error, userId: {}", userId);

                        return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_GET_KYC_TOKEN_TIMEOUT);
                    }

                    kycSecondDTO.setKycToken(faceToken.getToken());
                    kycSecondDTO.setBizNo(faceToken.getBizNo());

                    this.appCacheService.setChinaUserSecondKyc(userId, kycSecondDTO.getKycToken());

                    return ResultUtils.success(kycSecondDTO);
                } catch (final Exception e) {
                    log.error("getKycFirstxxx2 error, userId: {}, msg: {}", userId, e.getMessage(), e);

                    return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_GET_KYC_TOKEN_TIMEOUT);
                }
            } else {
                // 获取国际用户的kyc1认证信息
                final String kycToken = MD5Util.getMD5String(userId + userKycInfo.getCardNumber() + System.currentTimeMillis());
                this.appCacheService.setForeignUserSecondKyc(userId, kycToken);

                final KycSecondForeignDTO kycSecondForeignDTO = KycSecondForeignDTO.builder()
                        .country(userKycInfo.getCountry())
                        .firstName(userKycInfo.getFirstName())
                        .middleName(userKycInfo.getMiddleName())
                        .lastName(userKycInfo.getLastName())
                        .kycToken(kycToken)
                        .build();

                return ResultUtils.success(kycSecondForeignDTO);
            }
        } catch (final Exception e) {
            log.error("UserKycControler getKycFirst fail userId=" + userId + ", error=" + e.getMessage(), e);
        }

        return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_INFO_NOT_FOUND);
    }

    /**
     * 获取kyc2的认证状态
     *
     * @param request
     * @return
     */
    @GetMapping("/check-status")
    public ResponseResult checkKycStatus(final HttpServletRequest request) {
        final Long userId = HttpSessionUtils.getUserId(request);
        if (userId == null || userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserKycLevel userKycLevel = this.userKycInfoService.getUserKycLevelByUserId(userId, UserKycConsts.USER_KYC_LEVEL_2);

        if (ObjectUtils.isEmpty(userKycLevel)) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_INFO_NOT_FOUND);
        }

        final Integer status = userKycLevel.getStatus();

        if (status == null || status != UserKycConsts.USER_KYC_STATUS_1) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_KYC2_NOPASS_FAILED);
        }

        return ResultUtils.success();
    }

    /**
     * @param kycSecondForeignDTO
     * @param request
     * @description 保存国外二级认证结果
     * @date 2018/5/4 下午8:30
     */
    @PostMapping("/foreign/kyc-second")
    public ResponseResult kycForeignSecond(@RequestBody @Valid final KycSecondForeignDTO kycSecondForeignDTO, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        final String kycSecondToken = this.appCacheService.getForeignUserSecondKyc(userId);
        if (StringUtils.isEmpty(kycSecondToken)) {
            log.error("kycForeignSecond kycToken缓存时间过期,kycSecondToken={},userId={}", kycSecondToken, userId);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_TOKEN_TIMEOUT_FAILED);
        }
        /**
         * 校验用户的kyc1获取的token与完成kyc2认证后传过来的token是否一致
         */
        if (StringUtil.notEquals(kycSecondForeignDTO.getKycToken(), kycSecondToken)) {
            log.error("kycForeignSecond 用户kycToken缓存不一致,kycSecondToken={},formKycToken={},userId={}", kycSecondToken, kycSecondForeignDTO.getKycToken(), userId);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_TOKEN_TIMEOUT_FAILED);
        }

        final UserKycLevel userKycLevel = this.userKycInfoService.getUserKycLevelByUserId(userId, UserKycConsts.USER_KYC_LEVEL_1);

        if (userKycLevel.getStatus() != UserKycConsts.USER_KYC_STATUS_1) {
            log.error("kycForeignSecond kyc等级1没有认证完成，不能做认证等级2,kycToken={},kycSecond={},userId={}", kycSecondForeignDTO.getKycToken(), kycSecondToken, userId);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_KYC1_NOPASS_FAILED);
        }

        /**
         * 完成kyc2后不能再次认证
         */
        final UserKycLevel userKycLevel2 = this.userKycInfoService.getUserKycLevelByUserId(userId, UserKycConsts.USER_KYC_LEVEL_2);

        if (userKycLevel2 != null && userKycLevel2.getStatus() == UserKycConsts.USER_KYC_STATUS_1) {
            log.error("kycForeignSecond kyc等级2认证完成，不能做认证等级2,kycToken={},kycSecond={},userId={}", kycSecondForeignDTO.getKycToken(), kycSecondToken, userId);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_KYC2_PASS_FAILED);
        }

        try {
            kycSecondForeignDTO.setUserId(userId);
            final ResponseResult result = this.userKycInfoService.saveSecondForeignLevel(kycSecondForeignDTO);
            return result;
        } catch (final Exception e) {
            log.error("UserKycController kycForeignSecond fail:{}", e);
        }
        return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC2_SAVE_FAILED);
    }

    /**
     * @param request
     * @description app回调接口, 处理kyc2是否认证通过
     * @date 2018/5/8 下午2:54
     */
    @PostMapping("/verify")
    public ResponseResult verify(@RequestBody final String data, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);

        log.info("verify, data: {}, userId: {}", JSON.parse(data), userId);

        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        if (StringUtils.isEmpty(data)) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        try {
            return this.userKycInfoService.verify(userId, data);
        } catch (final Exception e) {
            log.error("UserKycController verify fail:{}", e);
        }

        return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
    }

    /**
     * @param request
     * @description app回调获取kyc2活体认证的状态
     * @date 2018/5/8 下午2:54
     */
    @PostMapping("/v2/verify")
    public ResponseResult verify2(@RequestBody final String deltaStr, final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);

        final String userAgent = WebUtils.getUserAgent(request);

        log.info("verify2, userId: {}, User-Agent: {}", userId, WebUtils.getUserAgent(request));

        if (userId <= 0) {
            log.error("verify2 user not login!");

            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final com.alibaba.fastjson.JSONObject deltaJson = JSON.parseObject(deltaStr);
        if (deltaJson == null) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        final String delta = deltaJson.getString("delta");
        if (StringUtils.isEmpty(delta)) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        try {
            return this.userKycInfoService.v2_verify(userId, delta, userAgent);
        } catch (final Exception e) {
            log.error("UserKycController verify2 fail: " + e.getMessage(), e);
        }

        return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
    }

    @GetMapping("/limit/country")
    public ResponseResult checkKycCountry(final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("user not login!");
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserKycInfo userKycInfo = this.userKycInfoService.getByUserId(userId);
        if (userKycInfo == null) {
            return ResultUtils.success();
        }

        final String locale = LocaleUtils.getLocale(request);

        final List<DictCountry> list = this.dictCountryService.limitCountries(locale);

        if (CollectionUtils.isNotEmpty(list)) {
            final DictCountry dictCountry = list.stream().filter(x -> x.getCountryCode().equals(userKycInfo.getCountry())).findFirst().orElse(null);

            if (dictCountry != null) {
                log.info("get user kyc limit country:" + JSON.toJSONString(dictCountry));

                return ResultUtils.success(ObjectCopyUtils.map(dictCountry, DictLimitCountryVO.class));
            }
        }

        return ResultUtils.success();
    }

}