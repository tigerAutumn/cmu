package cc.newex.dax.extra.service.cgm.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.extra.criteria.cgm.ProjectApplyInfoExample;
import cc.newex.dax.extra.data.cgm.ProjectApplyInfoRepository;
import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import cc.newex.dax.extra.service.cgm.ProjectApplyInfoService;
import org.springframework.stereotype.Service;

/**
 * @author newex-team
 * @date 2018/8/13 下午3:19
 */
@Service
public class ProjectApplyInfoServiceImpl
        extends AbstractCrudService<ProjectApplyInfoRepository, ProjectApplyInfo, ProjectApplyInfoExample, Long>
        implements ProjectApplyInfoService {

    @Override
    protected ProjectApplyInfoExample getPageExample(final String fieldName, final String keyword) {
        final ProjectApplyInfoExample example = new ProjectApplyInfoExample();
        example.createCriteria().andFieldLike(fieldName, keyword);

        return example;
    }
}
