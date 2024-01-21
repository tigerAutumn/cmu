package cc.newex.dax.users.service.kyc.impl;

import cc.newex.commons.fileupload.FileUploadResponse;
import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.lang.util.DateUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.common.config.FaceidProperties;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.consts.UserKycConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.enums.kyc.KycCardTypeEnum;
import cc.newex.dax.users.common.enums.kyc.KycImgsEnum;
import cc.newex.dax.users.common.exception.UsersBizException;
import cc.newex.dax.users.common.util.IDCardUtil;
import cc.newex.dax.users.common.util.ImageUtils;
import cc.newex.dax.users.criteria.UserKycInfoExample;
import cc.newex.dax.users.criteria.UserKycLevelExample;
import cc.newex.dax.users.data.UserInfoRepository;
import cc.newex.dax.users.data.UserKycInfoRepository;
import cc.newex.dax.users.data.UserKycLevelRepository;
import cc.newex.dax.users.data.UserKycTokenRepository;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserKycImg;
import cc.newex.dax.users.domain.UserKycInfo;
import cc.newex.dax.users.domain.UserKycLevel;
import cc.newex.dax.users.domain.UserKycToken;
import cc.newex.dax.users.domain.faceid.FaceToken;
import cc.newex.dax.users.dto.kyc.FaceIdReqDTO;
import cc.newex.dax.users.dto.kyc.KycChinaCacheDTO;
import cc.newex.dax.users.dto.kyc.KycFirstForeignDTO;
import cc.newex.dax.users.dto.kyc.KycForeignCacheDTO;
import cc.newex.dax.users.dto.kyc.KycSecondForeignDTO;
import cc.newex.dax.users.dto.kyc.KycUploadBackDTO;
import cc.newex.dax.users.dto.kyc.KycUploadFrontDTO;
import cc.newex.dax.users.dto.kyc.KycUploadReqDTO;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.faceid.FaceidService;
import cc.newex.dax.users.service.kyc.UserKycImgService;
import cc.newex.dax.users.service.kyc.UserKycInfoService;
import cc.newex.dax.users.service.kyc.UserKycLevelService;
import cc.newex.dax.users.service.kyc.UserKycTokenService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Joiner;
import com.google.common.collect.Maps;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.entity.ContentType;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 用户kyc表 服务实现
 *
 * @author newex-team
 * @date 2018-05-03
 */
