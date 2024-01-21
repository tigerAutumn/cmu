package cc.newex.dax.extra.data.cms;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.extra.criteria.cms.VersionInfoExample;
import cc.newex.dax.extra.domain.cms.VersionInfo;
import org.springframework.stereotype.Repository;

/**
 * 版本信息 数据访问类
 *
 * @author liutiejun
 * @date 2018-07-03
 */
@Repository
public interface VersionInfoRepository
        extends CrudRepository<VersionInfo, VersionInfoExample, Integer> {

}