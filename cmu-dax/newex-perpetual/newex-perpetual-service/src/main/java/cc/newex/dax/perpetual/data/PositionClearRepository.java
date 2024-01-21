package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.PositionClearExample;
import cc.newex.dax.perpetual.domain.PositionClear;
import org.springframework.stereotype.Repository;

/**
 * 用户持仓清算流水表 数据访问类
 *
 * @author newex-team
 * @date 2018-12-12 13:12:15
 */
@Repository
public interface PositionClearRepository extends CrudRepository<PositionClear, PositionClearExample, Long> {
}