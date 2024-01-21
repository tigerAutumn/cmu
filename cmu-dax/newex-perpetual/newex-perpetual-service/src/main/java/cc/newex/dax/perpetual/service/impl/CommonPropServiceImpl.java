package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.CommonPropExample;
import cc.newex.dax.perpetual.data.CommonPropRepository;
import cc.newex.dax.perpetual.domain.CommonProp;
import cc.newex.dax.perpetual.service.CommonPropService;
import cc.newex.dax.perpetual.service.cache.CacheService;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 公共配置表 服务实现
 *
 * @author newex-team
 * @date 2018-11-01 09:30:57
 */
@Slf4j
@Service
public class CommonPropServiceImpl extends AbstractCrudService<CommonPropRepository, CommonProp, CommonPropExample, Long> implements CommonPropService {
    @Autowired
    private CommonPropRepository commonPropRepository;

    private Map<Integer, Map<String, String>> cacheMap = new ConcurrentHashMap<>();

    @PostConstruct
    void init() {
        refreshConfig();
    }

    @Override
    protected CommonPropExample getPageExample(final String fieldName, final String keyword) {
        final CommonPropExample example = new CommonPropExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public <T> T getConfigObject(Integer brokerId, String key, Class<T> clazz) {

        if (!cacheMap.containsKey(brokerId)) {
            return null;
        }

        final Map<String, String> map = cacheMap.get(brokerId);
        if (!map.containsKey(key.toLowerCase())) {
            return null;
        }

        String value = map.get(key.toLowerCase());
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return JSON.parseObject(value, clazz);
    }

    @Override
    public <T> List<T> getConfigList(Integer brokerId, String key, Class<T> clazz) {
        if (!cacheMap.containsKey(brokerId)) {
            return null;
        }

        final Map<String, String> map = cacheMap.get(brokerId);
        if (!map.containsKey(key.toLowerCase())) {
            return null;
        }

        String value = map.get(key.toLowerCase());
        if (StringUtils.isBlank(value)) {
            return null;
        }

        return JSON.parseArray(value, clazz);
    }

    @Override
    public void setConfig(Integer brokerId, String key, String jsonStr) {

        Date date = new Date();
        this.add(CommonProp.builder()
                .brokerId(brokerId)
                .createdDate(date)
                .modifyDate(date)
                .propKey(key.toLowerCase())
                .propValue(jsonStr)
                .build());
    }

    @Override
    @Scheduled(fixedDelay = 60_000, initialDelay = 10_000)
    public void refreshConfig() {
        final List<CommonProp> commonProps = this.getAll();
        if (CollectionUtils.isEmpty(commonProps)) {
            return;
        }
        Map<Integer, Map<String, String>> tempMap = new ConcurrentHashMap<>();
        for (CommonProp c : commonProps) {
            if (!tempMap.containsKey(c.getBrokerId())) {
                tempMap.put(c.getBrokerId(), new ConcurrentHashMap<>());
            }
            final Map<String, String> stringMap = tempMap.get(c.getBrokerId());
            stringMap.put(c.getPropKey().toLowerCase(), c.getPropValue());
        }
        cacheMap.putAll(tempMap);
    }

}