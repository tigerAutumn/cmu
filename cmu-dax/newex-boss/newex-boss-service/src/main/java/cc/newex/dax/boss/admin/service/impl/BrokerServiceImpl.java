package cc.newex.dax.boss.admin.service.impl;

import cc.newex.commons.mybatis.service.AbstractCrudService;
import cc.newex.dax.boss.admin.criteria.BrokerExample;
import cc.newex.dax.boss.admin.data.BrokerRepository;
import cc.newex.dax.boss.admin.domain.Broker;
import cc.newex.dax.boss.admin.service.BrokerService;
import cc.newex.dax.boss.admin.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 后台系统券商表 服务实现
 *
 * @author mbg.generated
 * @date 2018-09-11 14:44:15
 */
@Slf4j
@Service
public class BrokerServiceImpl extends AbstractCrudService<BrokerRepository, Broker, BrokerExample, Long> implements BrokerService {

    @Autowired
    private BrokerRepository adminBrokerRepos;

    @Autowired
    private UserService userService;

    @Override
    protected BrokerExample getPageExample(final String fieldName, final String keyword) {
        final BrokerExample example = new BrokerExample();
        example.createCriteria().andFieldLike(fieldName, keyword);
        return example;
    }
}