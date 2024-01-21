package cc.newex.dax.market.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.market.criteria.FetchingDataPathExample;
import cc.newex.dax.market.domain.FetchingDataPath;

import java.util.List;

/**
 * 服务接口
 *
 * @author newex-team
 * @date 2018/03/18
 */

public interface FetchingDataPathService
        extends CrudService<FetchingDataPath, FetchingDataPathExample, Long> {

    List<FetchingDataPath> selectDataPath();

    /**
     * 放入 fetchingDataPath redis 缓存
     *
     * @param
     */
    void putPathRedis();

    /**
     * 放入所有 fetchingDataPath redis 缓存
     *
     * @param
     */
    void putAllPathRedis();

    /**
     * 根据marketFrom从缓存中获取fetchingDataPath
     *
     * @param marketFrom
     */
    FetchingDataPath getPathRedis(Integer marketFrom);

    /**
     * 获取缓存的fetchingDataPath
     *
     * @return
     */
    List<FetchingDataPath> getAllPathRedis();
}
