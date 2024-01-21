package cc.newex.dax.extra.service.cgm.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cgm.ProjectTokenInfoExample;
import cc.newex.dax.extra.data.cgm.ProjectTokenInfoRepository;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.enums.cgm.ProjectStatusEnum;
import cc.newex.dax.extra.enums.cgm.SweetsStatusEnum;
import cc.newex.dax.extra.service.cgm.ProjectTokenInfoService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

/**
 * @author newex-team
 * @date 2018/8/13 下午3:09
 */
@Service
public class ProjectTokenInfoServiceImpl
        extends AbstractCrudService<ProjectTokenInfoRepository, ProjectTokenInfo, ProjectTokenInfoExample, Long>
        implements ProjectTokenInfoService {

    @Autowired
    private ProjectTokenInfoRepository projectTokenInfoRepository;

    @Override
    protected ProjectTokenInfoExample getPageExample(final String fieldName, final String keyword) {
        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public List<ProjectTokenInfo> listProjectByUserId(final Long userId, final Integer brokerId) {
        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        final ProjectTokenInfoExample.Criteria criteria = example.createCriteria();
        criteria.andUserIdEqualTo(userId);
        if (Objects.nonNull(brokerId)) {
            criteria.andBrokerIdEqualTo(brokerId);
        }
        return this.projectTokenInfoRepository.selectByExample(example);
    }

    @Override
    public int updateSweetsStatus(final Long tokenInfoId, final Long userId) {
        final ProjectTokenInfo projectTokenInfo = ProjectTokenInfo.builder()
                .sweetsStatus(SweetsStatusEnum.PAID.getCode().byteValue())
                .build();

        final ProjectTokenInfoExample projectTokenInfoExample = new ProjectTokenInfoExample();
        final ProjectTokenInfoExample.Criteria criteria = projectTokenInfoExample.createCriteria();
        criteria.andIdEqualTo(tokenInfoId);
        criteria.andUserIdEqualTo(userId);

        return this.projectTokenInfoRepository.updateByExample(projectTokenInfo, projectTokenInfoExample);
    }

    @Override
    public List<ProjectTokenInfo> listProjectsByBrokerId(final Integer brokerId) {
        final ProjectTokenInfoExample projectTokenInfoExample = new ProjectTokenInfoExample();
        final ProjectTokenInfoExample.Criteria criteria = projectTokenInfoExample.createCriteria();
        //待上线
        criteria.andStatusEqualTo(ProjectStatusEnum.WAIT.getCode().byteValue());
        if (Objects.nonNull(brokerId)) {
            criteria.andBrokerIdEqualTo(brokerId);
        }

        return this.projectTokenInfoRepository.selectByExample(projectTokenInfoExample);
    }

    @Override
    public ProjectTokenInfo getByToken(final String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        example.createCriteria().andTokenEqualTo(token);

        return this.projectTokenInfoRepository.selectOneByExample(example);
    }

    @Override
    public ProjectTokenInfo getByTokenAndNotStatus(final String token, final ProjectStatusEnum projectStatusEnum) {
        if (StringUtils.isBlank(token) || Objects.isNull(projectStatusEnum)) {
            return null;
        }

        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        example.createCriteria().andTokenEqualTo(token).andStatusNotEqualTo(projectStatusEnum.getCode().byteValue());

        return this.projectTokenInfoRepository.selectOneByExample(example);
    }

    @Override
    public ProjectTokenInfo getByTokenSymbol(final String tokenSymbol) {
        if (StringUtils.isBlank(tokenSymbol)) {
            return null;
        }

        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        example.createCriteria().andTokenSymbolEqualTo(tokenSymbol);

        return this.projectTokenInfoRepository.selectOneByExample(example);
    }

    @Override
    public ProjectTokenInfo getByTokenSymbolAndNotStatus(final String tokenSymbol, final ProjectStatusEnum projectStatusEnum) {
        if (StringUtils.isBlank(tokenSymbol) || Objects.isNull(projectStatusEnum)) {
            return null;
        }

        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        example.createCriteria().andTokenSymbolEqualTo(tokenSymbol).andStatusNotEqualTo(projectStatusEnum.getCode().byteValue());

        return this.projectTokenInfoRepository.selectOneByExample(example);
    }

}
