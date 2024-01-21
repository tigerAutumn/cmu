package cc.newex.dax.users.service.security.impl;

import cc.newex.commons.fileupload.FileUploadResponse;
import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.UserFiatConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.criteria.UserFiatSettingExample;
import cc.newex.dax.users.data.UserFiatSettingRepository;
import cc.newex.dax.users.domain.UserFiatSetting;
import cc.newex.dax.users.domain.UserKycInfo;
import cc.newex.dax.users.dto.security.UserFiatSettingDTO;
import cc.newex.dax.users.service.kyc.UserKycInfoService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserFiatSettingService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 用户法币交易设置 服务实现
 *
 * @author newex-team
 * @date 2018-05-14
 */
@Slf4j
@Service
public class UserFiatSettingServiceImpl
        extends AbstractCrudService<UserFiatSettingRepository, UserFiatSetting, UserFiatSettingExample, Long>
        implements UserFiatSettingService {

    @Autowired
    private UserFiatSettingRepository userFiatSettingRepos;

    @Resource(name = "ossFileUploadService")
    private FileUploadService fileUploadService;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private UserKycInfoService userKycInfoService;

    @Override
    protected UserFiatSettingExample getPageExample(final String fieldName, final String keyword) {
        final UserFiatSettingExample example = new UserFiatSettingExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public Map list(final long userId) {
        final List<UserFiatSetting> list = this.getListByUserId(userId);
        if (CollectionUtils.isEmpty(list)) {
            return Collections.EMPTY_MAP;
        }
        final Map map = Maps.newHashMap();
        list.stream().forEach(event -> {
            if (event.getPayType() == UserFiatConsts.USER_FIAT_PAYTYPE_1) {
                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", event.getId());
                jsonObject.put("userName", StringUtil.getStarRealName(event.getUserName()));
                jsonObject.put("bankAddress", event.getBankAddress());
                jsonObject.put("bankCard", StringUtil.getStarString(event.getBankCard(), 5, 13));
                jsonObject.put("bankName", event.getBankName());
                map.put("bank", jsonObject);
            } else if (event.getPayType() == UserFiatConsts.USER_FIAT_PAYTYPE_2) {
                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", event.getId());
                jsonObject.put("preview", this.fileUploadService.getSignedUrl(event.getAlipayReceivingImg()));
                jsonObject.put("alipayReceivingImg", event.getAlipayReceivingImg());
                jsonObject.put("alipayName", event.getAlipayName());
                jsonObject.put("alipayCard", event.getAlipayCard());
                map.put("aliPay", jsonObject);
            } else {
                final JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", event.getId());
                jsonObject.put("preview", this.fileUploadService.getSignedUrl(event.getWechatReceivingImg()));
                jsonObject.put("wechatReceivingImg", event.getWechatReceivingImg());
                jsonObject.put("wechatPayName", event.getWechatPayName());
                jsonObject.put("wechatCard", event.getWechatCard());
                map.put("wechatPay", jsonObject);
            }
        });

        return map;
    }

    @Override
    public UserFiatSetting getByUserId(final Long userId, final int payType) {
        return this.getByUserId(userId, UserFiatConsts.USER_STATUS_0, payType);
    }

    @Override
    public UserFiatSetting getByUserId(final Long userId, final Integer status, final Integer payType) {
        final UserFiatSettingExample example = new UserFiatSettingExample();
        final UserFiatSettingExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if (status != null) {
            criteria.andStatusEqualTo(status);
        }
        if (payType != null) {
            criteria.andPayTypeEqualTo(payType);
        }
        final UserFiatSetting userFiatSetting = this.userFiatSettingRepos.selectOneByExample(example);
        return ObjectUtils.isEmpty(userFiatSetting) ? null : userFiatSetting;
    }

    @Override
    public List<UserFiatSetting> getListByUserId(final Long userId) {
        final UserFiatSettingExample example = new UserFiatSettingExample();
        example.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(UserFiatConsts.USER_STATUS_0);
        final List<UserFiatSetting> resultList = this.userFiatSettingRepos.selectByExample(example);
        return resultList;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult editAlipay(final UserFiatSettingDTO form) {
        final UserFiatSetting userFiatSetting = this.userFiatSettingRepos.selectById(form.getId());
        if (ObjectUtils.isEmpty(userFiatSetting)) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_WECHATPAY_NOT_EXISTS_FAILED);
        }
        userFiatSetting.setAlipayName(form.getAlipayName());
        userFiatSetting.setAlipayCard(form.getAlipayCard());
        userFiatSetting.setAlipayReceivingImg(form.getAlipayReceivingImg());
        userFiatSetting.setPayType(UserFiatConsts.USER_FIAT_PAYTYPE_2);

        this.dao.updateById(userFiatSetting);
        return ResultUtils.success();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult editWechatPay(final UserFiatSettingDTO form) {
        final UserFiatSetting userFiatSetting = this.userFiatSettingRepos.selectById(form.getId());
        if (ObjectUtils.isEmpty(userFiatSetting)) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_WECHATPAY_NOT_EXISTS_FAILED);
        }
        userFiatSetting.setWechatCard(form.getWechatCard());
        userFiatSetting.setWechatPayName(form.getWechatPayName());
        userFiatSetting.setWechatReceivingImg(form.getWechatReceivingImg());
        userFiatSetting.setPayType(UserFiatConsts.USER_FIAT_PAYTYPE_3);

        this.dao.updateById(userFiatSetting);
        return ResultUtils.success();

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult uploadFile(final MultipartFile file, final Long userId) throws Exception {
        final String currentDay = DateUtil.getCurrentFormatTime();
        final Long currentTime = System.currentTimeMillis();
        final String path = Joiner.on('/').skipNulls().join(currentDay,
                currentTime.toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), "."));

        log.info("upload file, userId: {}, file path: {}", userId, path);

        final FileUploadResponse fileUploadResponse = this.fileUploadService.uploadFile(path, file.getInputStream());

        final UserFiatSetting userFiatSetting = this.getByUserId(userId, UserFiatConsts.USER_FIAT_PAYTYPE_1);
        if (ObjectUtils.isEmpty(userFiatSetting) || StringUtils.isEmpty(userFiatSetting.getBankCard())) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_NOT_BIND_BANK_FAILED);
        }

        return ResultUtils.success(fileUploadResponse);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult savePayment(final int payType, final UserFiatSettingDTO form) {
        final UserKycInfo userKycInfo = this.userKycInfoService.getByUserId(form.getUserId());
        if (ObjectUtils.isEmpty(userKycInfo)) {
            log.error("saveBankInfo 请kyc认证");
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_INFO_NOT_FOUND);
        }

        log.info("保存支付信息" + form.toString());

        if (payType == UserFiatConsts.USER_FIAT_BANKPAY) {
            if (StringUtils.isEmpty(form.getBankCard())) {
                return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
            }

            final String bankName = form.getBankName();
            final String bankCard = form.getBankCard();

            log.info("bankInfo={}, userId={}", JSON.toJSONString(form), form.getUserId());

            if (form.getId() != null) {
                final UserFiatSetting userFiatSetting = this.userFiatSettingRepos.selectById(form.getId());
                userFiatSetting.setUserName(userKycInfo.getFirstName());
                userFiatSetting.setBankNo(bankCard);
                userFiatSetting.setBankName(bankName);
                userFiatSetting.setBankCard(form.getBankCard());
                userFiatSetting.setBankAddress(form.getBankAddress());
                userFiatSetting.setPayType(UserFiatConsts.USER_FIAT_PAYTYPE_1);
                this.userFiatSettingRepos.updateById(userFiatSetting);
            } else {
                final UserFiatSetting userFiatSetting = UserFiatSetting.builder()
                        .userId(form.getUserId())
                        .userName(userKycInfo.getFirstName())
                        .bankNo(bankCard)
                        .bankName(bankName)
                        .bankAddress(form.getBankAddress())
                        .bankCard(form.getBankCard())
                        .payType(UserFiatConsts.USER_FIAT_PAYTYPE_1).build();
                this.userFiatSettingRepos.insert(userFiatSetting);
            }
        }

        if (payType == UserFiatConsts.USER_FIAT_ALIPAY) {
            UserFiatSetting userFiatSetting = this.getByUserId(form.getUserId(), UserFiatConsts.USER_FIAT_PAYTYPE_2);
            if (!ObjectUtils.isEmpty(userFiatSetting)) {
                log.error("saveAlipay支付宝收款码已经存在");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_ALIPAY_EXISTS_FAILED);
            }
            userFiatSetting = UserFiatSetting.builder().userId(form.getUserId()).build();
            userFiatSetting.setAlipayName(form.getAlipayName());
            userFiatSetting.setAlipayCard(form.getAlipayCard());
            userFiatSetting.setAlipayReceivingImg(form.getAlipayReceivingImg());
            userFiatSetting.setPayType(UserFiatConsts.USER_FIAT_PAYTYPE_2);
            this.dao.insert(userFiatSetting);
        }

        if (payType == UserFiatConsts.USER_FIAT_WECHATPAY) {
            UserFiatSetting userFiatSetting = this.getByUserId(form.getUserId(), UserFiatConsts.USER_FIAT_PAYTYPE_3);
            if (!ObjectUtils.isEmpty(userFiatSetting)) {
                log.error("saveWechatPay微信收款码已经存在");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_FIAT_WECHATPAY_EXISTS_FAILED);
            }
            userFiatSetting = UserFiatSetting.builder().userId(form.getUserId()).build();
            userFiatSetting.setWechatCard(form.getWechatCard());
            userFiatSetting.setWechatPayName(form.getWechatPayName());
            userFiatSetting.setWechatReceivingImg(form.getWechatReceivingImg());
            userFiatSetting.setPayType(UserFiatConsts.USER_FIAT_PAYTYPE_3);
            this.dao.insert(userFiatSetting);
        }

        this.userSettingsService.enablePayAuthFlag(form.getUserId(), payType, 1);

        return ResultUtils.success();
    }
}