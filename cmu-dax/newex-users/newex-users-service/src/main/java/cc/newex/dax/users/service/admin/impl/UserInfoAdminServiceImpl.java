package cc.newex.dax.users.service.admin.impl;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.common.consts.UserDetailConsts;
import cc.newex.dax.users.criteria.UserInfoExample;
import cc.newex.dax.users.criteria.UserInviteRecordExample;
import cc.newex.dax.users.data.UserInfoRepository;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserInviteRecord;
import cc.newex.dax.users.domain.UserSettings;
import cc.newex.dax.users.dto.membership.UserInfoResDTO;
import cc.newex.dax.users.service.admin.UserInfoAdminService;
import cc.newex.dax.users.service.cache.AppCacheService;
import cc.newex.dax.users.service.membership.UserInviteRecordService;
import cc.newex.dax.users.service.membership.UserSettingsService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * 用户表 服务实现
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
@Service
public class UserInfoAdminServiceImpl extends AbstractCrudService<UserInfoRepository, UserInfo, UserInfoExample, Long>
        implements UserInfoAdminService {

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserSettingsService userSettingsService;

    @Autowired
    private AppCacheService appCacheService;

    @Autowired
    private UserInviteRecordService userInviteRecordService;

    @Override
    public UserInfo getUserInfoById(final Long userId) {
        return this.userInfoRepository.selectUserInfoById(userId);
    }

    @Override
    public boolean isAuthenticatorMobile(final Long userId) {
        final UserInfo user = this.userInfoRepository.selectById(userId);
        return user != null && !StringUtils.isEmpty(user.getMobile());
    }

    @Override
    public int updateUserMobile(final Long userId, final String mobile, final int areaCode) {
        if (userId == null || StringUtils.isBlank(mobile)) {
            return 0;
        }

        final UserInfo userInfo = this.userInfoRepository.selectById(userId);
        if (userInfo == null) {
            return 0;
        }

        final UserInfo.UserInfoBuilder builder = UserInfo.builder();

        builder.id(userId);

        if (StringUtils.equals(userInfo.getEmail(), userInfo.getMobile())) {
            builder.email(mobile);
        }

        builder.mobile(mobile);

        if (areaCode > 0) {
            builder.areaCode(areaCode);
        }

        return this.userInfoRepository.updateById(builder.build());
    }

    @Override
    public boolean isCanModifyEmail(final UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        return StringUtil.isEmail(userInfo.getEmail());
    }

    @Override
    public int updateUserEmail(final Long userId, final String email) {
        if (userId == null || StringUtils.isBlank(email)) {
            return 0;
        }

        final UserInfo userInfo = this.userInfoRepository.selectById(userId);
        if (userInfo == null) {
            return 0;
        }

        final UserInfo.UserInfoBuilder builder = UserInfo.builder();

        builder.id(userId);

        if (StringUtils.equals(userInfo.getEmail(), userInfo.getMobile())) {
            builder.mobile(email);
        }

        builder.email(email);

        return this.userInfoRepository.updateById(builder.build());
    }

    @Override
    public boolean unbindGoogle(final Long userId) {
        final UserInfo userInfo = UserInfo.builder()
                .id(userId)
                .googleCode("")
                .build();
        this.userInfoRepository.updateById(userInfo);
        this.userSettingsService.enableGoogleAuthFlag(userId, UserDetailConsts.DISABLE_AUTH);
        return true;
    }

    @Override
    public boolean unbindMobile(final Long userId) {
        final UserSettings userSettings = this.userSettingsService.getByUserId(userId);
        if (userSettings.getEmailAuthFlag() == UserDetailConsts.ENABLE_EMAIL_AUTH) {
            final UserInfo userInfo = this.userInfoRepository.selectById(userId);
            userInfo.setMobile(userInfo.getEmail());
            this.userInfoRepository.updateById(userInfo);
            this.userSettingsService.enabbeMobileAuthFlag(userId, UserDetailConsts.DISABLE_AUTH);
            this.appCacheService.deleteUserFiatInfo(userId);
            return true;
        }
        return false;
    }

    @Override
    public boolean resetUserPassword(final UserInfo userInfo, final int pwdType) {
        userInfo.setPassword(StringUtils.EMPTY);
        return this.userInfoRepository.updateById(userInfo) > 0;
    }

    @Override
    public int changeEmailAntiPhishingCode(final Long userId, final String antiPhishingCode) {
        final UserInfo userInfo = UserInfo.builder()
                .antiPhishingCode(antiPhishingCode)
                .id(userId)
                .build();
        return this.userInfoRepository.updateById(userInfo);
    }

    /**
     * 分页查询列表
     *
     * @param pageInfo
     * @param userInfoResDTO
     * @return
     */
    @Override
    public List<UserInfo> listByPage(final PageInfo pageInfo, final UserInfoResDTO userInfoResDTO) {
        //uid用于查询邀请人id
        if (StringUtils.isNotEmpty(userInfoResDTO.getUid())) {
            final UserInviteRecordExample userInviteRecordExample = new UserInviteRecordExample();
            userInviteRecordExample.createCriteria().andInviteCodeLike(userInfoResDTO.getUid().trim() + "%");
            final List<UserInviteRecord> recordList = this.userInviteRecordService.getByPage(pageInfo, userInviteRecordExample);
            if (CollectionUtils.isEmpty(recordList)) {
                return new ArrayList<>();
            }
            final List<UserInfo> dataList = new ArrayList<>();
            recordList.forEach(record -> dataList.add(UserInfo.builder().id(record.getUserId()).build()));
            final List<UserInfo> userInfoList = this.getIn(dataList);
            String inviteCode = null;
            if (recordList.size() > 0) {
                inviteCode = recordList.get(0).getInviteCode();
            }
            if (inviteCode != null) {
                for (final UserInfo userInfo : userInfoList) {
                    userInfo.setUid(inviteCode);
                }
            }
            for (final UserInfo userInfo : userInfoList) {
                userInfo.setPassword(null);
            }
            return userInfoList;
        }

        final UserInfoExample userInfoExample = new UserInfoExample();
        final UserInfoExample.Criteria criteria = userInfoExample.createCriteria();

        if (Objects.nonNull(userInfoResDTO.getId())) {
            criteria.andIdEqualTo(userInfoResDTO.getId());
        }

        if (StringUtils.isNotBlank(userInfoResDTO.getEmail())) {
            log.info("email: {}", userInfoResDTO.getEmail());

            criteria.andEmailLike("%" + userInfoResDTO.getEmail() + "%");
        }

        if (StringUtils.isNotBlank(userInfoResDTO.getMobile())) {
            log.info("mobile: {}", userInfoResDTO.getMobile());

            criteria.andMobileLike("%" + userInfoResDTO.getMobile() + "%");
        }

        if (StringUtils.isNotBlank(userInfoResDTO.getRealName())) {
            log.info("realName: {}", userInfoResDTO.getRealName());

            criteria.andRealNameLike("%" + userInfoResDTO.getRealName() + "%");
        }

        if (userInfoResDTO.getChannel() != null) {
            criteria.andChannelEqualTo(userInfoResDTO.getChannel());
        }

        if (userInfoResDTO.getStartTime() != null) {
            criteria.andCreatedDateGreaterThanOrEqualTo(userInfoResDTO.getStartTime());
        }

        if (userInfoResDTO.getEndTime() != null) {
            criteria.andCreatedDateLessThanOrEqualTo(userInfoResDTO.getEndTime());
        }

        if (userInfoResDTO.getBrokerId() != null) {
            criteria.andBrokerIdEqualTo(userInfoResDTO.getBrokerId());
        }

        final List<UserInfo> userInfoList = this.getByPage(pageInfo, userInfoExample);

        if (CollectionUtils.isNotEmpty(userInfoList)) {
            final List<Long> dataList = new ArrayList<>();
            userInfoList.forEach(userInfo -> dataList.add(userInfo.getId()));
            final UserInviteRecordExample userInviteRecordExample = new UserInviteRecordExample();
            userInviteRecordExample.createCriteria().andUserIdIn(dataList);
            final List<UserInviteRecord> userInviteRecordList = this.userInviteRecordService.getByExample(userInviteRecordExample);
            final Map<Long, String> inviteCodeMap = new HashMap<>();
            userInviteRecordList.forEach(record -> inviteCodeMap.put(record.getUserId(), record.getInviteCode()));
            for (final UserInfo userInfo : userInfoList) {
                userInfo.setUid(inviteCodeMap.get(userInfo.getId()));
                userInfo.setPassword(null);
            }
        }
        return userInfoList;
    }

    @Override
    public String getSession(final Long userId) {
        return JSON.toJSONString(this.appCacheService.getSession(userId));
    }

    @Override
    protected UserInfoExample getPageExample(final String fieldName, final String keyword) {
        final UserInfoExample example = new UserInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}
