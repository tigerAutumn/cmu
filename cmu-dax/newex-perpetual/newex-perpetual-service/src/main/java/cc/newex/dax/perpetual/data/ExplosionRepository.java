package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.ExplosionExample;
import cc.newex.dax.perpetual.domain.Explosion;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 爆仓 数据访问类
 *
 * @author newex-team
 * @date 2018-11-02 11:19:54
 */
@Repository
public interface ExplosionRepository extends CrudRepository<Explosion, ExplosionExample, Long> {

    /**
     * 以 id 为游标，分页获取爆仓数据
     * @param id
     * @param size
     * @param contractCode
     * @return
     */
    List<Explosion> listExplosion(@Param("id") long id, @Param("size") int size, @Param("contractCode") String contractCode);

    /**
     * 批量插入，过滤重复数据
     *
     * @param list
     * @return
     */
    int batchInsertOnDuplicateKeyDoNothing(@Param("records") List<Explosion> list);
}