package cc.newex.dax.users.rest.controller.outer.v1.common.support;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.UserKycConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.SHA1Util;
import cc.newex.dax.users.domain.UserKycLevel;
import cc.newex.dax.users.domain.UserKycToken;
import cc.newex.dax.users.dto.kyc.FaceIdReqDTO;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.kyc.UserKycInfoService;
import cc.newex.dax.users.service.kyc.UserKycTokenService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Validated
@RestController
@RequestMapping("/v1/users/support/thirdparty")
public class ThirdPartyController {

    @Autowired
    private UserKycInfoService userKycInfoService;
    @Autowired
    private UserKycTokenService userKycTokenService;
    @Autowired
    private AppCacheService appCacheService;

    @Value("${newex.faceid.api_secret}")
    private String apiSecret;

    /**
     * @param request
     * @description 保存二级认证结果
     * @date 2018/5/4 下午8:30
     */
    @PostMapping("/kyc/get-result")
    public ResponseResult getKycResult(@RequestParam("data") final String data, @RequestParam("sign") final String sign, final HttpServletRequest request) {
        log.info("get key result, data: {}, sign: {}", data, sign);

        if (StringUtils.isAnyEmpty(sign, data)) {
            log.error("getKycResult sign={},data={},ip={}", sign, data, IpUtil.getRealIPAddress(request));
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }

        final String dataSign = SHA1Util.getSha1(this.apiSecret + data);
        if (!StringUtils.equalsIgnoreCase(dataSign, sign)) {
            log.error("getKycResult 签名不一致,验证失败,sign={},dataSign={}", sign, dataSign);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_FACEID_SIGN);
        }

        final FaceIdReqDTO faceIdReqDTO = FaceIdReqDTO.builder().data(data).sign(sign).build();
        /**
         * 验证 singn 签名与data 加密后数据是否一致,防止被串改
         */
        final JSONObject notifyResult = JSON.parseObject(faceIdReqDTO.getData());
        final JSONObject bizInfo = JSON.parseObject(notifyResult.getString("biz_info"));
        final String bizId = bizInfo.getString("biz_id");
        faceIdReqDTO.setBizId(bizId);
        final UserKycToken userKycToken = this.userKycTokenService.getByBizId(bizId);
        if (ObjectUtils.isEmpty(userKycToken)) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_TOKEN_TIMEOUT_FAILED);
        }
        final Long userId = userKycToken.getUserId();
        final String kycSecondToken = this.appCacheService.getChinaUserSecondKyc(userId);
        if (StringUtils.isEmpty(kycSecondToken)) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_TOKEN_TIMEOUT_FAILED);
        }

        /**
         * 完成kyc2后不能再次认证
         */
        final UserKycLevel userKycLevel2 = this.userKycInfoService.getUserKycLevelByUserId(userId, UserKycConsts.USER_KYC_LEVEL_2);
        if (userKycLevel2 != null && userKycLevel2.getStatus() == UserKycConsts.USER_KYC_STATUS_1) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_KYC2_PASS_FAILED);
        }

        /**
         * kyc1认证没有通过不允许进行二级认证
         */
        final UserKycLevel userKycLevel = this.userKycInfoService.getUserKycLevelByUserId(userId, UserKycConsts.USER_KYC_LEVEL_1);
        if (userKycLevel.getStatus() != UserKycConsts.USER_KYC_STATUS_1) {
            log.error("getKycResult kyc等级1没有认证完成，不能做kyc2,kycSecond={},userId={}", kycSecondToken, userId);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_KYC1_NOPASS_FAILED);
        }

        try {
            faceIdReqDTO.setUserId(userId);
            final boolean result = this.userKycInfoService.saveSecondLevel(faceIdReqDTO);
            if (result) {
                /**
                 * face++的token只能使用一次
                 */
                this.appCacheService.deleteChinaUserSecondKyc(userId);
                log.info("getKycResult save success,userId={}", userId);
                return ResultUtils.success();
            }
        } catch (final Exception e) {
            log.error("getKycResult userId={}, fail:{}", userId, e);
        }

        return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC2_SAVE_FAILED);
    }

}
