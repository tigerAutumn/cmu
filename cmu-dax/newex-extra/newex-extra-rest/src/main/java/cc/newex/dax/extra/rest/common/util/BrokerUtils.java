package cc.newex.dax.extra.rest.common.util;

import cc.newex.commons.broker.consts.BrokerConsts;
import cc.newex.commons.dictionary.consts.BrokerIdConsts;

import javax.servlet.http.HttpServletRequest;

/**
 * The type Broker utils.
 *
 * @author better
 * @date create in 2018/9/29 上午11:41
 */
public class BrokerUtils {

    /**
     * brokerId
     */
    public static final String CURRENT_USER_BROKER_ID = "brokerId";

    /**
     * 获取brokerId
     *
     * @param request the request
     * @return broker id
     */
    public static Integer getBrokerId(final HttpServletRequest request) {
        return (Integer) request.getAttribute(BrokerConsts.BROKER_ID);
    }
}