package cc.newex.dax.extra.service.admin.cgm.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.commons.support.model.DataGridPager;
import cc.newex.commons.support.model.DataGridPagerResult;
import cc.newex.dax.extra.criteria.cgm.ProjectTokenInfoExample;
import cc.newex.dax.extra.data.cgm.ProjectTokenInfoRepository;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.dto.cgm.ProjectTokenInfoDTO;
import cc.newex.dax.extra.enums.cgm.DepositStatusEnum;
import cc.newex.dax.extra.enums.cgm.ProjectStatusEnum;
import cc.newex.dax.extra.enums.cgm.SweetsStatusEnum;
import cc.newex.dax.extra.service.admin.cgm.ProjectTokenInfoAdminService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * 项目基础信息表 服务实现
 *
 * @author mbg.generated
 * @date 2018-08-08 16:21:20
 */
@Slf4j
@Service
public class ProjectTokenInfoAdminServiceImpl
        extends AbstractCrudService<ProjectTokenInfoRepository, ProjectTokenInfo, ProjectTokenInfoExample, Long>
        implements ProjectTokenInfoAdminService {

    @Autowired
    private ProjectTokenInfoRepository projectTokenInfoRepos;

    @Override
    protected ProjectTokenInfoExample getPageExample(final String fieldName, final String keyword) {
        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public DataGridPagerResult<ProjectTokenInfoDTO> getProjectTokenPageInfo(final DataGridPager<ProjectTokenInfoDTO> pager) {
        final PageInfo pageInfo = pager.toPageInfo();

        // 按照更新时间降序排列
        pageInfo.setSortItem("updated_date");
        pageInfo.setSortType(PageInfo.SORT_TYPE_DES);

        final ModelMapper mapper = new ModelMapper();
        final ProjectTokenInfo projectTokenInfo = mapper.map(pager.getQueryParameter(), ProjectTokenInfo.class);
        final ProjectTokenInfoExample example = buildProjectTokenInfoExample(projectTokenInfo);

        final List<ProjectTokenInfo> projectTokenInfos = this.getByPage(pageInfo, example);
        final List<ProjectTokenInfoDTO> projectTokenInfoDTOS = mapper.map(
                projectTokenInfos, new TypeToken<List<ProjectTokenInfoDTO>>() {
                }.getType()
        );
        return new DataGridPagerResult<>(pageInfo.getTotals(), projectTokenInfoDTOS);
    }

    private ProjectTokenInfoExample buildProjectTokenInfoExample(ProjectTokenInfo projectTokenInfo) {
        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        final ProjectTokenInfoExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(projectTokenInfo.getId())) {
            criteria.andIdEqualTo(projectTokenInfo.getId());
        }
        if (Objects.nonNull(projectTokenInfo.getUserId())) {
            criteria.andUserIdEqualTo(projectTokenInfo.getUserId());
        }
        if (StringUtils.isNotBlank(projectTokenInfo.getName())) {
            criteria.andNameLike(projectTokenInfo.getName());
        }
        if (StringUtils.isNotBlank(projectTokenInfo.getMobile())) {
            criteria.andMobileLike(projectTokenInfo.getMobile());
        }
        if (StringUtils.isNotBlank(projectTokenInfo.getEmail())) {
            criteria.andEmailLike(projectTokenInfo.getEmail());
        }
        if (StringUtils.isNotBlank(projectTokenInfo.getToken())) {
            criteria.andTokenLike(projectTokenInfo.getToken());
        }
        if (StringUtils.isNotBlank(projectTokenInfo.getTokenSymbol())) {
            criteria.andTokenSymbolLike(projectTokenInfo.getTokenSymbol());
        }
        if (Objects.nonNull(projectTokenInfo.getStatus())) {
            criteria.andStatusEqualTo(projectTokenInfo.getStatus());
        }
        if (StringUtils.isNotBlank(projectTokenInfo.getContacts())) {
            criteria.andContactsLike(projectTokenInfo.getContacts());
        }
        if (StringUtils.isNotBlank(projectTokenInfo.getWechat())) {
            criteria.andWechatLike(projectTokenInfo.getWechat());
        }
        if (Objects.nonNull(projectTokenInfo.getBrokerId())) {
            criteria.andBrokerIdEqualTo(projectTokenInfo.getBrokerId());
        }
        return example;
    }

    @Override
    public int pass(final ProjectTokenInfoDTO projectTokenInfoDTO) {
        final ProjectTokenInfo projectTokenInfo = ProjectTokenInfo.builder()
                .id(projectTokenInfoDTO.getId())
                .deposit(projectTokenInfoDTO.getDeposit())
                .tokenNumber(projectTokenInfoDTO.getTokenNumber())
                .tokenUrl(projectTokenInfoDTO.getTokenUrl())
                .contacts(projectTokenInfoDTO.getContacts())
                .wechat(projectTokenInfoDTO.getWechat())
                .status(ProjectStatusEnum.PASS.getCode().byteValue())
                .updatedDate(new Date())
                .build();

        return this.projectTokenInfoRepos.updateById(projectTokenInfo);
    }

    @Override
    public int schedule(final ProjectTokenInfoDTO projectTokenInfoDTO) {
        final ProjectTokenInfo projectTokenInfo = ProjectTokenInfo.builder()
                .id(projectTokenInfoDTO.getId())
                .onlineTime(projectTokenInfoDTO.getOnlineTime())
                .status(ProjectStatusEnum.WAIT.getCode().byteValue())
                .updatedDate(new Date())
                .build();

        return this.projectTokenInfoRepos.updateById(projectTokenInfo);
    }

    @Override
    public int online(final ProjectTokenInfoDTO projectTokenInfoDTO) {
        final ProjectTokenInfo projectTokenInfo = ProjectTokenInfo.builder()
                .id(projectTokenInfoDTO.getId())
                .status(ProjectStatusEnum.ONLINE.getCode().byteValue())
                .updatedDate(new Date())
                .build();

        return this.projectTokenInfoRepos.updateById(projectTokenInfo);
    }

    @Override
    public int updateDepositStatus(final Long tokenInfoId, Byte depositStatus) {
        if (Objects.isNull(tokenInfoId)) {
            return 0;
        }

        if (Objects.isNull(depositStatus)) {
            depositStatus = DepositStatusEnum.NO_PAID.getCode().byteValue();
        }

        final ProjectTokenInfo projectTokenInfo = ProjectTokenInfo.builder()
                .id(tokenInfoId)
                .depositStatus(depositStatus)
                .build();

        return this.projectTokenInfoRepos.updateById(projectTokenInfo);
    }

    @Override
    public int updateDepositStatus(final Long tokenInfoId, final DepositStatusEnum depositStatus) {
        return this.updateDepositStatus(tokenInfoId, depositStatus.getCode().byteValue());
    }

    @Override
    public int updateSweetsStatus(final Long tokenInfoId, Byte sweetsStatus) {
        if (Objects.isNull(tokenInfoId)) {
            return 0;
        }

        if (Objects.isNull(sweetsStatus)) {
            sweetsStatus = SweetsStatusEnum.NO_PAID.getCode().byteValue();
        }

        final ProjectTokenInfo projectTokenInfo = ProjectTokenInfo.builder()
                .id(tokenInfoId)
                .sweetsStatus(sweetsStatus)
                .build();

        return this.projectTokenInfoRepos.updateById(projectTokenInfo);
    }

    @Override
    public int updateSweetsStatus(final Long tokenInfoId, final SweetsStatusEnum sweetsStatus) {
        return this.updateSweetsStatus(tokenInfoId, sweetsStatus.getCode().byteValue());
    }

    @Override
    public ProjectTokenInfo getByToken(final String token) {
        if (StringUtils.isBlank(token)) {
            return null;
        }

        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        example.createCriteria().andTokenEqualTo(token);

        return this.projectTokenInfoRepos.selectOneByExample(example);
    }

    @Override
    public ProjectTokenInfo getByTokenSymbol(final String tokenSymbol) {
        if (StringUtils.isBlank(tokenSymbol)) {
            return null;
        }

        final ProjectTokenInfoExample example = new ProjectTokenInfoExample();
        example.createCriteria().andTokenSymbolEqualTo(tokenSymbol);

        return this.projectTokenInfoRepos.selectOneByExample(example);
    }

}