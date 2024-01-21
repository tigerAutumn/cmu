package cc.newex.dax.users.rest.controller.inner.v1;

import cc.newex.commons.fileupload.FileUploadService;
import cc.newex.commons.lang.util.PwdStrengthUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.openapi.specs.config.OpenApiKeyConfig;
import cc.newex.commons.openapi.specs.util.OpenApiUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.integration.dto.message.MessageTemplateConsts;
import cc.newex.dax.users.common.consts.NoticeSendLogConsts;
import cc.newex.dax.users.common.consts.UserConsts;
import cc.newex.dax.users.common.consts.UserKycConsts;
import cc.newex.dax.users.common.consts.UserNoticeRecordConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.MobileUtils;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.criteria.UserKycLevelExample;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserFiatSetting;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserInviteRecord;
import cc.newex.dax.users.domain.UserIpRateLimit;
import cc.newex.dax.users.domain.UserKycLevel;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.domain.UserStatisticsInfo;
import cc.newex.dax.users.dto.admin.UserIpRateLimitDTO;
import cc.newex.dax.users.dto.apisecret.UserApiSecretResDTO;
import cc.newex.dax.users.dto.common.UserLevelEnum;
import cc.newex.dax.users.dto.kyc.KycUserLevelDTO;
import cc.newex.dax.users.dto.membership.SignUpReqDTO;
import cc.newex.dax.users.dto.membership.SignUpResDTO;
import cc.newex.dax.users.dto.membership.UserActivityConfigDTO;
import cc.newex.dax.users.dto.membership.UserFiatResDTO;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import cc.newex.dax.users.dto.membership.UserInviteDTO;
import cc.newex.dax.users.dto.membership.UserInviteRecordDTO;
import cc.newex.dax.users.dto.membership.UserStatisticsInfoDTO;
import cc.newex.dax.users.dto.security.UserFiatSettingDTO;
import cc.newex.dax.users.limited.BehaviorTheme;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.kyc.UserKycInfoService;
import cc.newex.dax.users.service.kyc.UserKycLevelService;
import cc.newex.dax.users.service.membership.UserActivityConfigService;
import cc.newex.dax.users.service.membership.UserApiSecretService;
import cc.newex.dax.users.service.membership.UserBrokerService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserInviteRecordService;
import cc.newex.dax.users.service.membership.UserIpRateLimitService;
import cc.newex.dax.users.service.membership.UserLevelService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserFiatSettingService;
import cc.newex.dax.users.service.security.UserNoticeEventService;
import cc.newex.dax.users.service.security.UserSecureEventService;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
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

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping(value = "/inner/v1/users")
public class UsersInnerController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserApiSecretService userApiSecretService;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private UserKycInfoService userKycInfoService;

    @Autowired
    private UserFiatSettingService userFiatSettingService;

    @Autowired
    private UserInviteRecordService userInviteRecordService;

    @Autowired
    private OpenApiKeyConfig openApiKeyConfig;

    @Autowired
    private AppCacheService appCacheService;

    @Autowired
    private UserActivityConfigService userActivityConfigService;

    @Autowired
    private UserIpRateLimitService userIpRateLimitService;

    @Autowired
    private UserLevelService userLevelService;

    @Autowired
    private UserKycLevelService userKycLevelService;

    @Autowired
    private UserNoticeEventService userNoticeEventService;

    @Autowired
    private UserBrokerService userBrokerService;

    @Autowired
    private UserSecureEventService userSecureEventService;

    @Resource(name = "ossFileUploadService")
    private FileUploadService fileUploadService;

    @GetMapping("/membership/{userId}")
    public ResponseResult getUserInfo(@PathVariable("userId") final long userId,
                                      @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }
        if (brokerId != null && brokerId.intValue() != userInfo.getBrokerId().intValue()) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS, null);
        }
        final UserInfoResDTO resDTO = ObjectCopyUtils.map(userInfo, UserInfoResDTO.class);

        final UserSettings userSettings = this.userSettingsService.getByUserId(userId);
        resDTO.setSpotFrozenFlag(userSettings.getSpotFrozenFlag());
        resDTO.setC2cFrozenFlag(userSettings.getC2cFrozenFlag());
        resDTO.setContractsFrozenFlag(userSettings.getContractsFrozenFlag());
        resDTO.setPerpetualProtocolFlag(userSettings.getPerpetualProtocolFlag());
        resDTO.setPortfolioProtocolFlag(userSettings.getPortfolioProtocolFlag());
        resDTO.setSpotProtocolFlag(userSettings.getSpotProtocolFlag());
        resDTO.setC2cProtocolFlag(userSettings.getC2cProtocolFlag());
        return ResultUtils.success(resDTO);
    }

    /**
     * @param userName
     * @description 根据手机号或者邮箱获取用户信息
     * @date 2018/7/6 下午4:07
     */
    @GetMapping("/membership/user-info")
    public ResponseResult<UserInfoResDTO> getUserInfoByUserName(@RequestParam("userName") final String userName) {
        final UserInfo userInfo = this.userInfoService.getUserInfo(userName);
        if (userInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST, null);
        }
        final UserInfoResDTO resDTO = ObjectCopyUtils.map(userInfo, UserInfoResDTO.class);

        final UserSettings userSettings = this.userSettingsService.getByUserId(userInfo.getId());
        resDTO.setSpotFrozenFlag(userSettings.getSpotFrozenFlag());
        resDTO.setC2cFrozenFlag(userSettings.getC2cFrozenFlag());
        resDTO.setContractsFrozenFlag(userSettings.getContractsFrozenFlag());
        resDTO.setPerpetualProtocolFlag(userSettings.getPerpetualProtocolFlag());
        resDTO.setPortfolioProtocolFlag(userSettings.getPortfolioProtocolFlag());
        resDTO.setSpotProtocolFlag(userSettings.getSpotProtocolFlag());
        resDTO.setC2cProtocolFlag(userSettings.getC2cProtocolFlag());
        return ResultUtils.success(resDTO);
    }

    @GetMapping("/membership/{userId}/detail")
    public ResponseResult<UserInfoResDTO> getUserDetailInfo(@PathVariable("userId") final long userId,
                                                            @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        final UserDetailInfo userDetailInfo = this.userInfoService.getUserDetailInfo(userId);

        if (userDetailInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST, null);
        }

        if (brokerId != null && brokerId.intValue() != userDetailInfo.getBrokerId().intValue()) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_ILLEGALITY_ACCESS, null);
        }

        final UserInfoResDTO resDTO = ObjectCopyUtils.map(userDetailInfo, UserInfoResDTO.class);

        return ResultUtils.success(resDTO);
    }

    /**
     * @param passphrase
     * @param apiKey
     * @description 根据用户apikey和密码获取用户信息
     * @date 2018/5/2 下午2:19
     */
    @GetMapping("/userInfo/{apiKey}")
    public ResponseResult<UserInfoResDTO> getUserInfoByApiKey(@PathVariable("apiKey") final String apiKey, @RequestParam("passphrase") final String passphrase) {
        final UserApiSecretResDTO userApiSecret = this.userApiSecretService.getApiSecretByCache(apiKey);
        if (userApiSecret == null) {
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_NOT_EXIST, null);
        }

        final String salt = OpenApiUtil.generatePassphraseSalt(apiKey, this.openApiKeyConfig.getPassphraseSaltConfig());
        final String reqPass = OpenApiUtil.encodePassphrase(this.openApiKeyConfig.getPassphraseAlgorithm(), passphrase, salt);

        if (StringUtil.notEquals(userApiSecret.getPassphrase(), reqPass)) {
            log.error("getUserApiSecret get userinfo is error,apiKey={},passphrase={}", apiKey, passphrase);
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_OPERATE_FAIL, null);
        }
        final UserDetailInfo userDetailInfo = this.userInfoService.getUserDetailInfo(userApiSecret.getUserId());

        final UserInfoResDTO resDTO = ObjectCopyUtils.map(userDetailInfo, UserInfoResDTO.class);
        return ResultUtils.success(resDTO);
    }

    /**
     * @param apiKey
     * @description 通过apikey获取api信息
     * @date 2018/5/2 下午4:14
     */
    @GetMapping("/api-secrets/{apiKey}")
    public ResponseResult<UserApiSecretResDTO> getUserApiSecret(@PathVariable("apiKey") final String apiKey) {
        final UserApiSecretResDTO userApiSecret = this.userApiSecretService.getApiSecretByCache(apiKey);

        if (ObjectUtils.isEmpty(userApiSecret)) {
            return ResultUtils.failure(BizErrorCodeEnum.APIKEY_NOT_EXIST, null);
        }

        return ResultUtils.success(userApiSecret);
    }

    @GetMapping("/ip-rate-limit/{ip}")
    public ResponseResult<UserIpRateLimitDTO> getUserIpRateLimit(@PathVariable("ip") final Long ip) {
        final UserIpRateLimit userIpRateLimit = this.userIpRateLimitService.getUserIpRateLimitByIp(ip);
        if (ObjectUtils.isEmpty(userIpRateLimit)) {
            return ResultUtils.failure(BizErrorCodeEnum.API_IP_RATE_LIMIT_NOT_EXIST, null);
        }

        final UserIpRateLimitDTO resDTO = ObjectCopyUtils.map(userIpRateLimit, UserIpRateLimitDTO.class);
        return ResultUtils.success(resDTO);
    }

    /**
     * 通过userId获取用户kyc认证的最高等级
     *
     * @param userId
     * @return
     */
    @GetMapping("/kyc/{userId}")
    public ResponseResult<Integer> getUserKycLevel(@PathVariable("userId") final long userId) {
        final UserKycLevel userKycLevel = this.userKycInfoService.getMaxKycLevelByUserId(userId);

        if (ObjectUtils.isEmpty(userKycLevel)) {
            return ResultUtils.success(0);
        }

        return ResultUtils.success(userKycLevel.getLevel());
    }

    @GetMapping("/kyc/userLevels")
    public ResponseResult<List<KycUserLevelDTO>> getUserKycLevels(@RequestParam("userIdList") final List<Long> userIdList) {
        log.info("getUserKycLevels begin: {}", userIdList == null ? 0 : userIdList.size());
        final UserKycLevelExample userLevelExample = new UserKycLevelExample();
        userLevelExample.createCriteria().andUserIdIn(userIdList).andStatusEqualTo(UserKycConsts.USER_KYC_STATUS_1);
        final List<UserKycLevel> userKycInfoList = this.userKycLevelService.getByExample(userLevelExample);
        final List<KycUserLevelDTO> kycUserInfoDTOList = new ArrayList<>();
        if (!CollectionUtils.isEmpty(userKycInfoList)) {
            userKycInfoList.forEach(
                    vo -> {
                        final KycUserLevelDTO dto = new KycUserLevelDTO();
                        BeanUtils.copyProperties(vo, dto);
                        kycUserInfoDTOList.add(dto);
                    }
            );
        }
        log.info("getUserKycLevels end");
        return ResultUtils.success(kycUserInfoDTOList);
    }

    /**
     * 通过userId获取法币设置列表
     *
     * @param userId
     * @return
     */
    @GetMapping("/fiat/{userId}")
    public ResponseResult<List<UserFiatSettingDTO>> getUserFiatSettingList(@PathVariable("userId") final long userId) {
        final List<UserFiatSettingDTO> list = Lists.newArrayList();
        final List<UserFiatSetting> resultList = this.userFiatSettingService.getListByUserId(userId);
        final UserDetailInfo userInfo = this.userInfoService.getUserDetailInfo(userId);

        if (CollectionUtils.isEmpty(resultList) || ObjectUtils.isEmpty(userInfo)) {
            return ResultUtils.success(Lists.newArrayList());
        }
        resultList.forEach(event -> {
            final UserFiatSettingDTO dto;
            dto = ObjectCopyUtils.map(event, UserFiatSettingDTO.class);
            dto.setAlipayReceivingImg(this.fileUploadService.getSignedUrl(dto.getAlipayReceivingImg()).replace("&amp;", "&"));
            dto.setWechatReceivingImg(this.fileUploadService.getSignedUrl(dto.getWechatReceivingImg()).replace("&amp;", "&"));
            dto.setAlipayAuthFlag(userInfo.getAlipayAuthFlag());
            dto.setWechatPayAuthFlag(userInfo.getWechatPayAuthFlag());
            dto.setBankPayAuthFlag(userInfo.getBankPayAuthFlag());
            list.add(dto);
        });

        return ResultUtils.success(list);
    }

    /**
     * @param userId
     * @description 通过用户id获取法币交易需要的用户名, 手机号和邮箱
     * @date 2018/5/17 下午8:16
     */
    @GetMapping("/fiat-user/{userId}")
    public ResponseResult getUserFiatRes(@PathVariable("userId") final long userId) {
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);

        if (ObjectUtils.isEmpty(userInfo)) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST);
        }

        UserFiatResDTO dto = this.appCacheService.getUserFiatInfo(userId);
        if (Objects.nonNull(dto)) {
            return ResultUtils.success(dto);
        }

        dto = UserFiatResDTO.builder().build();
        if (StringUtil.isMobile(userInfo.getMobile())) {
            log.info("getUserFiatRes mobile={},areaCode={}", userInfo.getMobile(), userInfo.getAreaCode());
            dto.setMobile(userInfo.getMobile());
            dto.setAreaCode(userInfo.getAreaCode());
        }
        if (StringUtil.isEmail(userInfo.getEmail())) {
            dto.setEmail(userInfo.getEmail());
        }
        dto.setRealName(userInfo.getRealName());
        this.appCacheService.setUserFiatInfo(userId, dto);
        return ResultUtils.success(dto);
    }

    /**
     * @param id       分页id
     * @param pageSize 分页大小,默认1000
     * @description 通过分页id获取邀请好友列表
     * @date 2018/5/29 下午8:28
     */
    @GetMapping("/invite/{id}")
    public ResponseResult<List<UserInviteRecordDTO>> queryUserInviteList(@PathVariable("id") final Long id,
                                                                         @RequestParam(value = "pageSize", defaultValue = "1000") final Integer pageSize) {
        final List<UserInviteRecord> list = this.userInviteRecordService.queryUserInviteList(id, pageSize);

        final List<UserInviteRecordDTO> userInviteRecordDTOList = ObjectCopyUtils.mapList(list, UserInviteRecordDTO.class);

        return ResultUtils.success(userInviteRecordDTOList);
    }

    /**
     * 分页查询
     *
     * @param id
     * @param startDate
     * @param endDate
     * @param pageIndex
     * @param pageSize
     * @return
     */
    @GetMapping("/invite/page")
    public ResponseResult<List<UserInviteRecordDTO>> queryUserInviteList(@RequestParam("id") final Long id,
                                                                         @RequestParam("startDate") final Date startDate,
                                                                         @RequestParam("endDate") final Date endDate,
                                                                         @RequestParam(value = "pageIndex", defaultValue = "1") final Integer pageIndex,
                                                                         @RequestParam(value = "pageSize", defaultValue = "1000") final Integer pageSize) {
        final List<UserInviteRecord> list = this.userInviteRecordService.queryUserInviteList(id, startDate, endDate, pageIndex, pageSize);

        final List<UserInviteRecordDTO> userInviteRecordDTOList = ObjectCopyUtils.mapList(list, UserInviteRecordDTO.class);

        return ResultUtils.success(userInviteRecordDTOList);
    }

    /**
     * @param userId
     * @param activityCode 活动编码
     * @description 通过用户id获取邀请用户信息
     * @date 2018/5/30 下午3:57
     */
    @GetMapping("/{activityCode}/{userId}")
    public ResponseResult<UserInviteDTO> queryUserInviteInfo(@PathVariable("userId") final Long userId, @PathVariable("activityCode") final String activityCode,
                                                             @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        final UserInviteDTO dto = this.userInviteRecordService.queryUserInviteInfo(userId, activityCode, brokerId);
        return ResultUtils.success(dto);
    }

    /**
     * @param idList
     * @description 通过用户id返回通过kyc2认证的用户id列表
     * @date 2018/6/1 下午4:04
     */
    @GetMapping("/kyc2/pass-list")
    public ResponseResult<List<Long>> queryKyc2PassIdList(@RequestParam("idList") final List<Long> idList) {
        return this.userKycInfoService.queryKyc2PassIdList(idList);
    }

    /**
     * 活动下线
     *
     * @param activityCode 活动编码
     * @return
     */
    @PostMapping("/offline/{activityCode}")
    public ResponseResult<Integer> offline(@PathVariable("activityCode") final String activityCode) {
        return ResultUtils.success(this.userActivityConfigService.offline(activityCode));
    }

    /**
     * 获取最新活动配置
     *
     * @return
     */
    @GetMapping("/activity-config")
    ResponseResult<UserActivityConfigDTO> getActivityConfig(@RequestParam(value = "activityCode", required = false) final String activityCode,
                                                            @RequestParam(value = "brokerId", required = false) final Integer brokerId) {
        return ResultUtils.success(this.userActivityConfigService.getActivityConfig(activityCode, brokerId));
    }

    /**
     * 通过用户id获取用户等级枚举
     *
     * @param userId
     * @return
     */
    @GetMapping("/user-level/{userId}")
    public ResponseResult<UserLevelEnum> getUserLevel(@PathVariable("userId") final long userId) {
        final UserLevelEnum userLevelEnum = this.userLevelService.getUserLevelEnum(userId);
        if (ObjectUtils.isEmpty(userLevelEnum)) {
            return ResultUtils.failure(BizErrorCodeEnum.USER_LEVEL_NOT_EXISTS, null);
        }
        return ResultUtils.success(userLevelEnum);
    }

    /**
     * @param level  用户等级
     * @param userId 用户id
     * @description 修改用户的等级
     * @date 2018/7/7 下午2:09
     */
    @PostMapping("/user-level/update/{userId}")
    public ResponseResult updateUserLevel(@RequestParam("level") final String level,
                                          @PathVariable("userId") final long userId) {
        final UserLevelEnum userLevelEnum = UserLevelEnum.getInstance(level);
        if (ObjectUtils.isEmpty(userLevelEnum)) {
            log.error("UsersInnerController updateUserLevel userLevel is not exits,userId={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.USER_LEVEL_NOT_EXISTS);
        }
        final int result = this.userLevelService.updateUserLevel(userId, level);

        if (result <= 0) {
            log.error("UsersInnerController updateUserLevel is error,userId={},result={}", userId, result);
            return ResultUtils.failure(BizErrorCodeEnum.USER_LEVEL_UPDATE_ERROR);
        }

        return ResultUtils.success();
    }

    /**
     * 获取用户统计数据
     */
    @GetMapping("/user/statistics")
    public ResponseResult<List<UserStatisticsInfoDTO>> getUserStatistics(@RequestParam("beginTime") final Long beginTime, @RequestParam("endTime") final Long endTime) {
        final List<UserStatisticsInfo> userStatisticsInfoList = this.userInfoService.getUserStatisticsInfoByTime(new Date(beginTime), new Date(endTime));
        final List<UserStatisticsInfoDTO> userStatisticsInfoDTOList = new ArrayList<>();
        if (!ObjectUtils.isEmpty(userStatisticsInfoList)) {
            final Map<String, List<UserStatisticsInfo>> userStatisticsInfoMap = new HashMap<>();
            for (final UserStatisticsInfo userStatisticsInfo : userStatisticsInfoList) {
                List<UserStatisticsInfo> infoList = userStatisticsInfoMap.get(userStatisticsInfo.getChannelCode());
                if (infoList == null) {
                    infoList = new ArrayList<>();
                    userStatisticsInfoMap.put(userStatisticsInfo.getChannelCode(), infoList);
                }
                infoList.add(userStatisticsInfo);
            }
            for (final Map.Entry<String, List<UserStatisticsInfo>> entry : userStatisticsInfoMap.entrySet()) {
                final List<UserStatisticsInfo> infoList = entry.getValue();
                final Set<Long> userIdSet = new HashSet<>();
                String channelCode = null, channelName = null, channelFullName = null;
                int userKyc1Count = 0, userKyc2Count = 0;
                for (final UserStatisticsInfo info : infoList) {
                    channelCode = info.getChannelCode();
                    channelName = info.getChannelName();
                    channelFullName = info.getChannelFullName();
                    userIdSet.add(info.getUserId());
                    final Integer level = info.getLevel();
                    if (level != null) {
                        if (level == 1) {
                            userKyc1Count++;
                        } else if (level == 2) {
                            userKyc2Count++;
                        }
                    }
                }
                userStatisticsInfoDTOList.add(UserStatisticsInfoDTO.builder().channelCode(channelCode)
                        .channelName(channelName).channelFullName(channelFullName).userCount(userIdSet.size())
                        .userKyc1Count(userKyc1Count).userKyc2Count(userKyc2Count).build());
            }
        }
        return ResultUtils.success(userStatisticsInfoDTOList);
    }

    @PostMapping("/sign-up")
    public ResponseResult signUp(@RequestBody @Valid final SignUpReqDTO signUpReqDTO) {
        if (UserConsts.LIMITED_REG_COUNTRIES.contains(signUpReqDTO.getAreaCode())) {
            log.error("current country {} is not allowed register", signUpReqDTO.getAreaCode());
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_COUNTRY_UNSUPPORT);
        }

        //密码强度校验
        final String level = PwdStrengthUtil.getStrengthLevelName(signUpReqDTO.getPassword());
        if (StringUtils.equalsIgnoreCase(level, PwdStrengthUtil.LOWEST)) {
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_PASSWORD_SIMPLE);
        }

        final String loginName = StringUtils.trim(signUpReqDTO.getLoginName());
        // 验证手机号
        final boolean isPhone = MobileUtils.checkPhoneNumber(loginName, String.valueOf(signUpReqDTO.getAreaCode()));

        // 不是手机号也不是邮箱
        if (!isPhone && !StringUtil.isEmail(loginName)) {
            log.error("login name is invalid! areaCode={},loginName={}", signUpReqDTO.getAreaCode(), loginName);
            return ResultUtils.failure(BizErrorCodeEnum.PHONE_FORMAT_ERROR);
        }

        // 如果存在则直接返回
        final UserInfo userInfo = this.userInfoService.getUserInfo(loginName);
        if (userInfo != null) {
            return ResultUtils.success(
                    SignUpResDTO.builder()
                            .userId(userInfo.getId())
                            .loginName(loginName)
                            .build()
            );
        }

        final long userId = this.userInfoService.register(
                signUpReqDTO.getLoginName(),
                signUpReqDTO.getPassword(),
                signUpReqDTO.getAreaCode(),
                signUpReqDTO.getIpAddress(),
                signUpReqDTO.getRegFrom(),
                signUpReqDTO.getChannel(),
                signUpReqDTO.getBrokerId()
        );

        if (userId <= 0) {
            log.error("mobile register failure! user id:{}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.REGISTER_SAVE_FAILED);
        }

        this.sendMessage(signUpReqDTO, userId, isPhone, signUpReqDTO.getBrokerId());

        return ResultUtils.success(
                SignUpResDTO.builder()
                        .userId(userId)
                        .loginName(loginName)
                        .build()
        );
    }

    private void sendMessage(final SignUpReqDTO signUpReqDTO, final long userId, final boolean isMobileReg, final Integer brokerId) {
        final String locale = Locale.SIMPLIFIED_CHINESE.toLanguageTag();
        try {
            final Map<String, Object> params = Maps.newHashMap();
            params.put("loginName", signUpReqDTO.getLoginName());
            params.put("password", signUpReqDTO.getPassword());

            if (isMobileReg) {
                this.userNoticeEventService.sendSMS(
                        locale,
                        BehaviorTheme.USERS1_REGISTER_PHONE_2,
                        NoticeSendLogConsts.BUSINESS_NOTIFICATION,
                        signUpReqDTO.getLoginName(),
                        signUpReqDTO.getAreaCode(),
                        MessageTemplateConsts.SMS_USERS_OPEN_API_REGISTER_SUCCESS,
                        userId,
                        params,
                        brokerId
                );
            } else {
                this.userNoticeEventService.sendEmail(
                        locale,
                        BehaviorTheme.USERS1_REGISTER_EMAIL_1,
                        UserNoticeRecordConsts.BUSINESS_NOTIFICATION,
                        signUpReqDTO.getLoginName(),
                        MessageTemplateConsts.MAIL_USERS_OPEN_API_REGISTER_SUCCESS,
                        userId,
                        params,
                        brokerId
                );
            }
        } catch (final Exception e) {
            log.error("send register sms notice error, data={}, e={}", signUpReqDTO, e);
        }
    }

    /**
     * 根据手机号或者邮箱获取用户id
     *
     * @param username
     */
    @GetMapping("/membership/user-info/user-id")
    public ResponseResult<Long> getUserIdByUsername(@RequestParam("username") final String username) {
        final UserInfo userInfo = this.userInfoService.getUserInfo(username);
        if (userInfo == null) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_EXIST, null);
        }
        return ResultUtils.success(userInfo.getId());
    }

    /**
     * 通过host获取brokerId
     * host:需要传域名,如www.coinmex.com,test.cmx.com
     *
     * @param host
     * @return
     */
    @GetMapping("/broker/detail")
    public ResponseResult<Integer> getBrokerId(@RequestParam("host") final String host) {
        final Integer brokerId = this.userBrokerService.getBrokerIdFromCache(host);

        log.info("host: {}, brokerId: {}", host, brokerId);

        return ResultUtils.success(brokerId);
    }

    /**
     * 查询用户ID对应的uid
     *
     * @param userIdList
     * @return
     */
    @GetMapping("/uid")
    public ResponseResult<List<String>> getUid(@RequestParam("ids") final List<Long> userIdList) {
        final List<String> uidList = this.userInfoService.getUid(userIdList);

        return ResultUtils.success(uidList);
    }

    /**
     * 判断用户当天是否登录过
     *
     * @param userId 用户ID
     * @return
     */
    @GetMapping(value = "/{userId}/logged/today")
    public ResponseResult<Boolean> isLoginWithToday(@PathVariable(value = "userId") final Long userId) {

        return ResultUtils.success(this.userInfoService.isLoginWithTodayByUserId(userId));
    }

    /**
     * 判断是否24小时内修改过安全设置  true:是 false:否
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/withdraw24HoursLimit/{userId}")
    public ResponseResult<Boolean> withdraw24HoursLimit(@PathVariable(value = "userId") final Long userId) {
        final Boolean result = this.userSecureEventService.withdraw24HoursLimit(userId);

        return ResultUtils.success(result);
    }

}
