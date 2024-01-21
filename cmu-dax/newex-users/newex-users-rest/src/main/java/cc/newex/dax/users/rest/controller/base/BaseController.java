package cc.newex.dax.users.rest.controller.base;

import cc.newex.commons.broker.consts.BrokerConsts;
import cc.newex.dax.users.common.consts.CommonConsts;
import cc.newex.dax.users.common.util.WebUtils;
import cc.newex.dax.users.service.membership.UserBrokerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 公共Action控制器类
 *
 * @author newex-team
 * @date 2018/03/18
 */
@Slf4j
public class BaseController {

    @Autowired
    private UserBrokerService userBrokerService;

    /**
     * 获取券商id
     *
     * @param request
     * @return
     */
    protected Integer getBrokerId(final HttpServletRequest request) {
        Integer brokerId = null;
        try {
            brokerId = (Integer) request.getAttribute(BrokerConsts.BROKER_ID);
        } catch (final Exception e) {
            log.error("getBrokerId is error", e);
        }
        return brokerId;
    }
}
