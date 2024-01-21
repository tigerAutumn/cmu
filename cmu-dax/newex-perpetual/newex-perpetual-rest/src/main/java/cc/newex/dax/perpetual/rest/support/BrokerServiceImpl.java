package cc.newex.dax.perpetual.rest.support;

import cc.newex.commons.broker.service.BrokerService;
import cc.newex.commons.support.model.ResponseResult;
import cc.newex.commons.support.util.ResultUtils;
import cc.newex.dax.users.client.UsersClient;
import com.alibaba.fastjson.JSON;
import com.google.common.base.Preconditions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Order(1)
@Slf4j
@Service
public class BrokerServiceImpl extends BrokerService {

    @Autowired
    private UsersClient usersClient;

    @Override
    public Integer getBrokerIdFromUserClient(final String host) {
        final ResponseResult<Integer> responseResult = this.usersClient.getBrokerId(host);
        Preconditions.checkArgument(responseResult != null,
                "take brokerId failed, response is null, host : {}", host);
        Preconditions.checkArgument(responseResult.getCode() == ResultUtils.success().getCode(),
                "take brokerId failed, response : {}, host : {}",
                JSON.toJSON(responseResult), host);
        BrokerServiceImpl.log.debug("take brokerId success, response : {}, host : {}",
                JSON.toJSON(responseResult), host);
        BrokerServiceImpl.log.info("take brokerId success : host : {}, brokerId : {}", host, responseResult.getData());
        return responseResult.getData();
    }
}
