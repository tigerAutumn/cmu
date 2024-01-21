package cc.newex.dax.market.data;

import cc.newex.commons.mybatis.data.CrudRepository;
import cc.newex.dax.market.criteria.FetchingDataPathExample;
import cc.newex.dax.market.domain.FetchingDataPath;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 数据访问类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Repository
public interface FetchingDataPathRepository
        extends CrudRepository<FetchingDataPath, FetchingDataPathExample, Long> {

    /**
     * 获取所有的采集路径
     *
     * @return
     */
    List<FetchingDataPath> selectDataPath();
}