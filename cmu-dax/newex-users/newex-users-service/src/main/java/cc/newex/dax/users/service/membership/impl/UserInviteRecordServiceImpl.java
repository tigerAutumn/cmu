package cc.newex.dax.users.service.membership.impl;

import cc.newex.commons.lang.util.StringUtil;
import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.common.enums.SiteDomainEnum;
import cc.newex.dax.users.criteria.UserActivityConfigExample;
import cc.newex.dax.users.criteria.UserInviteRecordExample;
import cc.newex.dax.users.data.UserInviteRecordRepository;
import cc.newex.dax.users.domain.UserActivityConfig;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.UserInviteRecord;
import cc.newex.dax.users.domain.UserLevel;
import cc.newex.dax.users.dto.admin.UserInviteDetailDTO;
import cc.newex.dax.users.dto.admin.UserInviteReqDTO;
import cc.newex.dax.users.dto.common.UserLevelEnum;
import cc.newex.dax.users.dto.membership.UserActivityConfigDTO;
import cc.newex.dax.users.dto.membership.UserInviteDTO;
import cc.newex.dax.users.service.membership.UserActivityConfigService;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.membership.UserInviteRecordService;
import cc.newex.dax.users.service.membership.UserLevelService;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 邀请好友记录表 服务实现
 *
 * @author newex-team
 * @date 2018-05-29
 */
