package cc.newex.dax.perpetual.service.impl;

import cc.newex.commons.mybatis.pager.PageInfo;
import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.perpetual.criteria.CurrencyPairBrokerRelationExample;
import cc.newex.dax.perpetual.data.CurrencyPairBrokerRelationRepository;
import cc.newex.dax.perpetual.domain.CurrencyPairBrokerRelation;
import cc.newex.dax.perpetual.dto.CurrencyPairBrokerRelationDTO;
import cc.newex.dax.perpetual.service.CurrencyPairBrokerRelationService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * 币对 券商 对应关系表 服务实现
 *
 * @author newex-team
 * @date 2018-11-16 11:18:52
 */
@Slf4j
@Service
public class CurrencyPairBrokerRelationServiceImpl extends AbstractCrudService<CurrencyPairBrokerRelationRepository, CurrencyPairBrokerRelation, CurrencyPairBrokerRelationExample, Long> implements CurrencyPairBrokerRelationService {
    @Autowired
    private CurrencyPairBrokerRelationRepository currencyPairBrokerRelationRepository;

    private volatile Map<CurrenccyPairBroker, CurrencyPairBrokerRelation> currencyPairBrokerRelationMap = new ConcurrentHashMap<>();

    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    protected CurrencyPairBrokerRelationExample getPageExample(final String fieldName, final String keyword) {
        final CurrencyPairBrokerRelationExample example = new CurrencyPairBrokerRelationExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @PostConstruct
    public void init() {
        this.loadDb();
    }

    @Override
    @Scheduled(fixedDelay = 500, initialDelay = 1000)
    public void loadDb() {
        final CurrencyPairBrokerRelationExample currencyPairBrokerRelationExample = new CurrencyPairBrokerRelationExample();
        final List<CurrencyPairBrokerRelation> currenccyPairBrokerRelationList = this.currencyPairBrokerRelationRepository.selectByExample
                (currencyPairBrokerRelationExample);
        final Map<CurrenccyPairBroker, CurrencyPairBrokerRelation> tmpMap = new ConcurrentHashMap<>();
        for (final CurrencyPairBrokerRelation bean : currenccyPairBrokerRelationList) {
            tmpMap.put(CurrenccyPairBroker.builder().currenccyPair(bean.getPairCode())
                    .brokerId(bean.getBrokerId()).build(), bean);
        }
        synchronized (this.currencyPairBrokerRelationMap) {
            this.currencyPairBrokerRelationMap = tmpMap;
        }
    }

    @Override
    public CurrencyPairBrokerRelation getPairCodeByCache(final String pairCode, final Integer brokerId) {
        return this.currencyPairBrokerRelationMap.get(CurrenccyPairBroker.builder().currenccyPair(pairCode)
                .brokerId(brokerId).build());
    }

    @Override
    public List<CurrencyPairBrokerRelation> getByBrokerId(final Integer brokerId) {
        final CurrencyPairBrokerRelationExample example = new CurrencyPairBrokerRelationExample();
        final CurrencyPairBrokerRelationExample.Criteria criteria = example.createCriteria();
        criteria.andBrokerIdEqualTo(brokerId);
        return this.getByExample(example);
    }

    @Override
    public List<CurrencyPairBrokerRelation> getByPairCode(final String... pairCode) {

        if (pairCode == null || pairCode.length == 0) {
            return new ArrayList<>(0);
        }
        final CurrencyPairBrokerRelationExample example = new CurrencyPairBrokerRelationExample();
        final CurrencyPairBrokerRelationExample.Criteria criteria = example.createCriteria();
        criteria.andPairCodeIn(Arrays.asList(pairCode));
        return this.getByExample(example);
    }

    /**
     * List currency pair broker relation by conditon list.
     *
     * @param pageInfo       the page info
     * @param queryParameter the query parameter
     * @return the list
     */
    @Override
    public List<CurrencyPairBrokerRelationDTO> listCurrencyPairBrokerRelationByConditon(final PageInfo pageInfo, final CurrencyPairBrokerRelationDTO queryParameter) {

        final CurrencyPairBrokerRelationExample example = new CurrencyPairBrokerRelationExample();
        final CurrencyPairBrokerRelationExample.Criteria criteria = example.createCriteria();

        if (Objects.nonNull(queryParameter.getBrokerId())) {
            criteria.andBrokerIdEqualTo(queryParameter.getBrokerId());
        }
        if (StringUtils.isNotEmpty(queryParameter.getPairCode())) {
            criteria.andPairCodeEqualTo(queryParameter.getPairCode());
        }
        return Optional.ofNullable(this.getByPage(pageInfo, example))
                .map(itemList -> itemList.stream()
                        .map(item -> this.modelMapper.map(item, CurrencyPairBrokerRelationDTO.class))
                        .collect(Collectors.toList()))
                .orElse(Collections.emptyList());
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class CurrenccyPairBroker {
        private String currenccyPair;
        private Integer brokerId;
    }
}