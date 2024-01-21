package cc.newex.dax.integration.service.conf.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.conf.ProviderConfExample;
import cc.newex.dax.integration.data.conf.ProviderConfRepository;
import cc.newex.dax.integration.domain.conf.ProviderConf;
import cc.newex.dax.integration.service.conf.ProviderConfService;
import cc.newex.dax.integration.service.conf.enums.RegionEnum;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.MutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 服务实现
 *
 * @author newex-team
 * @date 2018-06-02
 */
@Slf4j
@Service
public class ProviderConfServiceImpl
        extends AbstractCrudService<ProviderConfRepository, ProviderConf, ProviderConfExample, Integer>
        implements ProviderConfService {

    @Autowired
    private ProviderConfRepository providerConfRepository;

    @Override
    protected ProviderConfExample getPageExample(final String fieldName, final String keyword) {
        final ProviderConfExample example = new ProviderConfExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public List<ProviderConf> getAllMessageProviderConf() {
        final List<ProviderConf> providerConfList = this.getAll();

        if (CollectionUtils.isEmpty(providerConfList)) {
            return Lists.newArrayListWithCapacity(0);
        }

        return providerConfList.stream()
                .filter(x -> x.getStatus() == 1)
                .collect(Collectors.toList());
    }

    @Override
    public List<ProviderConf> getByType(final String type, final String region) {
        if (StringUtils.isBlank(type)) {
            return null;
        }

        final ProviderConfExample example = new ProviderConfExample();
        final ProviderConfExample.Criteria criteria = example.createCriteria();

        criteria.andTypeEqualTo(type);

        if (StringUtils.isNotBlank(region)) {
            criteria.andRegionEqualTo(region);
        }

        criteria.andStatusEqualTo(1);

        return this.providerConfRepository.selectByExample(example);
    }

    @Override
    public Map<String, ProviderConf> getProviderConfMap() {
        final List<ProviderConf> providerConfList = this.getAll();

        if (CollectionUtils.isEmpty(providerConfList)) {
            return Maps.newHashMap();
        }

        return providerConfList.stream()
                .filter(x -> x.getStatus() == 1)
                .collect(Collectors.toMap(ProviderConf::getName, Function.identity(), (k1, k2) -> k2, HashMap::new));
    }

    @Override
    public <Provider> Map<String, List<Pair<Provider, Double>>> newRegionProviderMap() {
        final Map<String, List<Pair<Provider, Double>>> map = new HashMap<>(RegionEnum.values().length);
        for (final RegionEnum e : RegionEnum.values()) {
            if (!StringUtils.equalsIgnoreCase(e.getName(), RegionEnum.ALL.getName())) {
                map.put(e.getName(), Lists.newArrayListWithCapacity(4));
            }
        }
        return map;
    }

    @Override
    public <Provider> void putRegionProviderMap(final Provider provider, final ProviderConf conf,
                                                final Map<String, List<Pair<Provider, Double>>> map) {
        if (StringUtils.equalsIgnoreCase(conf.getRegion(), RegionEnum.ALL.getName())) {
            for (final String key : map.keySet()) {
                map.get(key).add(new MutablePair<>(provider, conf.getWeight()));
            }
        } else if (map.containsKey(conf.getRegion())) {
            map.get(conf.getRegion()).add(new MutablePair<>(provider, conf.getWeight()));
        }
    }
}