package cc.newex.commons.broker.interceptor;

import cc.newex.commons.broker.consts.BrokerConsts;
import cc.newex.commons.broker.service.BrokerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class BrokerInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private BrokerService brokerService;

    @Override
    public boolean preHandle(final HttpServletRequest request,
                             final HttpServletResponse response, final Object handler) throws Exception {
        final Integer brokerId = brokerService.getBrokerId();
        if (brokerId != null) {
            request.setAttribute(BrokerConsts.BROKER_ID, brokerId);
        }
        return super.preHandle(request, response, handler);
    }
}
