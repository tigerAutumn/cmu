package cc.newex.dax.users.rest.controller.outer.v1.common.individual;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.lang.util.PwdStrengthUtil;
import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.UserFiatConsts;
import cc.newex.dax.users.common.consts.UserKycConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.enums.kyc.KycStatusEnum;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.common.util.UsersUtils;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserFiatSetting;
import cc.newex.dax.users.domain.UserInviteRecord;
import cc.newex.dax.users.domain.UserKycInfo;
import cc.newex.dax.users.domain.UserKycLevel;
import cc.newex.dax.users.domain.UserLevel;
import cc.newex.dax.users.domain.UserLoginEvent;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.domain.behavior.enums.ProtocolSettingEnum;
import cc.newex.dax.users.domain.subaccount.OrganizationInfo;
import cc.newex.dax.users.enums.membership.UserTypeEnum;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.rest.model.LastLoginResVO;
import cc.newex.dax.users.rest.model.ProfileKycLevelVO;
import cc.newex.dax.users.rest.model.ProtocolSettingVO;
import cc.newex.dax.users.rest.model.UserProfileResVO;
import cc.newex.dax.users.rest.model.UserSettingsVO;
import cc.newex.dax.users.service.kyc.UserKycInfoService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserInviteRecordService;
import cc.newex.dax.users.service.membership.UserLevelService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import cc.newex.dax.users.service.security.UserFiatSettingService;
import cc.newex.dax.users.service.security.UserLoginEventService;
import cc.newex.dax.users.service.security.UserSecureEventService;
import cc.newex.dax.users.service.subaccount.OrganizationInfoService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Objects;

