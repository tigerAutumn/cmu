package cc.newex.dax.extra.data.cgm;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cgm.ProjectApplyInfoExample;
import cc.newex.dax.extra.domain.cgm.ProjectApplyInfo;
import org.springframework.stereotype.Repository;

/**
 * 项目信息表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-08-08 16:21:11
 */
@Repository
public interface ProjectApplyInfoRepository extends CrudRepository<ProjectApplyInfo, ProjectApplyInfoExample, Long> {
}