package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.CommonPropExample;
import cc.newex.dax.perpetual.domain.CommonProp;
import org.springframework.stereotype.Repository;

/**
 * 公共配置表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:30:57
 */
@Repository
public interface CommonPropRepository extends CrudRepository<CommonProp, CommonPropExample, Long> {
}