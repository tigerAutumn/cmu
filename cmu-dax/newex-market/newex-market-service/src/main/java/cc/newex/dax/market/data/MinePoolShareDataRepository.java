package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.market.criteria.MinePoolShareDataExample;
import cc.newex.dax.market.domain.MinePoolShareData;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface MinePoolShareDataRepository
        extends CrudRepository<MinePoolShareData, MinePoolShareDataExample, Long> {
    /**
     * 获取矿池数据
     *
     * @return
     */
    List<MinePoolShareData> selectAll();
}