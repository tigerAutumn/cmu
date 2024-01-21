package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.HistoryExplosionExample;
import cc.newex.dax.perpetual.domain.HistoryExplosion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 爆仓流水表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-02 11:19:38
 */
@Repository
public interface HistoryExplosionRepository extends CrudRepository<HistoryExplosion, HistoryExplosionExample, Long> {

    void batchInsertWithId(@Param("list") List<HistoryExplosion> list);
}