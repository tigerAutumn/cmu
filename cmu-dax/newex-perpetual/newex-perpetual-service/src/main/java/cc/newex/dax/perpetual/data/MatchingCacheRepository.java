package cc.newex.dax.perpetual.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.perpetual.criteria.MatchingCacheExample;
import cc.newex.dax.perpetual.domain.MatchingCache;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 撮合缓存表 数据访问类
 *
 * @author newex-team
 * @date 2018-12-26 14:27:56
 */
@Repository
public interface MatchingCacheRepository extends CrudRepository<MatchingCache, MatchingCacheExample, Long> {

    List<String> selectContractCode();

    List<MatchingCache> selectBatchForUpdate(@Param("contractCode") String contractCode);
}