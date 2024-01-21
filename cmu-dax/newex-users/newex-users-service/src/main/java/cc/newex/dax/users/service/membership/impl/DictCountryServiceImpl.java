package cc.newex.dax.users.service.membership.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.users.criteria.DictCountryExample;
import cc.newex.dax.users.data.DictCountryRepository;
import cc.newex.dax.users.domain.DictCountry;
import cc.newex.dax.users.dto.admin.DictCountryReqDTO;
import cc.newex.dax.users.service.cache.LocalCacheService;
import cc.newex.dax.users.service.membership.DictCountryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 全球国家(地区)表 服务实现
 *
 * @author newex-team
 * @date 2018-04-08
 */
@Slf4j
@Service
public class DictCountryServiceImpl
        extends AbstractCrudService<DictCountryRepository, DictCountry, DictCountryExample, Integer>
        implements DictCountryService {

    @Resource
    private LocalCacheService memoryCacheService;

    @Override
    protected DictCountryExample getPageExample(final String fieldName, final String keyword) {
        final DictCountryExample example = new DictCountryExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<DictCountry> getAllByLocale(final String locale) {
        final List<DictCountry> cache = this.memoryCacheService.getAllCountries();
        if (CollectionUtils.isEmpty(cache)) {
            return this.dao.selectCountriesByLocale(locale);
        }
        return cache.stream()
                .filter(x -> StringUtils.equalsIgnoreCase(x.getLocale(), locale))
                .collect(Collectors.toList());
    }

    /**
     * 分页查询列表
     *
     * @param pageInfo
     * @param dictCountryReqDTO
     * @return
     */
    @Override
    public List<DictCountry> listByPage(final PageInfo pageInfo, final DictCountryReqDTO dictCountryReqDTO) {
        final DictCountryExample dictCountryExample = new DictCountryExample();
        final DictCountryExample.Criteria criteria = dictCountryExample.createCriteria();
        if (StringUtils.isNotEmpty(dictCountryReqDTO.getLocale())) {
            criteria.andLocaleEqualTo(dictCountryReqDTO.getLocale());
        }
        if (StringUtils.isNotEmpty(dictCountryReqDTO.getName())) {
            criteria.andNameLike(dictCountryReqDTO.getName() + "%");
        }
        if (Objects.nonNull(dictCountryReqDTO.getStatus())) {
            criteria.andStatusEqualTo(dictCountryReqDTO.getStatus());
        }
        return this.getByPage(pageInfo, dictCountryExample);
    }

    @Override
    public List<DictCountry> limitCountries(final String locale) {
        if (StringUtils.isBlank(locale)) {
            return null;
        }

        final List<DictCountry> list = this.memoryCacheService.getLimitCacheCountries();
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }

        List<DictCountry> dictCountryList = list.stream()
                .filter(x -> StringUtils.equalsIgnoreCase(x.getLocale(), locale))
                .collect(Collectors.toList());

        if (CollectionUtils.isEmpty(dictCountryList)) {
            //如果不支持当前国家语言则默认为英文
            dictCountryList = list.stream()
                    .filter(x -> StringUtils.equalsIgnoreCase(x.getLocale(), Locale.US.toLanguageTag().toLowerCase()))
                    .collect(Collectors.toList());
        }

        return dictCountryList;
    }
}