/**
 * 用户个人信息及设置
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/profile")
public class UserProfileController extends BaseController {

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserLoginEventService userLoginLogService;

    @Autowired
    private UserKycInfoService userKycInfoService;

    @Autowired
    private UserFiatSettingService userFiatSettingService;

    @Autowired
    private UserSecureEventService userSecureEventService;

    @Autowired
    private UserLevelService userLevelService;

    @Autowired
    private UserInviteRecordService userInviteRecordService;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private OrganizationInfoService organizationInfoService;

    @GetMapping("")
    public ResponseResult<?> profile(final HttpServletRequest request) {
        final Long userId = HttpSessionUtils.getUserId(request);

        if (userId <= 0) {
            log.error("user not login! userid={}", userId);

            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserDetailInfo userInfo = this.userInfoService.getUserDetailInfo(userId);
        if (userInfo == null) {
            log.error("user not exists! userId: {}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }

        final UserKycInfo userKycInfo = this.userKycInfoService.getByUserId(userId);

        final List<ProfileKycLevelVO> kycLevelVOList = Lists.newArrayList();

        final ProfileKycLevelVO level1 = this.getKycLevelInfo(userId, UserKycConsts.USER_KYC_LEVEL_1, userKycInfo);
        if (level1 != null) {
            kycLevelVOList.add(level1);
        }

        final ProfileKycLevelVO level2 = this.getKycLevelInfo(userId, UserKycConsts.USER_KYC_LEVEL_2, userKycInfo);
        if (level2 != null) {
            kycLevelVOList.add(level2);
        }

        final UserProfileResVO resForm = UserProfileResVO.builder()
                .userId(userInfo.getId())
                .areaCode(userInfo.getAreaCode())
                .nickname(UsersUtils.getStarLoginName(userInfo.getNickname()))
                .email(UsersUtils.getStarEmailLoginName(userInfo.getEmail()))
                .mobile(UsersUtils.getStarMobileLoginName(userInfo.getMobile()))
                .realName(StringUtil.getStarRealName(userInfo.getRealName()))
                .type(UserTypeEnum.parse(userInfo.getType()).getName())
                .subAccount(UsersUtils.toBoolean(userInfo.getParentId()))
                .kyc(kycLevelVOList)
                .brokerId(this.getBrokerId(request))
                .build();

        // 返回用户和邀请用户等级
        final UserLevel userLevel = this.userLevelService.getByUserId(userId);
        resForm.setUserLevel(userLevel.getUserLevel());

        final UserInviteRecord userInviteRecord = this.userInviteRecordService.getByUserId(userId);
        if (ObjectUtils.allNotNull(userInviteRecord)) {
            final UserLevel userInviteLevel = this.userLevelService.getByUserId(userInviteRecord.getInviteUserId());
            resForm.setInviteUserLevel(userInviteLevel.getUserLevel());
        }

        // 用户个人设置
        final UserSettingsVO userSettingsVO = this.getUserSettings(userInfo);
        resForm.setSettings(userSettingsVO);

        //获取当前登录信息是否IP异常
        final LastLoginResVO lastLoginResVO = this.getLastLoginInfo(userInfo.getId());
        resForm.setLastLogin(lastLoginResVO);

        final String orgName = this.getOrgName(userId);
        resForm.setOrgName(orgName);

        return ResultUtils.success(resForm);
    }

    /**
     * 获取机构名称
     *
     * @param userId
     * @return
     */
    private String getOrgName(final Long userId) {
        final OrganizationInfo organizationInfo = this.organizationInfoService.getByUserId(userId);

        if (ObjectUtils.allNotNull(organizationInfo)) {
            return organizationInfo.getOrgName();
        }

        return null;
    }

    /**
     * 查看用户个人设置
     *
     * @param userInfo
     * @return
     */
    private UserSettingsVO getUserSettings(final UserDetailInfo userInfo) {
        final UserFiatSetting userFiatSetting = this.userFiatSettingService.getByUserId(userInfo.getId(), UserFiatConsts.USER_FIAT_PAYTYPE_1);
        Boolean enableBankCardFlag = false;
        if (ObjectUtils.allNotNull(userFiatSetting)) {
            enableBankCardFlag = true;
        }
        final Boolean enableWithdrawLimit = this.userSecureEventService.withdraw24HoursLimit(userInfo.getId());

        return UserSettingsVO.builder()
                .enableLoginAuth(BooleanUtils.toBoolean(userInfo.getLoginAuthFlag()))
                .enableGoogleAuth(BooleanUtils.toBoolean(userInfo.getGoogleAuthFlag()))
                .isGoogleBind(StringUtils.isNotEmpty(userInfo.getGoogleCode()))
                .enableEmailAuth(BooleanUtils.toBoolean(userInfo.getEmailAuthFlag()))
                .enableMobileAuth(BooleanUtils.toBoolean(userInfo.getMobileAuthFlag()))
                .enableTradeAuth(BooleanUtils.toBoolean(userInfo.getTradeAuthFlag()))
                .enableAlipayAuthFlag(BooleanUtils.toBoolean(userInfo.getAlipayAuthFlag()))
                .enableWechatPayAuthFlag(BooleanUtils.toBoolean(userInfo.getWechatPayAuthFlag()))
                .enableBankPayAuthFlag(BooleanUtils.toBoolean(userInfo.getBankPayAuthFlag()))
                .enableBankCardFlag(enableBankCardFlag)
                .loginPwdStrength(PwdStrengthUtil.getStrengthLevelName(userInfo.getLoginPwdStrength()))
                .tradePwdStrength(PwdStrengthUtil.getStrengthLevelName(userInfo.getTradePwdStrength()))
                .tradePwdInput(UsersUtils.getTradePwdInputName(userInfo.getTradePwdInput()))
                .enableWithdrawLimit(enableWithdrawLimit)
                .enableSpotProtocolFlag(BooleanUtils.toBoolean(userInfo.getSpotProtocolFlag()))
                .enableC2CProtocolFlag(BooleanUtils.toBoolean(userInfo.getC2cProtocolFlag()))
                .enablePortfolioProtocolFlag(BooleanUtils.toBoolean(userInfo.getPortfolioProtocolFlag()))
                .enablePerpetualProtocolFlag(BooleanUtils.toBoolean(userInfo.getPerpetualProtocolFlag()))
                .build();
    }

    /**
     * 获取当前登录信息是否IP异常
     *
     * @param userId
     * @return
     */
    private LastLoginResVO getLastLoginInfo(final Long userId) {
        final UserLoginEvent secure = this.userLoginLogService.getLastLoginLog(userId);

        if (secure != null && !Objects.equals(secure.getIpAddress(), secure.getLastIpAddress())) {
            final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            return LastLoginResVO.builder()
                    .ip(IpUtil.longToString(secure.getIpAddress() == -1 ? 0 : secure.getIpAddress()))
                    .date(format.format(secure.getLastLoginDate()))
                    .region(secure.getRegion())
                    .build();
        }

        return null;
    }

    /**
     * 返回kyc认证等级信息列表
     *
     * @return
     */
    private ProfileKycLevelVO getKycLevelInfo(final Long userId, final Integer level, final UserKycInfo userKycInfo) {
        final UserKycLevel userKycLevel = this.userKycInfoService.getUserKycLevelByUserId(userId, level);

        if (ObjectUtils.allNotNull(userKycLevel) && ObjectUtils.allNotNull(userKycInfo)) {
            final ProfileKycLevelVO kycLevelVO = ProfileKycLevelVO.builder()
                    .level(userKycLevel.getLevel())
                    .status(KycStatusEnum.getKycStatusEnum(userKycLevel.getStatus()).getValue())
                    .country(userKycInfo.getCountry())
                    .rejectReason(userKycLevel.getRejectReason())
                    .build();

            return kycLevelVO;
        }

        return null;
    }

    @PostMapping("/protocol/setting")
    public ResponseResult<?> protocolSetting(final HttpServletRequest request, @RequestBody final ProtocolSettingVO protocolSettingVO) {
        final long userId = HttpSessionUtils.getUserId(request);
        if (userId <= 0) {
            log.error("protocolSetting user not login! userid is {}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_USER_NOT_LOGIN);
        }
        if (protocolSettingVO == null || protocolSettingVO.getType() == null || protocolSettingVO.getAgree() == null) {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .build();
        if (ProtocolSettingEnum.SPOT.getName().equalsIgnoreCase(protocolSettingVO.getType())) {
            userSettings.setSpotProtocolFlag(protocolSettingVO.getAgree() ? 1 : 0);
        } else if (ProtocolSettingEnum.C2C.getName().equalsIgnoreCase(protocolSettingVO.getType())) {
            userSettings.setC2cProtocolFlag(protocolSettingVO.getAgree() ? 1 : 0);
        } else if (ProtocolSettingEnum.PORTFOLIO.getName().equalsIgnoreCase(protocolSettingVO.getType())) {
            userSettings.setPortfolioProtocolFlag(protocolSettingVO.getAgree() ? 1 : 0);
        } else if (ProtocolSettingEnum.PERPETUAL.getName().equalsIgnoreCase(protocolSettingVO.getType())) {
            userSettings.setPerpetualProtocolFlag(protocolSettingVO.getAgree() ? 1 : 0);
        } else {
            return ResultUtils.failure(BizErrorCodeEnum.COMMON_PARAMS_EMPTY);
        }
        this.userSettingsService.editById(userSettings);
        return ResultUtils.success();
    }
}