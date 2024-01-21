package cc.newex.dax.users.service.frozen.impl;

import cc.newex.commons.security.jwt.token.JwtTokenUtils;
import cc.newex.commons.ucenter.model.SessionInfo;
import cc.newex.dax.users.criteria.UserInfoExample;
import cc.newex.dax.users.criteria.UserSettingsExample;
import cc.newex.dax.users.data.UserInfoRepository;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.frozen.UserFrozenService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author newex-team
 * @date 2018-07-03
 */
@Slf4j
@Service
public class UserFrozenServiceImpl implements UserFrozenService {

    @Autowired
    private UserInfoRepository userInfoDao;
    @Autowired
    private UserSettingsService userSettingsService;
    @Autowired
    private AppCacheService appCacheService;

    @Override
    public Integer getGlobalFrozen(final String name) {
        return this.appCacheService.getGlobalFrozenStatus(name);
    }

    @Override
    public void setGlobalFrozen(final String name, final Integer status) {
        this.appCacheService.setGlobalFrozenStatus(name, status);
    }

    @Override
    public void deleteGlobalFrozen(final String name) {
        this.appCacheService.deleteGlobalFrozenStatus(name);
    }

    @Override
    public int frozen(final Long userId, final Integer status, final String remark) {
        final UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .frozen(status)
                .memo(remark)
                .build();
        final int result = this.userInfoDao.updateById(userInfo);

        //删除open api 用户信息缓存
        this.appCacheService.deleteOpenApiUserInfo(userId);

        //更新当前用户登录会话状态
        final SessionInfo session = SessionInfo.builder()
                .userId(userId)
                .frozen(status)
                .build();
        JwtTokenUtils.updateSession(session);

        return result;
    }

    @Override
    public int frozenSpot(final Long userId, final Integer status, final String remark) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .spotFrozenFlag(status)
                .build();
        final int result = this.userSettingsService.editByExample(userSettings, this.getUserSettingsExample(userId));

        //删除open api 用户信息缓存
        this.appCacheService.deleteOpenApiUserInfo(userId);

        //更新当前用户登录会话状态
        final SessionInfo session = SessionInfo.builder()
                .userId(userId)
                .spotFrozen(status)
                .build();
        JwtTokenUtils.updateSession(session);

        return result;
    }

    @Override
    public int frozenC2C(final Long userId, final Integer status, final String remark) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .c2cFrozenFlag(status)
                .build();
        final int result = this.userSettingsService.editByExample(userSettings, this.getUserSettingsExample(userId));

        //删除open api 用户信息缓存
        this.appCacheService.deleteOpenApiUserInfo(userId);

        //更新当前用户登录会话状态
        final SessionInfo session = SessionInfo.builder()
                .userId(userId)
                .c2cFrozen(status)
                .build();
        JwtTokenUtils.updateSession(session);

        return result;
    }

    @Override
    public int frozenContracts(final Long userId, final Integer status, final String remark) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .contractsFrozenFlag(status)
                .build();
        final int result = this.userSettingsService.editByExample(userSettings, this.getUserSettingsExample(userId));

        //删除open api 用户信息缓存
        this.appCacheService.deleteOpenApiUserInfo(userId);

        //更新当前用户登录会话状态
        final SessionInfo session = SessionInfo.builder()
                .userId(userId)
                .contractsFrozen(status)
                .build();
        JwtTokenUtils.updateSession(session);

        return result;
    }

    @Override
    public int frozenAsset(final Long userId, final Integer status, final String remark) {
        final UserSettings userSettings = UserSettings.builder()
                .userId(userId)
                .assetFrozenFlag(status)
                .build();
        final int result = this.userSettingsService.editByExample(userSettings, this.getUserSettingsExample(userId));

        //删除open api 用户信息缓存
        this.appCacheService.deleteOpenApiUserInfo(userId);

        //更新当前用户登录会话状态
        final SessionInfo session = SessionInfo.builder()
                .userId(userId)
                .assetFrozen(status)
                .build();
        JwtTokenUtils.updateSession(session);

        return result;
    }

    @Override
    public int getUserFrozenStatus(final Long userId) {
        final UserInfoExample example = new UserInfoExample();
        example.createCriteria()
                .andIdEqualTo(userId);
        final UserInfo userInfo = this.userInfoDao.selectOneByExample(example);
        return userInfo == null ? 0 : userInfo.getFrozen();
    }

    @Override
    public int getUserSpotFrozenStatus(final Long userId) {
        final UserSettings userSettings = this.userSettingsService.getOneByExample(this.getUserSettingsExample(userId));
        return userSettings == null ? 0 : userSettings.getSpotFrozenFlag();
    }

    @Override
    public int getUserC2CFrozenStatus(final Long userId) {
        final UserSettings userSettings = this.userSettingsService.getOneByExample(this.getUserSettingsExample(userId));
        return userSettings == null ? 0 : userSettings.getC2cFrozenFlag();
    }

    @Override
    public int getUserContractsFrozenStatus(final Long userId) {
        final UserSettings userSettings = this.userSettingsService.getOneByExample(this.getUserSettingsExample(userId));
        return userSettings == null ? 0 : userSettings.getContractsFrozenFlag();
    }

    @Override
    public int getUserAssetFrozenStatus(final Long userId) {
        final UserSettings userSettings = this.userSettingsService.getOneByExample(this.getUserSettingsExample(userId));
        return userSettings == null ? 0 : userSettings.getAssetFrozenFlag();
    }

    private UserSettingsExample getUserSettingsExample(final Long userId) {
        final UserSettingsExample example = new UserSettingsExample();
        example.createCriteria()
                .andUserIdEqualTo(userId);
        return example;
    }
}
