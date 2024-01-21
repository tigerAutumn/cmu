package cc.newex.dax.users.service.cache.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.DictCountryExample;
import cc.newex.dax.users.data.DictCountryRepository;
import cc.newex.dax.users.domain.DictCountry;
import cc.newex.dax.users.service.cache.LocalCacheService;
import com.netflix.loadbalancer.PredicateKey;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;

/**
 * @author newex-team
 * @date 2018-06-04
 */
@Slf4j
@Service
public class LocalMemoryCacheServiceImpl
        extends AbstractCrudService<DictCountryRepository, DictCountry, DictCountryExample, Integer>
        implements LocalCacheService {
    private static List<DictCountry> dictCountryCache;

    private static List<DictCountry> dictLimitCountryCache;
    @Resource
    private DictCountryRepository dictCountryRepository;

    @PostConstruct
    @Override
    public void loadDataToMemoryCache() {
        dictCountryCache = this.dictCountryRepository.selectCountriesWithMainField();
        dictLimitCountryCache = this.selectLimitCountries();
    }

    @Override
    public List<DictCountry> getAllCountries() {
        if (CollectionUtils.isEmpty(dictCountryCache)) {
            dictCountryCache = this.dictCountryRepository.selectCountriesWithMainField();
        }
        return dictCountryCache;
    }

    /**
     * 获取受限制的国家列表
     *
     * @return
     */
    @Override
    public List<DictCountry> getLimitCacheCountries() {
        if (CollectionUtils.isEmpty(dictLimitCountryCache)) {
            dictLimitCountryCache = this.selectLimitCountries();
        }
        return dictLimitCountryCache;
    }


    @Override
    protected DictCountryExample getPageExample(String s, String s1) {
        return null;
    }

    /**
     * 获取受限制的国家
     *
     * @return
     */
    private List<DictCountry> selectLimitCountries() {
        DictCountryExample dictCountryExample = new DictCountryExample();
        DictCountryExample.Criteria criteria = dictCountryExample.createCriteria();
        //受限制列表 0
        criteria.andStatusEqualTo(0);

        return dictCountryRepository.selectByExample(dictCountryExample);
    }

}
