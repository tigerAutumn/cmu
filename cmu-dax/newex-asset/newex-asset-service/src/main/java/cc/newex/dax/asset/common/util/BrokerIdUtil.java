package cc.newex.dax.asset.common.util;

import cc.newex.commons.support.model.ResponseResult;
import cc.newex.dax.asset.util.DomainUtil;
import cc.newex.dax.users.client.UsersClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

/**
 * @author newex
 */
@Component
@Slf4j
public class BrokerIdUtil {

    private static UsersClient USERS_CLIENT;

    @Autowired
    private UsersClient usersClient;

    public static ResponseResult<Integer> getBrokerId(HttpServletRequest request) {
        String domain = DomainUtil.getDomain(request);
        return USERS_CLIENT.getBrokerId(domain);
    }

    @PostConstruct
    public void init() {
        USERS_CLIENT = usersClient;
    }


}
