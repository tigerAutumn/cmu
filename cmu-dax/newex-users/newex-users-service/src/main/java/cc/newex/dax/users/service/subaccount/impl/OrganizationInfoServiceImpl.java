package cc.newex.dax.users.service.subaccount.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.subaccount.OrganizationInfoExample;
import cc.newex.dax.users.data.subaccount.OrganizationInfoRepository;
import cc.newex.dax.users.domain.UserInfo;
import cc.newex.dax.users.domain.subaccount.OrganizationInfo;
import cc.newex.dax.users.service.membership.UserInfoService;
import cc.newex.dax.users.service.subaccount.OrganizationInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

/**
 * 组织详情表 服务实现
 *
 * @author newex-team
 * @date 2018-10-31 16:16:36
 */
@Slf4j
@Service
public class OrganizationInfoServiceImpl
        extends AbstractCrudService<OrganizationInfoRepository, OrganizationInfo, OrganizationInfoExample, Long>
        implements OrganizationInfoService {

    @Autowired
    private OrganizationInfoRepository organizationInfoRepository;

    @Autowired
    private UserInfoService userInfoService;

    @Override
    protected OrganizationInfoExample getPageExample(final String fieldName, final String keyword) {
        final OrganizationInfoExample example = new OrganizationInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public int add(final OrganizationInfo record) {
        if (Objects.isNull(record)) {
            return 0;
        }

        final Long userId = record.getUserId();
        final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
        if (Objects.isNull(userInfo)) {
            return 0;
        }

        // 已经存在，不能重复添加
        final OrganizationInfo other = this.getByUserId(userId);
        if (Objects.nonNull(other)) {
            return 0;
        }

        final Integer orgType = record.getOrgType();
        if (Objects.isNull(orgType)) {
            return 0;
        }

        final int save = this.organizationInfoRepository.insert(record);
        if (save > 0) {
            userInfo.setType(orgType);

            this.userInfoService.editById(userInfo);
        }

        return save;
    }

    @Override
    public int editById(final OrganizationInfo record) {
        if (Objects.isNull(record)) {
            return 0;
        }

        final OrganizationInfo oldRecord = this.organizationInfoRepository.selectById(record.getId());
        if (Objects.isNull(oldRecord)) {
            return 0;
        }

        final Long userId = record.getUserId();

        // userId不让修改
        if (!oldRecord.getUserId().equals(userId)) {
            return 0;
        }

        final Integer orgType = record.getOrgType();
        if (Objects.isNull(orgType)) {
            return 0;
        }

        final int update = this.organizationInfoRepository.updateById(record);
        if (update > 0) {
            final UserInfo userInfo = this.userInfoService.getUserInfo(userId);
            userInfo.setType(orgType);

            this.userInfoService.editById(userInfo);
        }

        return update;
    }

    @Override
    public OrganizationInfo getByUserId(final Long userId) {
        if (userId == null || userId <= 0) {
            return null;
        }

        final OrganizationInfoExample example = new OrganizationInfoExample();
        example.createCriteria().andUserIdEqualTo(userId);

        return this.organizationInfoRepository.selectOneByExample(example);
    }

}