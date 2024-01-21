package cc.newex.dax.integration.service.conf.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.integration.criteria.conf.BrokerSignConfigExample;
import cc.newex.dax.integration.data.conf.BrokerSignConfigRepository;
import cc.newex.dax.integration.domain.conf.BrokerSignConfig;
import cc.newex.dax.integration.service.conf.BrokerSignConfigService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @author mbg.generated
 * @date 2018-09-12 15:04:54
 */
@Slf4j
@Service
public class BrokerSignConfigServiceImpl extends AbstractCrudService<BrokerSignConfigRepository, BrokerSignConfig, BrokerSignConfigExample, Long> implements BrokerSignConfigService {
    @Autowired
    private BrokerSignConfigRepository brokerSignConfigRepos;

    @Override
    protected BrokerSignConfigExample getPageExample(final String fieldName, final String keyword) {
        final BrokerSignConfigExample example = new BrokerSignConfigExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }

    @Override
    public String takeSignByBroker(final Integer brokerId) {

        if (brokerId == null) {
            BrokerSignConfigServiceImpl.log.warn("brokerId is null");
            return null;
        }

        final BrokerSignConfigExample example = new BrokerSignConfigExample();
        example.createCriteria().andBrokerIdEqualTo(brokerId);
        final BrokerSignConfig record = this.getOneByExample(example);

        if (record == null) {
            BrokerSignConfigServiceImpl.log.error("not found borkerSignConfig by borkerId : {}", brokerId);
            return null;
        }

        return record.getSign();
    }

    @Override
    public BrokerSignConfig getByBrokerId(final Integer brokerId) {
        final BrokerSignConfigExample example = new BrokerSignConfigExample();
        example.createCriteria().andBrokerIdEqualTo(brokerId);
        return this.getOneByExample(example);
    }

    @Override
    public List<BrokerSignConfig> getByBrokerId(final Integer[] brokerId) {
        if (brokerId == null || brokerId.length == 0) {
            return new ArrayList<>();
        }
        final BrokerSignConfigExample example = new BrokerSignConfigExample();
        example.createCriteria().andBrokerIdIn(Arrays.asList(brokerId));
        return this.getByExample(example);
    }

    @Override
    public void updateByBrokerId(final Integer brokerId, final String sign) {
        final BrokerSignConfigExample example = new BrokerSignConfigExample();
        example.createCriteria().andBrokerIdEqualTo(brokerId);

        final BrokerSignConfig record = BrokerSignConfig.builder()
                .modifyTime(new Date())
                .sign(sign)
                .build();

        this.brokerSignConfigRepos.updateByExample(record, example);
    }

    @Override
    public void removeByBrokerId(final Integer brokerId) {

        final BrokerSignConfigExample example = new BrokerSignConfigExample();
        example.createCriteria().andBrokerIdEqualTo(brokerId);
        this.brokerSignConfigRepos.deleteByExample(example);
    }
}