@Slf4j
@Service
public class UserInviteRecordServiceImpl
        extends AbstractCrudService<UserInviteRecordRepository, UserInviteRecord, UserInviteRecordExample, Long>
        implements UserInviteRecordService {

    @Autowired
    private UserInviteRecordRepository userInviteRecordRepos;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserLevelService userLevelService;

    @Autowired
    private UserActivityConfigService userActivityConfigService;

//    @Autowired
//    private UserActivityConfigRepository userActivityConfigRepository;

    @Value("${newex.activity.url}")
    private String activityUrl;

    @Value("${newex.app.domain}")
    private String domain;

    @Override
    protected UserInviteRecordExample getPageExample(final String fieldName, final String keyword) {
        final UserInviteRecordExample example = new UserInviteRecordExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public UserInviteRecord getByUserId(final Long userId) {
        final UserInviteRecordExample example = new UserInviteRecordExample();
        example.createCriteria().andUserIdEqualTo(userId);
        return this.userInviteRecordRepos.selectOneByExample(example);
    }

    private void inviteRegisterBitmore(final Long userId, final String inviteCode, final String activityCode, final Integer brokerId) {
        if (StringUtils.isEmpty(inviteCode) || StringUtils.isEmpty(activityCode)) {
            return;
        }
        final UserInfo inviteUserInfo = this.userInfoService.getUserInfoByUid(inviteCode);
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId); //注册人信息

        final String userName;
        if (StringUtil.isEmail(userInfo.getEmail())) {
            userName = StringUtil.getStarEmail(userInfo.getEmail());
        } else {
            userName = StringUtil.getStarMobile(userInfo.getMobile());
        }

        if (StringUtils.isNotEmpty(inviteCode) && StringUtils.isNotEmpty(activityCode)) {
            final UserActivityConfigExample configExample = new UserActivityConfigExample();
            configExample.createCriteria().andActivityCodeEqualTo(activityCode).andOnlineEqualTo(1);
            final UserActivityConfig userActivityConfig = this.userActivityConfigService.getOneByExample(configExample);
            if (ObjectUtils.allNotNull(userActivityConfig, inviteUserInfo)) {
                final UserInviteRecord userInviteRecord = UserInviteRecord.builder().userId(userId).inviteUserId(inviteUserInfo.getId())
                        .inviteCode(inviteCode).activityCode(activityCode).userName(userName).currencyId(userActivityConfig.getCurrencyId())
                        .currencyNum(userActivityConfig.getCurrencyNum()).inviteCurrencyId(0).inviteCurrencyNum(0D).brokerId(brokerId).build();
                this.userInviteRecordRepos.insert(userInviteRecord);
            }
        }
    }

    private void inviteRegisterCoinmex(final Long userId, final String inviteCode, String activityCode, final Integer brokerId) {
        log.info("inviteRegisterCoinmex:" + "userId = [" + userId + "], inviteCode = [" + inviteCode + "], activityCode = [" + activityCode + "], brokerId = [" + brokerId + "]");

        final UserInfo inviteUserInfo;
        if (StringUtils.isEmpty(inviteCode)) {
            inviteUserInfo = this.userInfoService.getUserInfo(7L);
        } else {
            inviteUserInfo = this.userInfoService.getUserInfoByUid(inviteCode);
        }

        if (Objects.isNull(inviteUserInfo)) {
            return;
        }

        //注册人信息
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);

        String userName = null;
        if (StringUtil.isEmail(userInfo.getEmail())) {
            userName = StringUtil.getStarEmail(userInfo.getEmail());
        } else {
            userName = StringUtil.getStarMobile(userInfo.getMobile());
        }

        // 无论活动是否下线、是否存在，都保存对应的邀请关系
        UserActivityConfigDTO userActivityConfig = this.userActivityConfigService.getActivityConfig(activityCode, brokerId);
        if (Objects.isNull(userActivityConfig)) {
            userActivityConfig = this.userActivityConfigService.getActivityConfig(null, brokerId);
        }

        activityCode = "";
        Integer currencyId = 0;
        Double currencyNum = 0.0;
        Integer inviteCurrencyId = 0;
        Double inviteCurrencyNum = 0.0;

        if (Objects.nonNull(userActivityConfig)) {
            activityCode = userActivityConfig.getActivityCode();
            currencyId = userActivityConfig.getCurrencyId();
            currencyNum = userActivityConfig.getCurrencyNum();
            inviteCurrencyId = userActivityConfig.getInviteCurrencyId();
            inviteCurrencyNum = userActivityConfig.getInviteCurrencyNum();
        }

        final UserInviteRecord userInviteRecord = UserInviteRecord.builder()
                .userId(userId)
                .userName(userName)
                .inviteUserId(inviteUserInfo.getId())
                .inviteCode(inviteUserInfo.getUid())
                .activityCode(activityCode)
                .currencyId(currencyId)
                .currencyNum(currencyNum)
                .inviteCurrencyId(inviteCurrencyId)
                .inviteCurrencyNum(inviteCurrencyNum)
                .brokerId(brokerId)
                .build();

        this.userInviteRecordRepos.insert(userInviteRecord);
    }

    @Override
    public void inviteRegister(final Long userId, final String inviteCode, final String activityCode, final Integer brokerId) {
        log.info("inviteRegister:" + "userId = [" + userId + "], inviteCode = [" + inviteCode + "], activityCode = [" + activityCode + "], brokerId = [" + brokerId + "]");
        if (this.domain.contains(SiteDomainEnum.COINMEX.getDomain())) {
            this.inviteRegisterCoinmex(userId, inviteCode, activityCode, brokerId);
            return;
        } else if (this.domain.contains(SiteDomainEnum.BITMORE.getDomain())) {
            this.inviteRegisterBitmore(userId, inviteCode, activityCode, brokerId);
            return;
        }
    }

    @Override
    public List<UserInviteRecord> queryUserInviteList(final Long id, final Integer pageSize) {
        final UserInviteRecordExample recordExample = new UserInviteRecordExample();
        recordExample.createCriteria().andIdGreaterThan(id);
        recordExample.setOrderByClause(" id asc");
        return this.userInviteRecordRepos.selectUserInviteList(id, pageSize);
    }

    @Override
    public List<UserInviteRecord> queryUserInviteList(final Long id, final Date startDate, final Date endDate, final Integer pageIndex, final Integer pageSize) {
        final UserInviteRecordExample example = new UserInviteRecordExample();
        final UserInviteRecordExample.Criteria criteria = example.createCriteria();

        if (id != null) {
            criteria.andIdGreaterThan(id);
        }

        if (startDate != null) {
            criteria.andCreatedDateGreaterThanOrEqualTo(startDate);
        }

        if (endDate != null) {
            criteria.andCreatedDateLessThanOrEqualTo(endDate);
        }

        // 按照ID升序排列
        final PageInfo pageInfo = new PageInfo((pageIndex - 1) * pageSize, pageSize, "id", PageInfo.SORT_TYPE_ASC);

        return this.userInviteRecordRepos.selectByPager(pageInfo, example);
    }

    @Override
    public List<UserInviteReqDTO> queryUserInviteList(UserInviteReqDTO dto) {
        final List<UserInviteReqDTO> list = Lists.newArrayList();
        UserInviteRecord userInviteRecord;
        if (dto.getUserId() != null) {
            final UserInfo userInfo = this.userInfoService.getUserInfo(dto.getUserId());
            final UserLevel userLevel = this.userLevelService.getByUserId(dto.getUserId());
            dto.setUserLevel(userLevel.getUserLevel().toLowerCase());
            dto.setUpdateDate(userLevel.getModifyDate());
            if (null != userInfo) {
                dto.setMobile(userInfo.getMobile());
                dto.setEmail(userInfo.getEmail());
                dto.setUserId(userInfo.getId());
                list.add(dto);
                return list;
            }
            return null;
        }
        if (StringUtils.isNotEmpty(dto.getEmail())) {
            final UserInfo userInfo = this.userInfoService.getUserInfo(dto.getEmail());
            if (null != userInfo) {
                final UserLevel userLevel = this.userLevelService.getByUserId(userInfo.getId());
                if (ObjectUtils.allNotNull(userLevel)) {
                    dto.setUserLevel(userLevel.getUserLevel().toLowerCase());
                    dto.setUpdateDate(userLevel.getModifyDate());
                    dto.setMobile(userInfo.getMobile());
                    dto.setEmail(userInfo.getEmail());
                    dto.setUserId(userInfo.getId());
                    list.add(dto);
                }
                return list;
            }
            return null;
        }
        if (StringUtils.isNotEmpty(dto.getMobile())) {
            final UserInfo userInfo = this.userInfoService.getUserInfo(dto.getMobile());
            if (ObjectUtils.anyNotNull(userInfo)) {
                final UserLevel userLevel = this.userLevelService.getByUserId(userInfo.getId());
                if (ObjectUtils.allNotNull(userLevel)) {
                    dto.setUserLevel(userLevel.getUserLevel().toLowerCase());
                    dto.setUpdateDate(userLevel.getModifyDate());
                    dto.setEmail(userInfo.getEmail());
                    dto.setMobile(userInfo.getMobile());
                    dto.setUserId(userInfo.getId());
                    list.add(dto);
                }
                return list;
            }
            return null;
        }

        String level = null;
        if (dto.getUserLevel() != null) {
            level = dto.getUserLevel().toLowerCase();
        }
        final List<UserLevel> userLevelList = this.userLevelService.getListByLevel(level, dto.getLastUserId(), dto.getPageSize());
        if (CollectionUtils.isNotEmpty(userLevelList)) {
            for (final UserLevel userLevel : userLevelList) {
                final UserInfo userinfo = this.userInfoService.getUserInfo(userLevel.getUserId());
                if (ObjectUtils.allNotNull(userLevel, userinfo)) {
                    dto.setUserLevel(userLevel.getUserLevel());
                    dto.setUpdateDate(userLevel.getModifyDate());
                    dto.setUserId(userLevel.getUserId());
                    dto.setEmail(userinfo.getEmail());
                    dto.setMobile(userinfo.getMobile());
                    dto.setUserId(userinfo.getId());
                    list.add(dto);
                }
                dto = UserInviteReqDTO.builder().build();
            }
            return list;
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public UserInviteDTO queryUserInviteInfo(final Long userId, String activityCode, Integer brokerId) {
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (userInfo == null) {
            return null;
        }
        brokerId = brokerId == null ? 1 : brokerId;
        if(brokerId != 1 && brokerId.intValue() != userInfo.getBrokerId().intValue()){
            return null;
        }

        UserActivityConfigDTO userActivityConfigDTO = this.userActivityConfigService.getActivityConfig(activityCode, brokerId);

        if (Objects.isNull(userActivityConfigDTO)) {
            userActivityConfigDTO = this.userActivityConfigService.getActivityConfig(null, brokerId);
        }

        final UserInviteDTO.UserInviteDTOBuilder builder = UserInviteDTO.builder();

        builder.userId(userId)
                .inviteCode(userInfo.getUid())
                .email(userInfo.getEmail())
                .mobile(userInfo.getMobile());

        if (Objects.nonNull(userActivityConfigDTO)) {
            // activityCode = null时查询最新的活动信息
            activityCode = userActivityConfigDTO.getActivityCode();

            builder.currencyId(userActivityConfigDTO.getCurrencyId())
                    .currencyCode(userActivityConfigDTO.getCurrencyCode())
                    .currencyNum(userActivityConfigDTO.getCurrencyNum())
                    .inviteCurrencyId(userActivityConfigDTO.getInviteCurrencyId())
                    .inviteCurrencyNum(userActivityConfigDTO.getInviteCurrencyNum());
        }

        builder.activityUrl(String.format(this.activityUrl, activityCode, userInfo.getUid()));

        return builder.build();
    }

    @Override
    public UserInviteDetailDTO queryUserInviteDetail(final Long userId) {
        final UserInviteDetailDTO dto = UserInviteDetailDTO.builder().userId(userId).build();
        if (userId != null) {
            final UserInviteRecord userInviteRecord = this.getByUserId(userId);
            if (ObjectUtils.allNotNull(userInviteRecord)) {
                final UserInfo userInfo = this.userInfoService.getUserInfo(dto.getUserId());
                final UserLevel userLevel = this.userLevelService.getByUserId(dto.getUserId());
                dto.setEmail(userInfo.getEmail());
                dto.setMobile(userInfo.getMobile());
                dto.setUserName(userInfo.getRealName());
                dto.setUserLevel(UserLevelEnum.getInstance(userLevel.getUserLevel()));

                final Long inviteUserId = userInviteRecord.getInviteUserId();
                final UserLevel inviteUserLevel = this.userLevelService.getByUserId(inviteUserId);
                final UserInfo inviteUserInfo = this.userInfoService.getUserInfo(inviteUserId);
                if (inviteUserLevel == null || inviteUserInfo == null) {
                    return dto;
                }
                dto.setInviteUserId(inviteUserId);
                dto.setInviteEmail(inviteUserInfo.getEmail());
                dto.setInviteMobile(inviteUserInfo.getMobile());
                dto.setInviteUserName(inviteUserInfo.getRealName());
                dto.setInviteUserLevel(UserLevelEnum.getInstance(inviteUserLevel.getUserLevel()));
                dto.setBrokerId(userInviteRecord.getBrokerId());
                return dto;
            }
        }
        return null;
    }

}