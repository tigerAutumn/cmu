package cc.newex.dax.users.service.cache;

import cc.newex.dax.users.domain.DictCountry;

import java.util.List;

/**
 * @author newex-team
 * @date 2018-06-04
 */
public interface LocalCacheService {

    void loadDataToMemoryCache();

    List<DictCountry> getAllCountries();

    /**
     * 获取受限制的国家列表
     *
     * @return
     */
    List<DictCountry> getLimitCacheCountries();

}
