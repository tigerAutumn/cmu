package cc.newex.dax.perpetual.data;

import org.springframework.stereotype.Repository;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.FeeExample;
import cc.newex.dax.perpetual.domain.Fee;

/**
 * 平台手续费配置表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-01 09:30:37
 */
@Repository
public interface FeeRepository extends CrudRepository<Fee, FeeExample, Long> {
}
