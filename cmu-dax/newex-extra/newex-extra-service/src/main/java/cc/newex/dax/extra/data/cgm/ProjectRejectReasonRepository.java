package cc.newex.dax.extra.data.cgm;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cgm.ProjectRejectReasonExample;
import cc.newex.dax.extra.domain.cgm.ProjectRejectReason;
import org.springframework.stereotype.Repository;

/**
 * 项目拒绝原因表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-08-08 16:21:16
 */
@Repository
public interface ProjectRejectReasonRepository extends CrudRepository<ProjectRejectReason, ProjectRejectReasonExample, Long> {
}