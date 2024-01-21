package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.market.criteria.MarketPropertiesExample;
import cc.newex.dax.market.domain.MarketProperties;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface MarketPropertiesRepository
        extends CrudRepository<MarketProperties, MarketPropertiesExample, Long> {

    /**
     * 获取所有的配置
     *
     * @return
     */
    List<MarketProperties> selectAll();

}