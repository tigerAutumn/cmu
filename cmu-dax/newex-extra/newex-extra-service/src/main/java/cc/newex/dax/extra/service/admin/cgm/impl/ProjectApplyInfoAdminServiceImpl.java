package cc.newex.dax.extra.service.admin.cgm.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cgm.ProjectApplyInfoExample;
import cc.newex.dax.extra.data.cgm.ProjectApplyInfoRepository;
import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import cc.newex.dax.extra.dto.cgm.ProjectApplyInfoDTO;
import cc.newex.dax.extra.service.admin.cgm.ProjectApplyInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * 项目信息表 服务实现
 *
 * @author mbg.generated
 * @date 2018-08-08 16:21:11
 */
@Slf4j
@Service
public class ProjectApplyInfoAdminServiceImpl
        extends AbstractCrudService<ProjectApplyInfoRepository, ProjectApplyInfo, ProjectApplyInfoExample, Long>
        implements ProjectApplyInfoAdminService {

    @Autowired
    private ProjectApplyInfoRepository projectApplyInfoRepos;

    @Override
    protected ProjectApplyInfoExample getPageExample(final String fieldName, final String keyword) {
        final ProjectApplyInfoExample example = new ProjectApplyInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public DataGridPagerResult<ProjectApplyInfoDTO> getProjectApplyPageInfo(final DataGridPager<ProjectApplyInfoDTO> pager) {

        final PageInfo pageInfo = pager.toPageInfo();

        final ModelMapper mapper = new ModelMapper();
        final ProjectApplyInfo projectApplyInfo = mapper.map(pager.getQueryParameter(), ProjectApplyInfo.class);

        final ProjectApplyInfoExample example = this.buildProjectApplyInfoExample(projectApplyInfo);

        final List<ProjectApplyInfo> projectApplyInfos = this.getByPage(pageInfo, example);

        final List<ProjectApplyInfoDTO> result = mapper.map(
                projectApplyInfos, new TypeToken<List<ProjectApplyInfoDTO>>() {
                }.getType()
        );
        return new DataGridPagerResult<>(pageInfo.getTotals(), result);
    }

    private ProjectApplyInfoExample buildProjectApplyInfoExample(final ProjectApplyInfo projectApplyInfo) {
        final ProjectApplyInfoExample example = new ProjectApplyInfoExample();
        final ProjectApplyInfoExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(projectApplyInfo.getId())) {
            criteria.andIdEqualTo(projectApplyInfo.getId());
        }

        if (Objects.nonNull(projectApplyInfo.getTokenInfoId())) {
            criteria.andTokenInfoIdEqualTo(projectApplyInfo.getTokenInfoId());
        }
        if (StringUtils.isNotBlank(projectApplyInfo.getLocale())) {
            criteria.andLocaleEqualTo(projectApplyInfo.getLocale());
        }
        if (StringUtils.isNotBlank(projectApplyInfo.getCompany())) {
            criteria.andCompanyLike(projectApplyInfo.getCompany());
        }
        if (StringUtils.isNotBlank(projectApplyInfo.getCompanyPosition())) {
            criteria.andCompanyPositionLike(projectApplyInfo.getCompanyPosition());
        }
        if (StringUtils.isNotBlank(projectApplyInfo.getWebsite())) {
            criteria.andWebsiteLike(projectApplyInfo.getWebsite());
        }
        if (Objects.nonNull(projectApplyInfo.getBrokerId())) {
            criteria.andBrokerIdEqualTo(projectApplyInfo.getBrokerId());
        }
        return example;
    }

    @Override
    public ProjectApplyInfo getByTokenInfoId(final Long tokenInfoId) {
        if (Objects.isNull(tokenInfoId)) {
            return null;
        }

        final ProjectApplyInfoExample example = new ProjectApplyInfoExample();
        example.createCriteria().andTokenInfoIdEqualTo(tokenInfoId);

        final List<ProjectApplyInfo> projectApplyInfoList = this.projectApplyInfoRepos.selectByExample(example);
        if (CollectionUtils.isEmpty(projectApplyInfoList)) {
            return null;
        }

        return projectApplyInfoList.get(0);
    }

}