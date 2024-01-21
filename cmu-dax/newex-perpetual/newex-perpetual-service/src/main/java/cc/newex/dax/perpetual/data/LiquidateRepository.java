package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.LiquidateExample;
import cc.newex.dax.perpetual.domain.Liquidate;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 强平表 数据访问类
 *
 * @author newex-team
 * @date 2018-11-02 11:16:40
 */
@Repository
public interface LiquidateRepository extends CrudRepository<Liquidate, LiquidateExample, Long> {

    /**
     * 获取强平数据
     *
     * @param id
     * @param size
     * @param contractCode
     * @return
     */
    List<Liquidate> listLiquidate(@Param("id") long id, @Param("size") int size, @Param("contractCode") String contractCode);

    /**
     * 批量插入，过滤重复数据
     *
     * @param list
     * @return
     */
    int batchInsertOnDuplicateKeyDoNothing(@Param("records") List<Liquidate> list);
}