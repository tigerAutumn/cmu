package cc.newex.dax.extra.service.cgm.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cgm.ProjectRejectReasonExample;
import cc.newex.dax.extra.data.cgm.ProjectRejectReasonRepository;
import cc.newex.dax.extra.domain.cgm.ProjectRejectReason;
import cc.newex.dax.extra.service.cgm.ProjectRejectReasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author newex-team
 * @date 2018/8/13 下午3:21
 */
@Service
public class ProjectRejectReasonServiceImpl
        extends AbstractCrudService<ProjectRejectReasonRepository, ProjectRejectReason, ProjectRejectReasonExample, Long>
        implements ProjectRejectReasonService {

    @Autowired
    private ProjectRejectReasonRepository projectRejectReasonRepos;


    @Override
    protected ProjectRejectReasonExample getPageExample(final String fieldName, final String keyword) {
        final ProjectRejectReasonExample example = new ProjectRejectReasonExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
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
