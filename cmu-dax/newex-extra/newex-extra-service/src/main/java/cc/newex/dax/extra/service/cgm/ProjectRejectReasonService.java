package cc.newex.dax.extra.service.cgm;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.cgm.ProjectRejectReasonExample;
import cc.newex.dax.extra.domain.cgm.ProjectRejectReason;

/**
 * @author newex-team
 * @date 2018/8/13 下午3:20
 */
public interface ProjectRejectReasonService extends CrudService<ProjectRejectReason, ProjectRejectReasonExample, Long> {

    /**
     * 查询project 拒绝原因
     *
     * @param tokenInfoId
     * @return
     */
    ProjectRejectReason getByTokenInfoId(Long tokenInfoId);

}
