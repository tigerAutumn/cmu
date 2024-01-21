package cc.newex.dax.users.rest.common.broker;

import cc.newex.commons.broker.service.BrokerService;
import cc.newex.dax.users.service.membership.UserBrokerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;


@Order(1)
@Slf4j
@Service
public class BrokerServiceImpl extends BrokerService {

    @Autowired
    private UserBrokerService userBrokerService;

    @Override
    public Integer getBrokerIdFromUserClient(final String key) {
        return this.userBrokerService.getBrokerIdFromCache(key);
    }
}
