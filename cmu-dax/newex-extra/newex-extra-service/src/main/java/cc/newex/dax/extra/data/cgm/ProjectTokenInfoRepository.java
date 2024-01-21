package cc.newex.dax.extra.data.cgm;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cgm.ProjectTokenInfoExample;
import cc.newex.dax.extra.domain.cgm.ProjectTokenInfo;
import org.springframework.stereotype.Repository;

/**
 * 项目基础信息表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-08-08 16:21:20
 */
@Repository
public interface ProjectTokenInfoRepository extends CrudRepository<ProjectTokenInfo, ProjectTokenInfoExample, Long> {
}