package cc.newex.dax.perpetual.service;

import cc.newex.commons.mybatis.service.CrudService;
import cc.newex.dax.perpetual.criteria.MatchingCacheExample;
import cc.newex.dax.perpetual.domain.MatchingCache;

import java.util.List;

/**
 * 撮合缓存表 服务接口
 *
 * @author newex-team
 * @date 2018-12-26 14:27:56
 */
public interface MatchingCacheService extends CrudService<MatchingCache, MatchingCacheExample, Long> {

    List<String> getContractCode();

    List<MatchingCache> getBatch(final String contractCode);

    List<MatchingCache> getBatchForUpdate(String contractCode);
}