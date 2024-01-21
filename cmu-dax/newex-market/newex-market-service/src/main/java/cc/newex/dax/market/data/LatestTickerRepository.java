package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.market.criteria.LatestTickerExample;
import cc.newex.dax.market.domain.LatestTicker;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface LatestTickerRepository
    extends CrudRepository<LatestTicker, LatestTickerExample, Long> {

    /**
     * 查询所有的法币行情
     *
     * @return
     */
    List<LatestTicker> selectFiatTickers();

    /**
     * 查询所有的行情
     *
     * @return
     */
    List<LatestTicker> selectAllTickers();
}