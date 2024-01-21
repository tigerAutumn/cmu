package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.PreLiquidateAlertExample;
import cc.newex.dax.perpetual.domain.PreLiquidateAlert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 风险告警表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-14 12:09:15
 */
@Repository
public interface PreLiquidateAlertRepository extends CrudRepository<PreLiquidateAlert, PreLiquidateAlertExample, Long> {

    /**
     * 批量插入，过滤重复数据
     *
     * @param list
     * @return
     */
    int batchInsertOnDuplicateKeyDoNothing(@Param("records") List<PreLiquidateAlert> list);
}