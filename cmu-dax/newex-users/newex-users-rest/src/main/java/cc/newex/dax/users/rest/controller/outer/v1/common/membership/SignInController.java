package cc.newex.dax.users.rest.controller.outer.v1.common.membership;

import cc.newex.commons.lang.util.IpUtil;
import cc.newex.commons.security.jwt.model.JwtUserDetails;
import cc.newex.commons.security.jwt.token.JwtTokenProvider;
import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.common.consts.UserDetailConsts;
import cc.newex.dax.users.common.enums.BizErrorCodeEnum;
import cc.newex.dax.users.common.util.HttpSessionUtils;
import cc.newex.dax.users.common.util.ObjectCopyUtils;
import cc.newex.dax.users.common.util.WebUtils;
import cc.newex.dax.users.domain.UserDetailInfo;
import cc.newex.dax.users.domain.UserLoginInfo;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.domain.behavior.enums.BehaviorNameEnum;
import cc.newex.dax.users.domain.behavior.model.CheckBehaviorItem;
import cc.newex.dax.users.domain.behavior.model.UserBehaviorResult;
import cc.newex.dax.users.rest.common.limit.annotation.RetryLimit;
import cc.newex.dax.users.rest.common.limit.enums.RetryLimitTypeEnum;
import cc.newex.dax.users.rest.controller.base.BaseController;
import cc.newex.dax.users.rest.model.AccessTokenResVO;
import cc.newex.dax.users.rest.model.LoginReqVO;
import cc.newex.dax.users.service.behavior.UserBehaviorService;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 用户登录控制器
 */
@Slf4j
@RestController
@RequestMapping(value = "/v1/users/membership/sign-in")
public class SignInController extends BaseController {
    @Autowired
    private UserInfoService userInfoService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private UserBehaviorService userBehaviorService;
    @Autowired
    private AppCacheService appCacheService;

    /**
     * 用户登录访问路径
     * 二次验证类型 step2Type验证类型 1: Google 2MsgCode
     *
     * @param request
     * @param form
     * @return
     */
    @RetryLimit(type = RetryLimitTypeEnum.LOGIN)
    @PostMapping(value = "")
    public ResponseResult login(@RequestBody @Valid final LoginReqVO form,
                                final HttpServletRequest request) {
        final long userId = HttpSessionUtils.getUserId(request);
        // 用户已经登录
        if (userId >= 0) {
            log.error("sign in, userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.LOGIN_USER_ALREADY_LANDED);
        }

        // 获取登录IP地址
        final String ipAddress = IpUtil.getRealIPAddress(request);
        final String deviceId = WebUtils.getDeviceId(request);
        log.info("username: {}, ip: {}, deviceId: {} is sign-in", form.getUsername(), ipAddress, deviceId);

        final UserLoginInfo userLoginInfo = this.userInfoService.getUserLoginInfo(form.getUsername());
        if (userLoginInfo == null) {
            log.error("user sign in failure! User does not exist, username={}", form.getUsername());
            return ResultUtils.failure(BizErrorCodeEnum.LOGIN_LOGINNAME_OR_PASSWORD_ERROR);
        }

        //登录失败
        if (!this.passwordEncoder.matches(form.getPassword(), userLoginInfo.getPassword())) {
            log.error("user sign in failure! error password, username=[{}], rawPassword=[{}], encodedPassword=[{}]",
                    form.getUsername(), form.getPassword(), userLoginInfo.getPassword());

            return ResultUtils.failure(BizErrorCodeEnum.LOGIN_LOGINNAME_OR_PASSWORD_ERROR);
        }

        // 非正常用户
        if (userLoginInfo.getStatus() != UserDetailConsts.NORAML) {
            log.error("user is or disabled ! userid={}", userId);
            return ResultUtils.failure(BizErrorCodeEnum.LOGIN_USER_ACCOUNT_FROZEN);
        }

        final UserDetailInfo userDetailInfo = ObjectCopyUtils.map(userLoginInfo, UserDetailInfo.class);
        final UserBehaviorResult userBehaviorResult = this.userBehaviorService.getUserCheckRuleBehavior(BehaviorNameEnum.LOGIN_STEP2_AUTH.getName(), userDetailInfo.getId());
        final List<String> behaviorList = userBehaviorResult.getCheckItems().stream().map(CheckBehaviorItem::getName).collect(Collectors.toList());
        final List<String> totalBehaviorList = userBehaviorResult.getTotalCheckItems().stream().map(CheckBehaviorItem::getName).collect(Collectors.toList());
        final UserSettings userSettings = this.userSettingsService.getById(userLoginInfo.getId());

        userDetailInfo.setNickname(form.getUsername());
        userDetailInfo.setStatus(userLoginInfo.getStatus());
        userDetailInfo.setFrozen(userLoginInfo.getFrozen());
        userDetailInfo.setSpotFrozenFlag(userSettings.getSpotFrozenFlag());
        userDetailInfo.setC2cFrozenFlag(userSettings.getC2cFrozenFlag());
        userDetailInfo.setContractsFrozenFlag(userSettings.getContractsFrozenFlag());
        userDetailInfo.setAssetFrozenFlag(userSettings.getAssetFrozenFlag());
        userDetailInfo.setBrokerId(this.getBrokerId(request));
        userDetailInfo.setPerpetualProtocolFlag(userSettings.getPerpetualProtocolFlag());

        //二次验证
        if (!ObjectUtils.isEmpty(userSettings) && userSettings.getLoginAuthFlag() == UserDetailConsts.ENABLE_LOGIN_AUTH) {
            final String key = StringUtils.join(userDetailInfo.getId());
            this.appCacheService.setTwoFactorLoginUserId(key, userDetailInfo.getId());
            final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtStep2UserDetails(userDetailInfo);
            return ResultUtils.success(AccessTokenResVO.builder()
                    .accessToken(this.jwtTokenProvider.generateToken(jwtUserDetails))
                    .refreshToken("")
                    .scopes(behaviorList)
                    .totalScopes(totalBehaviorList)
                    .build()
            );
        }

        final JwtUserDetails jwtUserDetails = this.userInfoService.createJwtUserDetails(userDetailInfo);
        return ResultUtils.success(
                AccessTokenResVO.builder()
                        .accessToken(JwtTokenUtils.generateTokenAndCreateSession(jwtUserDetails, request))
                        .refreshToken("")
                        .scopes(Lists.newArrayList())
                        .totalScopes(Lists.newArrayList())
                        .build()
        );
    }
}
