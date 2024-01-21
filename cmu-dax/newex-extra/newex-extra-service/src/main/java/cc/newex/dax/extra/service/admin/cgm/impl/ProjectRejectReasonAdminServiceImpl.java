package cc.newex.dax.extra.service.admin.cgm.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cgm.ProjectRejectReasonExample;
import cc.newex.dax.extra.data.cgm.ProjectRejectReasonRepository;
import cc.newex.dax.extra.data.cgm.ProjectTokenInfoRepository;
import cc.newex.dax.extra.domain.cgm.ProjectRejectReason;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import cc.newex.dax.extra.enums.cgm.ProjectStatusEnum;
import cc.newex.dax.extra.service.admin.cgm.ProjectRejectReasonAdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Objects;

/**
 * 项目拒绝原因表 服务实现
 *
 * @author mbg.generated
 * @date 2018-08-08 16:21:16
 */
@Slf4j
@Service
public class ProjectRejectReasonAdminServiceImpl
        extends AbstractCrudService<ProjectRejectReasonRepository, ProjectRejectReason, ProjectRejectReasonExample, Long>
        implements ProjectRejectReasonAdminService {

    @Autowired
    private ProjectRejectReasonRepository projectRejectReasonRepos;

    @Autowired
    private ProjectTokenInfoRepository projectTokenInfoRepos;

    @Override
    protected ProjectRejectReasonExample getPageExample(final String fieldName, final String keyword) {
        final ProjectRejectReasonExample example = new ProjectRejectReasonExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }

    @Override
    public int add(final ProjectRejectReason projectRejectReason) {
        final int save = this.projectRejectReasonRepos.insert(projectRejectReason);

        if (save > 0) {
            // 更新项目的状态
            final ProjectTokenInfo projectTokenInfo = ProjectTokenInfo.builder()
                    .id(projectRejectReason.getTokenInfoId())
                    .status(ProjectStatusEnum.REJECT.getCode().byteValue())
                    .updatedDate(new Date())
                    .build();

            this.projectTokenInfoRepos.updateById(projectTokenInfo);
        }

        return save;
    }

    @Override
    public ProjectRejectReason getByTokenInfoId(final Long tokenInfoId) {
        if (Objects.isNull(tokenInfoId)) {
            return null;
        }

        final ProjectRejectReasonExample example = new ProjectRejectReasonExample();
        example.createCriteria().andTokenInfoIdEqualTo(tokenInfoId);

        return this.projectRejectReasonRepos.selectOneByExample(example);
    }

}