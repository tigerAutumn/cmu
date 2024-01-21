package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.market.criteria.CreatorTableExample;
import cc.newex.dax.market.domain.CreatorTable;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface CreatorTableRepository
        extends CrudRepository<CreatorTable, CreatorTableExample, Long> {

    /**
     * 按照marketFrom 初始化 kline market data表
     *
     * @param marketFrom
     * @return
     */
    int createMarketData(@Param("marketFrom") int marketFrom);

    /**
     * 创建market index指数表
     *
     * @param marketFrom
     * @return
     */
    int createMarketIndexRecord(@Param("marketFrom") int marketFrom);

    /**
     * check table is exist
     *
     * @param tableName
     * @return
     */
    int checkTableName(@Param("tableName") int tableName);
}