package cc.newex.dax.perpetual.openapi.support.aop.interceptor;

import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author newex-team
 * @date 2018-10-31 16:16:36
 */
//@Component
public class RequestReplaceFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        if (!(request instanceof CustomServletRequestWrapper)) {
            request = new CustomServletRequestWrapper(request);
        }
        filterChain.doFilter(request, response);
    }
}