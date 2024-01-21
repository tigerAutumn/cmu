package cc.newex.dax.extra.service.admin.cgm;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.extra.criteria.cgm.ProjectRejectReasonExample;
import cc.newex.dax.extra.domain.cgm.ProjectRejectReason;

/**
 * 项目拒绝原因表 服务接口
 *
 * @author mbg.generated
 * @date 2018-08-08 16:21:16
 */
public interface ProjectRejectReasonAdminService extends CrudService<ProjectRejectReason, ProjectRejectReasonExample, Long> {

    ProjectRejectReason getByTokenInfoId(Long tokenInfoId);

}