@Slf4j
@Service
public class UserKycInfoServiceImpl extends AbstractCrudService<UserKycInfoRepository, UserKycInfo, UserKycInfoExample, Long>
        implements UserKycInfoService {

    @Autowired
    private UserKycInfoRepository userKycInfoRepository;

    @Autowired
    private UserKycLevelRepository userKycLevelRepository;

    @Autowired
    private UserKycTokenRepository userKycTokenRepository;

    @Autowired
    private FileUploadService fileUploadService;

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserKycTokenService userKycTokenService;

    @Autowired
    private UserKycImgService userKycImgService;

    @Autowired
    private UserKycLevelService userKycLevelService;

    @Autowired
    private UserNoticeEventService noticeEventService;

    @Autowired
    private AppCacheService appCacheService;

    @Autowired
    private FaceidProperties faceidProperties;

    @Autowired
    private FaceidService faceidService;

    private final String accept = ContentType.APPLICATION_JSON.getMimeType();

    @Override
    protected UserKycInfoExample getPageExample(final String fieldName, final String keyword) {
        final UserKycInfoExample example = new UserKycInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult uploadFile(final MultipartFile file, final String type, final Integer country, final Long userId) throws Exception {
        if (country.equals(156) && StringUtils.equals(type, KycImgsEnum.KYC_IMGS_FRONT.getValue())) {
            return this.uploadFrontFile(file, userId);
        }

        if (country.equals(156) && StringUtils.equals(type, KycImgsEnum.KYC_IMGS_BACK.getValue())) {
            return this.uploadBackFile(file, userId);
        }

        if (country.equals(156) && StringUtils.equals(type, KycImgsEnum.KYC_IMGS_HANDS.getValue())) {
            return this.uploadHandsImgFile(file, userId);
        }

        if (country.equals(156) && StringUtils.equals(type, KycImgsEnum.PHONE_REG_ADDRESS.getValue())) {
            return this.uploadAddressImgFile(file, userId);
        }

        return this.uploadOtherFile(file, type, userId);
    }

    /**
     * @param file
     * @param userId
     * @description 保存用户kyc1的正面照--中国
     * @date 2018/5/7 下午2:38
     */
    public ResponseResult uploadFrontFile(final MultipartFile file, final Long userId) throws Exception {
        final String currentDay = DateUtil.getCurrentFormatTime();
        final Long currentTime = System.currentTimeMillis();
        final String path = Joiner.on('/').skipNulls().join(currentDay,
                currentTime.toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), "."));

        final HttpResponse<JsonNode> response = Unirest.post(this.faceidProperties.getOcridcardUrl())
                .header("accept", this.accept)
                .field("api_key", this.faceidProperties.getApiKey())
                .field("api_secret", this.faceidProperties.getApiSecret())
                .field("legality", 1)
                .field("image", file.getInputStream(), ContentType.APPLICATION_OCTET_STREAM, "image.jpg")
                .asJson();

        log.info("uploadFrontFile response={}", response.getBody().getObject());

        if (ObjectUtils.isEmpty(response)) {
            log.error("uploadFrontFile faceid认证失败。");
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_TOKEN_TIMEOUT_FAILED);
        }

        log.info("uploadFrontFile-cost-time={},user_id={}", System.currentTimeMillis() - currentTime, userId);

        if (HttpStatus.OK.value() == response.getStatus()) {
            final JSONObject object = response.getBody().getObject();
            final String name;
            final String idCardNumber;
            try {
                name = object.getString("name");
                idCardNumber = object.getString("id_card_number");
                if (!IDCardUtil.isValidatedAllIdcard(idCardNumber) || name.contains("*")) {
                    log.error("uploadFrontFile 上传非法证件照userId={}", userId);
                    return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_IMG_FAILED);
                }
                if (IDCardUtil.isAgeIllegal(idCardNumber)) {
                    log.error("user age is illegal");
                    return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_KYC1_AGE_ILLEGAL);
                }
            } catch (final Exception e) {
                log.error("uploadFrontFile 上传非法证件照userId={}", userId);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_IMG_FAILED);
            }

            if (StringUtils.isEmpty(name) || StringUtils.isEmpty(idCardNumber)) {
                log.error("uploadFrontFile faceid认证失败。");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_IMG_FAILED);
            }

            final String gender = object.get("gender") + "";
            final JSONObject legality = object.getJSONObject("legality");
            final Double precision = legality.getDouble("ID Photo");
            final Double edited = legality.getDouble("Edited");

            if (precision == null || precision < this.faceidProperties.getPrecision()) {
                log.error("uploadFrontFile faceid证件认证精度不够,重新上传证件。");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_FACEID_PRECISIONLESS_FAILED);
            }
            if (edited == null || edited > this.faceidProperties.getEdited()) {
                log.error("uploadFrontFile faceid证件认证被编辑,重新上传证件。");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_FACEID_PRECISIONLESS_FAILED);
            }

            final FileUploadResponse fileUploadResponse;
            try {
                fileUploadResponse = this.fileUploadService.uploadFile(path, file.getInputStream());
                log.info("uploadFrontFile response={},time={}", JSON.toJSONString(fileUploadResponse), System.currentTimeMillis() - currentTime);
            } catch (final Exception e) {
                log.error("uploadFrontFile is error ,userId={}", userId, e);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
            }
            final KycUploadFrontDTO kycUploadResDTO = KycUploadFrontDTO.builder().name(name).cardNumber(idCardNumber)
                    .gender(gender).imagePath(fileUploadResponse.getUrl()).build();
            /**
             * 缓存kyc正面照图片
             */
            final KycChinaCacheDTO kycChinaCacheDTO = KycChinaCacheDTO.builder().name(name).idCardNumber(idCardNumber)
                    .gender(gender).frontPath(path).remarks(object.toString()).build();
            this.appCacheService.setChinaUserKycInfo(userId, kycChinaCacheDTO);
            return ResultUtils.success(kycUploadResDTO);
        } else {
            log.error("uploadFile response={}", JSON.toJSONString(response.getBody().getObject()));
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
        }
    }

    /**
     * @param file
     * @param userId
     * @description 保存用户kyc1的背面照--中国
     * @date 2018/5/7 下午2:38
     */
    private ResponseResult uploadBackFile(final MultipartFile file, final Long userId) throws Exception {
        final String currentDay = DateUtil.getCurrentFormatTime();
        final Long currentTime = System.currentTimeMillis();
        final String path = Joiner.on('/').skipNulls().join(currentDay,
                currentTime.toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), "."));
        final HttpResponse<JsonNode> response = Unirest.post(this.faceidProperties.getOcridcardUrl())
                .header("accept", this.accept)
                .field("api_key", this.faceidProperties.getApiKey())
                .field("api_secret", this.faceidProperties.getApiSecret())
                .field("legality", 1)
                .field("image", file.getInputStream(), ContentType.APPLICATION_OCTET_STREAM, "image.jpg")
                .asJson();
        if (ObjectUtils.isEmpty(response)) {
            log.error("uploadBackFile response返回为空。");
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_TOKEN_TIMEOUT_FAILED);
        }
        log.info("uploadBackFile-cost-time={},user_id={}", System.currentTimeMillis() - currentTime, userId);

        if (HttpStatus.OK.value() == response.getStatus()) {
            final JSONObject object = response.getBody().getObject();
            final String validDate;
            try {
                validDate = object.getString("valid_date");
            } catch (final Exception e) {
                log.error("uploadBackFile 上传非法证件照userId={}", userId);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_IMG_FAILED);
            }

            if (StringUtils.isEmpty(validDate)) {
                log.error("uploadBackFile 上传非法证件照userId={}", userId);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_IMG_FAILED);
            }

            final JSONObject legality = object.getJSONObject("legality");
            final Double precision = legality.getDouble("ID Photo");
            final Double edited = legality.getDouble("Edited");

            if (precision == null || precision < this.faceidProperties.getPrecision()) {
                log.error("uploadBackFile faceid证件认证精度不够,重新上传证件。");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_FACEID_PRECISIONLESS_FAILED);
            }
            if (edited == null || edited > this.faceidProperties.getEdited()) {
                log.error("uploadBackFile faceid证件认证被编辑,重新上传证件。");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_FACEID_PRECISIONLESS_FAILED);
            }

            final FileUploadResponse fileUploadResponse;
            try {
                fileUploadResponse = this.fileUploadService.uploadFile(path, file.getInputStream());
                log.info("uploadBackFile response={},time={}", JSON.toJSONString(fileUploadResponse), System.currentTimeMillis() - currentTime);
            } catch (final Exception e) {
                log.error("uploadBackFile is error ,userId={}", userId, e);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
            }

            try {
                final KycChinaCacheDTO kycChinaCacheDTO = this.appCacheService.getChinaUserKycInfo(userId);
                if (Objects.isNull(kycChinaCacheDTO)) {
                    return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_FACEID_FRONT_FAILED);
                }
                kycChinaCacheDTO.setValidDate(validDate);
                kycChinaCacheDTO.setBackPath(path);
                this.appCacheService.setChinaUserKycInfo(userId, kycChinaCacheDTO);
            } catch (final Exception e) {
                log.error("uploadBackFile 不去不到正面照缓存信息", e);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_FACEID_FRONT_FAILED);
            }

            final KycUploadBackDTO kycUploadBackDTO = KycUploadBackDTO.builder().validDate(validDate).imagePath(fileUploadResponse.getUrl()).build();
            return ResultUtils.success(kycUploadBackDTO);

        } else {
            log.error("uploadBackFile response={}", JSON.toJSONString(response.getBody().getObject()));
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
        }
    }

    /**
     * @param file
     * @param userId
     * @description 上传国内用户活体认证-手持照片
     * @date 2018/5/7 下午2:57
     */
    private ResponseResult uploadHandsImgFile(final MultipartFile file, final Long userId) {
        final String currentDay = DateUtil.getCurrentFormatTime();
        final Long currentTime = System.currentTimeMillis();
        final String path = Joiner.on('/').skipNulls().join(currentDay,
                currentTime.toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), "."));

        log.info("uploadHandsImgFile, userId: {}, path: {}", userId, path);

        FileUploadResponse fileUploadResponse = null;
        try {
            fileUploadResponse = this.fileUploadService.uploadFile(path, file.getInputStream());
        } catch (final Exception e) {
            log.error("uploadHandsImgFile is error, userId: " + userId, e);

            return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
        }

        final UserKycImg userKycImg = this.userKycImgService.getByUserid(userId);
        userKycImg.setHandsImg(fileUploadResponse.getFileName());
        userKycImg.setFrontImg(null);
        userKycImg.setBackImg(null);
        userKycImg.setAddressImg(null);
        userKycImg.setUpdatedDate(new Date());

        this.userKycImgService.editById(userKycImg);

        final Map<String, String> map = Maps.newHashMap();
        map.put("imagePath", fileUploadResponse.getUrl());

        log.info("uploadHandsImgFile, userId: {}, imagePath: {}", userId, map);

        return ResultUtils.success(map);
    }

    /**
     * @param file
     * @param userId
     * @description 上传国内用户活体认证-环境照片
     * @date 2018/5/7 下午2:57
     */
    private ResponseResult uploadAddressImgFile(final MultipartFile file, final Long userId) {
        final String currentDay = DateUtil.getCurrentFormatTime();
        final Long currentTime = System.currentTimeMillis();
        final String path = Joiner.on('/').skipNulls().join(currentDay,
                currentTime.toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), "."));

        log.info("uploadAddressImgFile, userId: {}, path: {}", userId, path);

        FileUploadResponse fileUploadResponse = null;
        try {
            fileUploadResponse = this.fileUploadService.uploadFile(path, file.getInputStream());
        } catch (final Exception e) {
            log.error("uploadAddressImgFile is error, userId: " + userId, e);

            return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
        }

        final UserKycImg userKycImg = this.userKycImgService.getByUserid(userId);
        userKycImg.setAddressImg(fileUploadResponse.getFileName());
        userKycImg.setFrontImg(null);
        userKycImg.setBackImg(null);
        userKycImg.setHandsImg(null);
        userKycImg.setUpdatedDate(new Date());

        this.userKycImgService.editById(userKycImg);

        final Map<String, String> map = Maps.newHashMap();
        map.put("imagePath", fileUploadResponse.getUrl());

        log.info("uploadAddressImgFile, userId: {}, imagePath: {}", userId, map);

        return ResultUtils.success(map);
    }

    /**
     * @param file
     * @param type
     * @param userId
     * @description 国外用户kyc图片上传
     * @date 2018/5/7 下午2:57
     */
    private ResponseResult uploadOtherFile(final MultipartFile file, final String type, final Long userId) throws Exception {
        final String currentDay = DateUtil.getCurrentFormatTime();
        final Long currentTime = System.currentTimeMillis();
        final String path = Joiner.on('/').skipNulls().join(currentDay,
                currentTime.toString() + "." + StringUtils.substringAfterLast(file.getOriginalFilename(), "."));

        final FileUploadResponse fileUploadResponse;
        try {
            fileUploadResponse = this.fileUploadService.uploadFile(path, file.getInputStream());
        } catch (final Exception e) {
            log.error("uploadOtherFile is error ,userId={}", userId, e);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
        }
        final KycForeignCacheDTO kycForeignCacheDTO = this.appCacheService.getForeignUserKycInfo(userId);
        kycForeignCacheDTO.setUserId(userId);

        if (StringUtils.equals(type, KycImgsEnum.KYC_IMGS_CARD.getValue())) {
            kycForeignCacheDTO.setCardType(KycImgsEnum.KYC_IMGS_CARD.getType());
            kycForeignCacheDTO.setCardImg(fileUploadResponse.getFileName());
        } else if (StringUtils.equals(type, KycImgsEnum.KYC_IMGS_HANDS.getValue())) {
            kycForeignCacheDTO.setHandsImg(fileUploadResponse.getFileName());
        } else if (StringUtils.equals(type, KycImgsEnum.PHONE_REG_ADDRESS.getValue())) {
            kycForeignCacheDTO.setAddressImg(fileUploadResponse.getFileName());
        }

        this.appCacheService.setForeignUserKycInfo(userId, kycForeignCacheDTO);

        final Map<String, String> map = Maps.newHashMap();
        map.put("imagePath", fileUploadResponse.getUrl());

        return ResultUtils.success(map);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult saveKycFirstInfo(final KycUploadReqDTO form) {
        final KycChinaCacheDTO kycChinaCacheDTO = this.appCacheService.getChinaUserKycInfo(form.getUserId());

        if (ObjectUtils.isEmpty(kycChinaCacheDTO)) {
            log.error("saveKycFirstInfo 获取缓存中数据失败,userId={}", form.getUserId());
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC1_SAVE_FAILED);
        }
        if (StringUtils.isEmpty(kycChinaCacheDTO.getFrontPath())) {
            log.error("saveKycFirstInfo 获取缓存中数据失败,请上传正面照,userId={}", form.getUserId());
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_FACEID_FRONT_FAILED);
        }
        if (StringUtils.isEmpty(kycChinaCacheDTO.getBackPath())) {
            log.error("saveKycFirstInfo 获取缓存中数据失败,请上传背面照,userId={}", form.getUserId());
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_FACEID_BACK_FAILED);
        }
        /**
         * 如果kyc1的证件号码已经认证,并且不是认证过的人,不让重新认证
         */
        final UserKycInfoExample userKycInfoExample = new UserKycInfoExample();
        userKycInfoExample.createCriteria().andCardNumberEqualTo(kycChinaCacheDTO.getIdCardNumber());
        final UserKycInfo userKycInfo = this.userKycInfoRepository.selectOneByExample(userKycInfoExample);
        if (!ObjectUtils.isEmpty(userKycInfo) && userKycInfo.getUserId().longValue() != form.getUserId().longValue()) {
            log.error("saveKycFirstInfo kyc1认证信息已经存在,userId={}", form.getUserId());
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_CARDNUMBER_FOUND);
        }

        try {
            final UserKycInfo newKycInfo = UserKycInfo.getInstance();
            newKycInfo.setUserId(form.getUserId());
            newKycInfo.setFirstName(kycChinaCacheDTO.getName());
            newKycInfo.setCardNumber(kycChinaCacheDTO.getIdCardNumber());
            newKycInfo.setGender(kycChinaCacheDTO.getGender());
            newKycInfo.setCountry(kycChinaCacheDTO.getCountry());

            newKycInfo.setValidDate(kycChinaCacheDTO.getValidDate());

            this.userKycInfoRepository.replace(newKycInfo);

            final UserKycImg userKycImg = UserKycImg.getInstance();
            userKycImg.setFrontImg(kycChinaCacheDTO.getFrontPath());
            userKycImg.setBackImg(kycChinaCacheDTO.getBackPath());
            userKycImg.setUserId(form.getUserId());
            this.userKycImgService.replace(userKycImg);

            final UserKycLevel userKycLevel = UserKycLevel.getInstance();
            userKycLevel.setUserId(form.getUserId());
            userKycLevel.setLevel(UserKycConsts.USER_KYC_LEVEL_1);
            userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_1);
            userKycLevel.setRemarks(kycChinaCacheDTO.getRemarks());
            this.userKycLevelRepository.replace(userKycLevel);

            /**
             * 设置用户的真实姓名
             */
            final UserInfo userInfo = this.userInfoRepository.selectById(form.getUserId());
            if (!ObjectUtils.isEmpty(userInfo)) {
                userInfo.setRealName(kycChinaCacheDTO.getName());
                this.userInfoRepository.updateById(userInfo);
            }

            log.info("saveKycFirstInfo form:{}, user id:{} success", form, form.getUserId());
            this.appCacheService.deleteChinaUserKycInfo(form.getUserId());
            return ResultUtils.success();
        } catch (final Exception ex) {
            log.info("saveKycFirstInfo user:{} failure", form);
            throw new UsersBizException(BizErrorCodeEnum.ERROR_KYC1_SAVE_FAILED, ex);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult saveForeignKycFirstInfo(final KycFirstForeignDTO form) {
        final UserKycInfo userKycInfo = UserKycInfo.getInstance();
        userKycInfo.setUserId(form.getUserId());
        userKycInfo.setCountry(form.getCountry());
        userKycInfo.setFirstName(form.getFirstName());
        userKycInfo.setMiddleName(form.getMiddleName());
        userKycInfo.setLastName(form.getLastName());
        userKycInfo.setCardNumber(form.getCardNumber());
        userKycInfo.setCardType(KycCardTypeEnum.getKycCardTypeEnum(form.getCardType()).getType());

        /**
         * 国内用户不能进行国外kyc1认证处理
         */
        if (form.getCountry() == 156) {
            log.error("国内用户不能进行国外kyc1认证处理,userId={}", form.getUserId());
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_IMG_FAILED);
        }
        final UserKycInfoExample userKycInfoExample = new UserKycInfoExample();
        userKycInfoExample.createCriteria().andCardNumberEqualTo(form.getCardNumber());
        final UserKycInfo kyc = this.userKycInfoRepository.selectOneByExample(userKycInfoExample);
        if (!ObjectUtils.isEmpty(kyc)) {
            log.error("saveForeignKycFirstInfo kyc证件号码已经存在,userId={}", form.getUserId());
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_CARDNUMBER_FOUND);
        }
        final UserKycLevelExample userKycLevelExample = new UserKycLevelExample();
        userKycLevelExample.createCriteria().andUserIdEqualTo(form.getUserId()).andLevelEqualTo(UserKycConsts.USER_KYC_LEVEL_1).andStatusEqualTo(UserKycConsts.USER_KYC_STATUS_1);
        final UserKycLevel kycLevel = this.userKycLevelRepository.selectOneByExample(userKycLevelExample);
        if (!ObjectUtils.isEmpty(kycLevel)) {
            log.error("saveForeignKycFirstInfo already pass kyc1,userId={}", form.getUserId());
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_USER_KYC1_PASS_FAILED);
        }

        final UserKycLevel userKycLevel = UserKycLevel.builder().userId(form.getUserId()).level(UserKycConsts.USER_KYC_LEVEL_1)
                .status(UserKycConsts.USER_KYC_STATUS_1).remarks("success").build();

        /**
         * 设置用户的真实姓名
         */
        final UserInfo userInfo = this.userInfoRepository.selectById(form.getUserId());
        if (!ObjectUtils.isEmpty(userInfo)) {
            userInfo.setRealName(userKycInfo.getFirstName() + userKycInfo.getMiddleName() + userKycInfo.getLastName());
            this.userInfoRepository.updateById(userInfo);
        }

        try {
            this.userKycInfoRepository.replace(userKycInfo);
            this.userKycLevelRepository.replace(userKycLevel);
            log.info("saveForeignKycFirstInfo form:{}, user id:{} success", JSON.toJSONString(form), form.getUserId());
            return ResultUtils.success();
        } catch (final Exception ex) {
            log.info("saveForeignKycFirstInfo form:{} failure", JSON.toJSONString(form));
            throw new UsersBizException(BizErrorCodeEnum.ERROR_KYC1_SAVE_FAILED, ex);
        }
    }

    @Override
    public UserKycInfo getByUserId(final Long userId) {
        final UserKycInfoExample example = new UserKycInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);

        return this.userKycInfoRepository.selectOneByExample(example);
    }

    @Override
    public UserKycLevel getUserKycLevelByUserId(final Long userId, final Integer level) {
        final UserKycLevelExample example = new UserKycLevelExample();
        example.createCriteria().andUserIdEqualTo(userId).andLevelEqualTo(level);

        return this.userKycLevelRepository.selectOneByExample(example);
    }

    @Override
    public UserKycLevel getMaxKycLevelByUserId(final Long userId) {
        final UserKycLevelExample userKycLevelExample = new UserKycLevelExample();
        userKycLevelExample.createCriteria().andUserIdEqualTo(userId).andStatusEqualTo(UserKycConsts.USER_KYC_STATUS_1);
        userKycLevelExample.setOrderByClause("level desc");

        final List<UserKycLevel> levelList = this.userKycLevelRepository.selectByExample(userKycLevelExample);

        if (CollectionUtils.isEmpty(levelList)) {
            return UserKycLevel.builder().userId(userId).level(0).build();
        } else {
            return levelList.stream().findFirst().get();
        }
    }

    @Override
    public boolean saveSecondLevel(final FaceIdReqDTO faceIdReqDTO) throws UnirestException {
        final UserKycLevel userKycLevel = UserKycLevel.builder().userId(faceIdReqDTO.getUserId())
                .level(UserKycConsts.USER_KYC_LEVEL_2).build();
        final boolean faceResult = this.getFaceResult(faceIdReqDTO.getBizId(), faceIdReqDTO.getUserId(), userKycLevel);
        return faceResult;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseResult saveSecondForeignLevel(final KycSecondForeignDTO dto) {
        log.info("saveSecondForeignLevel kycSecondForeignDTO={}", dto);
        final KycForeignCacheDTO kycForeignCacheDTO = this.appCacheService.getForeignUserKycInfo(dto.getUserId());
        if (Objects.isNull(kycForeignCacheDTO.getUserId())) {
            log.error("saveSecondForeignLevel 获取缓存中数据失败,userId={}", dto.getUserId());
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC2_SAVE_FAILED);
        }

        if (StringUtils.isAnyEmpty(kycForeignCacheDTO.getAddressImg(), kycForeignCacheDTO.getCardImg(), kycForeignCacheDTO.getHandsImg())) {
            log.error("saveSecondForeignLevel,请上传住址照片,证件照片和手持照片,userId={}", dto.getUserId());
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_UPLOAD_IMAGE_FAILED);
        }

        final UserKycInfo userKycInfo = this.getByUserId(dto.getUserId());
        userKycInfo.setUserId(dto.getUserId());
        userKycInfo.setAddress1(dto.getAddress1());
        userKycInfo.setAddress2(dto.getAddress2());
        userKycInfo.setAddress3(dto.getAddress3());
        userKycInfo.setCity(dto.getCity());
        userKycInfo.setZipCode(dto.getZipCode());

        final UserKycLevel userKycLevel = UserKycLevel.getInstance();

        userKycLevel.setUserId(dto.getUserId());
        userKycLevel.setLevel(UserKycConsts.USER_KYC_LEVEL_2);
        userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_4);
        userKycLevel.setRemarks("success");

        final UserKycImg userKycImg = UserKycImg.getInstance();
        userKycImg.setUserId(dto.getUserId());
        userKycImg.setCardImg(kycForeignCacheDTO.getCardImg());
        userKycImg.setCardType(kycForeignCacheDTO.getCardType());
        userKycImg.setHandsImg(kycForeignCacheDTO.getHandsImg());
        userKycImg.setAddressImg(kycForeignCacheDTO.getAddressImg());

        try {
            this.userKycImgService.replace(userKycImg);
            this.userKycLevelRepository.replace(userKycLevel);
            this.userKycInfoRepository.replace(userKycInfo);
            this.appCacheService.deleteForeignUserKycInfo(dto.getUserId());
            return ResultUtils.success();
        } catch (final Exception ex) {
            log.info("saveSecondForeignLevel failure kycSecondForeignDTO:{} ", JSON.toJSONString(dto));
            throw new UsersBizException(BizErrorCodeEnum.ERROR_KYC2_SAVE_FAILED, ex);
        }
    }

    @Override
    public ResponseResult verify(final Long userId, String data) throws Exception {
        final UserKycInfo userKycInfo = this.getByUserId(userId);
        if (ObjectUtils.isEmpty(userKycInfo)) {
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_KYC_INFO_NOT_FOUND);
        }
        final Base64.Decoder decoder = Base64.getDecoder();
        final UserKycLevel userKycLevel = UserKycLevel.getInstance();
        /*
         保存获取token返回结果入库
         */
        userKycLevel.setUserId(userId);
        userKycLevel.setLevel(UserKycConsts.USER_KYC_LEVEL_2);
        if (StringUtils.isNotEmpty(data)) {
            final com.alibaba.fastjson.JSONObject resultObject = JSON.parseObject(data);
            data = new String(decoder.decode(resultObject.getString("data")));
            final com.alibaba.fastjson.JSONObject object = JSON.parseObject(data);
            log.info("verify object:{}", object.toString());
            if (ObjectUtils.isEmpty(object)) {
                log.error("verify app活体认证失败");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
            }
            String resultFaceid = null;
            String faceGenuineness = null;
            try {
                resultFaceid = object.getString("result_faceid");
                faceGenuineness = object.getString("face_genuineness");
            } catch (final Exception e) {
                userKycLevel.setRemarks("app" + data);
                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
                this.userKycLevelRepository.replace(userKycLevel);
                log.error("verifyException resultFaceid={},userId={}", resultFaceid, userId);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
            }

            if (StringUtils.isEmpty(resultFaceid)) {
                userKycLevel.setRemarks("app" + data);
                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
                this.userKycLevelRepository.replace(userKycLevel);
                log.error("verify resultFaceid={},userId={}", resultFaceid, userId);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
            }
            if (StringUtils.isEmpty(faceGenuineness)) {
                userKycLevel.setRemarks("app" + data);
                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
                this.userKycLevelRepository.replace(userKycLevel);
                log.error("verify faceGenuineness={},userId={}", faceGenuineness, userId);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
            }
            /**
             *判断活体认证是否翻拍,人脸合成和面具
             */
            final com.alibaba.fastjson.JSONObject faceGenuinenessObject = JSON.parseObject(faceGenuineness);
            /**
             * 屏幕翻拍判断
             */
            final Double screen_replay_confidence = faceGenuinenessObject.getDouble("screen_replay_confidence") == null ?
                    0D : faceGenuinenessObject.getDouble("screen_replay_confidence");
            final Double screen_replay_threshold = faceGenuinenessObject.getDouble("screen_replay_threshold") == null ?
                    0D : faceGenuinenessObject.getDouble("screen_replay_threshold");
            /**
             * 面具判断
             */
            final Double mask_confidence = faceGenuinenessObject.getDouble("mask_confidence") == null ?
                    0D : faceGenuinenessObject.getDouble("mask_confidence");
            final Double mask_threshold = faceGenuinenessObject.getDouble("mask_threshold") == null ?
                    0D : faceGenuinenessObject.getDouble("mask_threshold");
            /**
             * 软件合成脸判断
             */
            final Double synthetic_face_confidence = faceGenuinenessObject.getDouble("synthetic_face_confidence") == null ?
                    0D : faceGenuinenessObject.getDouble("synthetic_face_confidence");
            final Double synthetic_face_threshold = faceGenuinenessObject.getDouble("synthetic_face_threshold") == null ?
                    0D : faceGenuinenessObject.getDouble("synthetic_face_threshold");

            if (screen_replay_confidence > screen_replay_threshold || mask_confidence > mask_threshold
                    || synthetic_face_confidence > synthetic_face_threshold) {
                userKycLevel.setRemarks("app" + data);
                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
                userKycLevel.setRejectReason(BizErrorCodeEnum.ERROR_MEGLIVE_NOT_ONESELF_FAILED.getMessage());
                this.userKycLevelRepository.replace(userKycLevel);
                log.error("verify app活体认证失败非本人,状态设置为拒绝");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_NOT_ONESELF_FAILED);
            }

            final com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(resultFaceid);
            final Double confidence = jsonObject.getDouble("confidence");
            final com.alibaba.fastjson.JSONObject thresholds = jsonObject.getJSONObject("thresholds");
            final Double threshold = thresholds.getDouble("1e-4");
            /**
             *分数小于阈值,说明不是一个人,认证不通过
             */
            if (confidence < threshold) {
                userKycLevel.setRemarks("app" + data);
                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
                userKycLevel.setRejectReason(BizErrorCodeEnum.ERROR_MEGLIVE_NOT_ONESELF_FAILED.getMessage());
                this.userKycLevelRepository.replace(userKycLevel);
                log.error("verify app活体认证失败非本人,状态设置为拒绝");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_NOT_ONESELF_FAILED);
            }

            userKycLevel.setRemarks("app" + data);
            userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_1);
            this.userKycLevelRepository.replace(userKycLevel);
            log.info("verify save success,userId={}", userId);
            return ResultUtils.success();
        }
        return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
    }

    @Override
    public ResponseResult v2_verify(final Long userId, final String delta, final String userAgent) {
        final UserKycInfo userKycInfo = this.getByUserId(userId);
        final UserKycImg userKycImg = this.userKycImgService.getByUserid(userId);
        final UserKycLevelExample userKycLevelExample = new UserKycLevelExample();
        userKycLevelExample.createCriteria().andUserIdEqualTo(userId).andLevelEqualTo(UserKycConsts.USER_KYC_LEVEL_2);
        //可能没有kyc2
        UserKycLevel userKycLevel = this.userKycLevelService.getOneByExample(userKycLevelExample);
        if (userKycLevel == null) {
            userKycLevel = UserKycLevel.builder().userId(userId).level(UserKycConsts.USER_KYC_LEVEL_2).build();
        }

        HttpResponse<JsonNode> response = null;

        try {
            final InputStream handInputStream = this.fetchInputStream(userKycImg.getHandsImg());
            final InputStream addressInputStream = this.fetchInputStream(userKycImg.getAddressImg());

            log.info("v2_verify userId: {}, handsImg: {}, addressImg: {}", userId, userKycImg.getHandsImg(), userKycImg.getAddressImg());

            response = Unirest.post(this.faceidProperties.getVerifyUrl())
                    .header("accept", this.accept)
                    .field("api_key", this.faceidProperties.getApiKey())
                    .field("api_secret", this.faceidProperties.getApiSecret())
                    .field("comparison_type", 1)
                    .field("face_image_type", "meglive")
                    .field("idcard_name", userKycInfo.getFirstName())
                    .field("idcard_number", userKycInfo.getCardNumber())
                    .field("delta", delta)
                    .field("check_delta", "1")
                    .field("return_faces", "1")
                    .field("image_best", handInputStream, ContentType.APPLICATION_OCTET_STREAM, "image_best.jpg")
                    .field("image_env", addressInputStream, ContentType.APPLICATION_OCTET_STREAM, "image_env.jpg")
                    .asJson();
        } catch (final Exception e) {
            log.error(e.getMessage(), e);
        }

        if (ObjectUtils.isEmpty(response)) {
            log.error("v2_verify response is null,userId={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
        }

        if (HttpStatus.OK.value() == response.getStatus()) {
            final JSONObject object = response.getBody().getObject();
            log.info("v2_verify object:{}", object.toString());
            if (ObjectUtils.isEmpty(object)) {
                log.error("v2_verify app活体认证失败");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
            }
            JSONObject resultFaceid = null;
            final JSONObject faceGenuineness;
            try {
                resultFaceid = object.getJSONObject("result_faceid");
                faceGenuineness = object.getJSONObject("face_genuineness");
            } catch (final Exception e) {
                userKycLevel.setRemarks(this.buildUserAgent(userAgent, object));
                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
                this.userKycLevelRepository.updateById(userKycLevel);
                log.error("v2_verify resultFaceid={},userId={}", resultFaceid, userId);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
            }

            if (resultFaceid == null) {
                userKycLevel.setRemarks(this.buildUserAgent(userAgent, object));
                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
                this.userKycLevelRepository.updateById(userKycLevel);
                log.error("v2_verify resultFaceid={},userId={}", resultFaceid, userId);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
            }
            if (faceGenuineness == null) {
                userKycLevel.setRemarks(this.buildUserAgent(userAgent, object));
                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
                this.userKycLevelRepository.replace(userKycLevel);
                log.error("v2_verify faceGenuineness={},userId={}", faceGenuineness, userId);
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
            }
            /**
             *判断活体认证是否翻拍,人脸合成和面具
             */
            final com.alibaba.fastjson.JSONObject faceGenuinenessObject = JSON.parseObject(faceGenuineness.toString());
            /**
             * 屏幕翻拍判断
             */
            final Double screen_replay_confidence = faceGenuinenessObject.getDouble("screen_replay_confidence") == null ?
                    0D : faceGenuinenessObject.getDouble("screen_replay_confidence");
            final Double screen_replay_threshold = faceGenuinenessObject.getDouble("screen_replay_threshold") == null ?
                    0D : faceGenuinenessObject.getDouble("screen_replay_threshold");
            /**
             * 面具判断
             */
            final Double mask_confidence = faceGenuinenessObject.getDouble("mask_confidence") == null ?
                    0D : faceGenuinenessObject.getDouble("mask_confidence");
            final Double mask_threshold = faceGenuinenessObject.getDouble("mask_threshold") == null ?
                    0D : faceGenuinenessObject.getDouble("mask_threshold");
            /**
             * 软件合成脸判断
             */
            final Double synthetic_face_confidence = faceGenuinenessObject.getDouble("synthetic_face_confidence") == null ?
                    0D : faceGenuinenessObject.getDouble("synthetic_face_confidence");
            final Double synthetic_face_threshold = faceGenuinenessObject.getDouble("synthetic_face_threshold") == null ?
                    0D : faceGenuinenessObject.getDouble("synthetic_face_threshold");

            if (screen_replay_confidence > screen_replay_threshold || mask_confidence > mask_threshold
                    || synthetic_face_confidence > synthetic_face_threshold) {
                userKycLevel.setRemarks(this.buildUserAgent(userAgent, object));
                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
                userKycLevel.setRejectReason(BizErrorCodeEnum.ERROR_MEGLIVE_NOT_ONESELF_FAILED.getMessage());
                this.userKycLevelRepository.replace(userKycLevel);
                log.error("v2_verify app活体认证失败非本人,状态设置为拒绝");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_NOT_ONESELF_FAILED);
            }

            final com.alibaba.fastjson.JSONObject jsonObject = JSON.parseObject(resultFaceid.toString());
            final Double confidence = jsonObject.getDouble("confidence");
            final com.alibaba.fastjson.JSONObject thresholds = jsonObject.getJSONObject("thresholds");
            final Double threshold = thresholds.getDouble("1e-4");
            /**
             *分数小于阈值,说明不是一个人,认证不通过
             */
            if (confidence < threshold) {
                userKycLevel.setRemarks(this.buildUserAgent(userAgent, object));
                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
                userKycLevel.setRejectReason(BizErrorCodeEnum.ERROR_MEGLIVE_NOT_ONESELF_FAILED.getMessage());
                this.userKycLevelRepository.replace(userKycLevel);
                log.error("v2_verify app活体认证失败非本人,状态设置为拒绝");
                return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_NOT_ONESELF_FAILED);
            }

            userKycLevel.setRemarks(this.buildUserAgent(userAgent, object));
            userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_1);
            this.userKycLevelRepository.replace(userKycLevel);
            log.info("v2_verify save success,userId={}", userId);
            return ResultUtils.success();
        }
        return ResultUtils.failure(BizErrorCodeEnum.ERROR_MEGLIVE_FAILED);
    }

    /**
     * 保存userAgent以便后期统计客户端信息
     *
     * @param userAgent
     * @param object
     */
    private String buildUserAgent(final String userAgent, final JSONObject object) {
        object.put("user_agent", userAgent);
        return object.toString();
    }

    /**
     * 构建s3 InputStream
     *
     * @param img
     * @return
     * @throws IOException
     */
    private InputStream fetchInputStream(final String img) throws IOException {
        final String signedUrl = this.fileUploadService.getSignedUrl(img);
        final URL url = new URL(signedUrl);
        final HttpURLConnection httpconn = (HttpURLConnection) url.openConnection();
        return httpconn.getInputStream();
    }

    /**
     * @param bizId
     * @description 返回是否验证成功, true:通过,false:失败
     * @date 2018/5/11 下午3:22
     */
    private boolean getFaceResult(final String bizId, final Long userId, final UserKycLevel userKycLevel) throws UnirestException {
        final HttpResponse<JsonNode> response = Unirest.get(this.faceidProperties.getResultUrl())
                .header("accept", this.accept)
                .queryString("api_key", this.faceidProperties.getApiKey())
                .queryString("api_secret", this.faceidProperties.getApiSecret())
                .queryString("biz_id", bizId)
                .queryString("return_image", 4)
                .asJson();
        final JSONObject object = response.getBody().getObject();
//        log.info("getFaceResult result: {}, {}, {}", bizId, userId, object);
        final String status;
        final JSONObject verifyResult;
        final JSONObject resultFaceid;
        final JSONObject imagesResult;
        try {
            verifyResult = object.getJSONObject("verify_result");
            status = object.getString("status");
            resultFaceid = verifyResult.getJSONObject("result_faceid");
            imagesResult = object.getJSONObject("images");
            final String imageBest = this.getImageBest(imagesResult.getString("image_best"));
            imagesResult.put("image_best", imageBest);
            object.put("images", imagesResult);
            userKycLevel.setRemarks(object.toString());

            try {
                final UserKycImg userKycImg = this.userKycImgService.getByUserid(userId);
                userKycImg.setHandsImg(imageBest);
                this.userKycImgService.replace(userKycImg);
            } catch (final Exception e) {
                log.error("getFaceResult-4 userId={}", userId, e);
            }
        } catch (final Exception e) {
            log.error("getFaceResult-1 object={},userId={}", object, userId);
            userKycLevel.setRemarks("error");
            userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
            this.userKycLevelRepository.replace(userKycLevel);
            this.sendMsg(userId);
            return false;
        }
        final Double confidence = resultFaceid.getDouble("confidence");
        final JSONObject thresholds = resultFaceid.getJSONObject("thresholds");
        //相识度大于69.315表示同一人
        final Double threshold = thresholds.getDouble("1e-4");
        log.info("confidence={},thresholds={},threshold={}", confidence, thresholds, threshold);
        //分数小于阈值,说明不是一个人,认证不通过
        if (confidence < threshold) {
            userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_3);
            this.userKycLevelRepository.replace(userKycLevel);
            log.error("getFaceResult-3 app活体认证失败非本人,状态设置为拒绝,userId={},resultFaceid={}", userId, resultFaceid);
            this.sendMsg(userId);
            return false;
        }

        /**
         * 判断是否通过
         */
        if (StringUtils.equalsIgnoreCase(status, "OK")) {
            final JSONObject livenessResult = object.getJSONObject("liveness_result");
            if (!ObjectUtils.isEmpty(livenessResult) && StringUtils.equals(livenessResult.getString("result"), "PASS")) {
                final UserKycToken userKycToken = this.userKycTokenService.getByBizId(bizId);
                userKycToken.setRemarks(object.toString());
                this.userKycTokenRepository.updateById(userKycToken);

                userKycLevel.setStatus(UserKycConsts.USER_KYC_STATUS_1);
                userKycLevel.setRemarks(object.toString());
                this.userKycLevelRepository.replace(userKycLevel);

                return true;
            }
        }
        return false;
    }

    /**
     * @param image
     * @description 获取图片相对路径
     * @date 2018/7/11 下午6:08
     */
    private String getImageBest(final String image) {
        final String currentDay = DateUtil.getCurrentFormatTime();
        final Long currentTime = System.currentTimeMillis();
        final String path = Joiner.on('/').skipNulls().join(currentDay,
                currentTime.toString() + ".jpeg");
        final InputStream inputStream = ImageUtils.base64ToImage(image.split(",")[1]);
        final FileUploadResponse fileUploadResponse;
        try {
            fileUploadResponse = this.fileUploadService.uploadFile(path, inputStream);
        } catch (final Exception e) {
            log.error("getImageBest is error ");
            return null;
        }
        return fileUploadResponse.getFileName();
    }

    /**
     * @param userId
     * @description 发送kyc审核失败的信息
     * @date 2018/5/28 上午10:05
     */
    public void sendMsg(final Long userId) {
        final UserInfo userInfo = this.userInfoRepository.selectById(userId);

        try {
            if (StringUtil.isEmail(userInfo.getEmail())) {
                this.noticeEventService.sendEmail("zh-cn",
                        BehaviorTheme.USERS10_KYC_AUDIT_FAIL,
                        NoticeSendLogConsts.BUSINESS_NOTIFICATION, userInfo.getEmail(),
                        MessageTemplateConsts.MAIL_USERS_KYC2_AUDIT_FAIL_SUCCESS, userId, Collections.EMPTY_MAP, userInfo.getBrokerId());
            } else {
                this.noticeEventService.sendSMS("zh-cn",
                        BehaviorTheme.USERS10_KYC_AUDIT_FAIL, NoticeSendLogConsts.BUSINESS_NOTIFICATION,
                        userInfo.getMobile(), userInfo.getAreaCode(),
                        MessageTemplateConsts.SMS_USERS_KYC2_AUDIT_FAIL_SUCCESS, userId, Collections.EMPTY_MAP, userInfo.getBrokerId());
            }
        } catch (final Exception e) {
            log.error("发送kyc2审核失败的信息失败, userId={}", userId);
        }

    }

    @Override
    public ResponseResult<List<Long>> queryKyc2PassIdList(final List<Long> idList) {
        final UserKycLevelExample example = new UserKycLevelExample();
        example.createCriteria().andUserIdIn(idList).andLevelEqualTo(UserKycConsts.USER_KYC_LEVEL_2).andStatusEqualTo(UserKycConsts.USER_KYC_STATUS_1);
        final List<UserKycLevel> list = this.userKycLevelRepository.selectByExample(example);
        List<Long> kyc2List = Collections.emptyList();
        if (!CollectionUtils.isEmpty(list)) {
            kyc2List = list.stream().map(UserKycLevel::getUserId).collect(Collectors.toList());
        }
        return ResultUtils.success(kyc2List);
    }

    @Override
    public FaceToken getFaceToken(final Long userId) {
        final UserKycInfo userKycInfo = this.getByUserId(userId);

        final FaceToken faceToken = this.faceidService.getToken(userKycInfo);

        if (faceToken == null) {
            return null;
        }

        // 保存获取token返回结果入库
        final UserKycToken userKycToken = UserKycToken.builder()
                .userId(userId)
                .token(faceToken.getToken())
                .bizId(faceToken.getBizId())
                .returnResult(JSON.toJSONString(faceToken))
                .build();

        this.userKycTokenRepository.insert(userKycToken);

        return faceToken;
    }
}