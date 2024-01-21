package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.DappsInfoExample;
import cc.newex.dax.extra.domain.cms.DappsInfo;
import org.springframework.stereotype.Repository;

/**
 * dapps应用程序表 数据访问类
 *
 * @author mbg.generated
 * @date 2018-09-12 17:14:02
 */
@Repository
public interface DappsInfoRepository extends CrudRepository<DappsInfo, DappsInfoExample, Long> {
}