//package cc.newex.dax.users.rest.common.interceptor;
//
//import cc.newex.dax.users.common.consts.CommonConsts;
//import cc.newex.dax.users.service.membership.UserBrokerService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.net.URL;
//
///**
// * @author new-team
// * @date 2018-09-17
// */
//@Slf4j
//public class BrokerInterceptor extends HandlerInterceptorAdapter {
//    @Autowired
//    private UserBrokerService userBrokerService;
//
//    @Override
//    public boolean preHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler) throws Exception {
//        log.debug("request url:{}", request.getRequestURL());
//
//        final String host = getHost(request);
//
//        final Integer brokerId = this.userBrokerService.getBrokerIdFromCache(getHost(request));
//
//        request.setAttribute(CommonConsts.CURRENT_USER_BROKER_ID, brokerId);
//
//        return super.preHandle(request, response, handler);
//    }
//
//    /**
//     * 获取主机名
//     *
//     * @param request
//     * @return
//     */
//    public static String getHost(final HttpServletRequest request) {
//        final String urlStr = request.getRequestURL().toString();
//
//        try {
//            final URL url = new URL(urlStr);
//
//            return url.getHost();
//        } catch (final Exception e) {
//            log.error(e.getMessage(), e);
//        }
//
//        return null;
//    }
//
//}
